<?php

namespace App\Http\Controllers\Api\V1_0_0\Customer\Main;

use App\Helpers\APIHelper;
use App\Helpers\CustomClass;
use App\Http\Controllers\Controller;
use App\Models\Branch;
use App\Models\CartItem;
use App\Models\CustomerPlaces;
use App\Models\Drink;
use App\Models\Extra;
use App\Models\Item;
use App\Models\Offer;
use App\Models\SupportedVendor;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Http\Request;
use Illuminate\Validation\Rule;

class CartController extends Controller
{
    public function list(SupportedVendor $vendor_type)
    {
        $cart = \request()->user()->carts()->where('supported_vendor_id',$vendor_type->id)->first();
        if (!$cart) {
            $cart = \request()->user()->carts()->create([
                'supported_vendor_id' => $vendor_type->id
            ]);
        }
        $items = $cart->items();
        return \APIHelper::jsonRender('',[
            'cart'=>[
                'items'=>$items->get()->each(function ($item){
                    if ($item->is_offer()) {
                        unset($item->item);
                        $item->item = $item->offer;
                    }
                    $item->is_offer = $item->is_offer();
                    unset($item->offer);
                }),
                'place' => \request()->user()->defaultPlace,
                'total'=>(float)number_format($cart->getTotal(),2,'.',''),
                'delivery_price_reservation'=> (float)number_format($cart->getDeliveryPrice(),2,'.','')
            ]
        ],200,[]);
    }

    private function isRestaurant()
    {
        $item = Item::find(\request('item_id'));
        return (\request('offer_id'))?false:$item->vendor->isRestaurant();
    }
    public function add(Request $request)
    {
        $request->validate([

            'offer_id'  =>  'exists:'.(new Offer())->getTable().',id|nullable',
            'item_id'   =>  'exists:'.(new Item())->getTable().',id|required_without:offer_id',

            'amount'    =>  'integer',

            'with'      =>  [
                'nullable'
            ],
            'without'      =>  [
                'nullable'
            ],
            'size'      =>  [
                Rule::requiredIf($this->isRestaurant()),
                'in:S,M,B,s,m,b,Small,Medium,Big,small,medium,big',
            ],
            'drinks'      =>  [
                'nullable'
            ],
            'drinks.*'  =>  [
                ($this->isRestaurant())?Rule::in(['',...collect(Item::find($request->input('item_id'))->drinks)->pluck('id')->toArray()]):''
            ],
            'extras'      =>  [
                'nullable'
            ],
            'extras.*'  =>  [
                ($this->isRestaurant())?Rule::in(['',...collect(Item::find($request->input('item_id'))->extras)->pluck('id')->toArray()]):''
            ]
        ]);
        $place = $request->user()->defaultPlace;

        if (!$request->input('amount'))
            $amount = 1;
        else
            $amount = $request->input('amount');

        $branch = Branch::ByDistance($place)
            ->where('available_status', '=', true);
        $offer  =   null;
        $item   =   null;

        if ($request->input('offer_id'))
        {
            $offer = Offer::find($request->input('offer_id'));
            $data = new CustomClass([
                'with'  =>  $offer->with,
                'without'=> $offer->without,
                'size'  =>  $offer->size,
                'drinks'=>  $offer->drinks,
                'extras'=>  $offer->extras
            ]);
            $arr = ['offer_id',$offer->id];

            $branch = $branch
//                ->whereHas('offers', function (Builder $query) use ($offer) {
//                    $query
//                        ->where('offer_id','=',$offer->id);
//                })
                ->first();

            $unit_price = $offer->price;
        }else
        {
            $item = Item::find($request->input('item_id'));
            $data = new CustomClass([
                'with'  =>  (!is_null($request->input('with'))&&$request->input('with')!=='[]')?$request->input('with'):[],
                'without'=> (!is_null($request->input('without'))&&$request->input('without')!=='[]')?$request->input('without'):[],
                'size'  =>  strtoupper(substr($request->input('size'),0,1))??'',
                'drinks'=>  (!is_null($request->input('drinks'))&&$request->input('drinks')!=='[]')?$request->input('drinks'):[],
                'extras'=>  (!is_null($request->input('extras'))&&$request->input('extras')!=='[]')?$request->input('extras'):[]
            ]);
            $arr = ['item_id',$item->id];

            $branch = $branch
                ->whereHas('items', function (Builder $query) use ($item, $amount) {
                    $query
                        ->where('item_id','=',$item->id)
                        ->where(function(Builder $query) use ($amount) {
                            $query
                                ->where('amount','>=',$amount)
                                ->Orwhere('amount','=',-1);
                        });
                })
                ->first();

            if (is_null($branch))
            {
                $max = Branch::ByDistance($place)
                    ->where('available_status', '=', true)
                    ->whereHas('items', function (Builder $query) use ($item) {
                        $query
                            ->where('item_id','=',$item->id);
                    })
                    ->get()
                    ->each(function ($branch) use ($item){
                        $branch->max = $branch->items()->where('item_id',$item->id)->max('amount');
                    })
                    ->pluck('max')
                    ->max();

                return APIHelper::error('itemMaxAmt',[],[],['max'=>($max??0)]);
            }

            $unit_price = $item->price;
            if ($branch->vendor->isRestaurant())
            {
                $unit_price += $item->sizes[__('api/order.sizes.'.strtolower($data->size))];

                foreach (collect($item->drinks)->filter(function ($drink) use ($data) {
                    return in_array($drink->id,$data->drinks, false);
                }) as $drink)
                {
                    $unit_price += $drink->price;
                }
                foreach (collect($item->extras)->filter(function ($extra) use ($data) {
                    return in_array($extra->id,$data->extras, false);
                }) as $extra)
                {
                    $unit_price += $extra->price;
                }
            }
        }

        $cart = $request->user()->carts()->where('supported_vendor_id',($offer??$item)->vendor->supported_vendor->id)->first();
        if (!$cart) {
            $cart = $request->user()->carts()->create([
                'supported_vendor_id' => ($offer??$item)->vendor->supported_vendor->id
            ]);
        }
        $cart->items()->create([
            $arr[0]     =>  $arr[1],
            'branch_id' =>  $branch->id,
            'amount'    =>  $amount,
            'data'      =>  $data,
            'unit_price'=>  $unit_price,
            'total_price'=> $unit_price * $amount
        ]);
        return \APIHelper::jsonRender('success.success',[],200,[]);
    }

    public function view(CartItem $item)
    {
        if ($item->is_offer()) return APIHelper::error('OfferNoView');
        return \APIHelper::jsonRender('',$item->load('item:'.implode(',',['id','category_id','price','img','name_'.self::$lang.' as name', 'brief_desc_'.self::$lang.' as description',
                ...(($item->item->vendor->isRestaurant())?
                    ['amount as calories', 'sizes', 'optionals', 'extras', 'drinks']:
                    ['amount', 'amount_type', 'images'])
            ])));
    }

    public function edit(Request $request, CartItem $item)
    {
        if ($item->is_offer()) return APIHelper::error('OfferNoView');
        Item::$withDetails = false;
        $request->validate([
            'amount'    =>  'integer|nullable',
            'with'      =>  [
                'array',
                'nullable'
            ],
            'without'      =>  [
                'array',
                'nullable'
            ],
            'size'      =>  [
                'in:S,M,B,s,m,b,Small,Medium,Big,small,medium,big',
                'nullable'
            ],
            'drinks'      =>  [
                'nullable'
            ],
            'drinks.*'  =>  [
                Rule::exists((new Drink())->getTable(),'id'),
            ],
            'extras'      =>  [
                'nullable'
            ],
            'extras.*'  =>  [
                Rule::exists((new Extra())->getTable(),'id'),
            ]
        ]);

        $data = $item->data;

        $item->amount = $request->input('amount')??$item->amount;
        $data->with = $request->input('with')??'';
        $data->without = $request->input('without')??'';
        if ($request->input('size'))
        {
            if (array_key_exists(strtoupper($request->input('size')), $item->item->sizes))
                $data->size = strtoupper($request->input('size'));
            else
                return \APIHelper::error('itemNoSize');
        }
        if ($request->input('drinks'))
        {
            $drinks = [];
            foreach ($request->input('drinks') as $drink)
            {
                if (in_array((int)$drink, $item->item->drinks, true))
                    $drinks[] = $drink;
            }
            $data->drinks = $drinks;
        }
        if ($request->input('extras'))
        {
            $extras = [];
            foreach ($request->input('extras') as $extra)
            {
                if (in_array((int)$extra, $item->item->extras, true))
                    $extras[] = $extra;
            }
            $data->extras = $extras;
        }

        $data->size = isset($data->size)?strtoupper((strlen($data->size)>1)?$data->size[0]:$data->size):'';
        $item->data = $data;

        $size = (!empty($item->item->sizes))?($item->item->sizes[strtoupper($data->size[0])]):0;
        $item->unit_price = $item->item->price + $size;
        foreach ($data->drinks as $drink) {
            $item->unit_price += Drink::find($drink)->first()->price;
        }
        foreach ($data->extras as $extra) {
            $item->unit_price += Extra::find($extra)->first()->price;
        }

        $item->total_price = $item->unit_price * $item->amount;
        $item->save();

        return \APIHelper::jsonRender('success.success', $item);
    }

    public function delete(CartItem $item)
    {
        $item->delete();

        return APIHelper::jsonRender('success.itemDelete',[],200,[]);
    }

    public function convertToOrder(SupportedVendor $vendor_type)
    {
        \request()->validate([
            'place' =>  'exists:'.(new CustomerPlaces())->getTable().',id|nullable'
        ]);

        $cart = \request()->user()->carts()->where('supported_vendor_id',$vendor_type->id)->first();
        $order = $cart->toOrder(\request('place'));
//        foreach (Admin::whereNotNull('FToken')->get() as $admin){
//            APIHelper::notification($admin, 'new order created',
//                \Auth::user()->name . ' created a new order link :' . route('admin.order.show', $order));
//        }
        if(!$order->status) return APIHelper::jsonRender($order->message, $order->data, $order->code);

        return APIHelper::jsonRender('success.success',$order->data,200,[]);
    }

    public function addMoney(Request $request)
    {
        $request->validate([
            'amount'    =>  'required|numeric'
        ]);

        $request->user()->wallet->update([
            'amount'    =>  $request->user()->wallet->amount + $request->input('amount')
        ]);

        return $request->user()->wallet;
    }
}
