<?php

namespace App\Http\Controllers\Api\V1_0_0\Customer\Main;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Branch;
use App\Models\Item;
use App\Models\KitchenType;
use App\Models\Offer;
use App\Models\RestaurantTypes;
use App\Models\SupportedVendor;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Http\Request;
use Illuminate\Validation\Rule;

class PublicController extends Controller
{
    public function list(Request $request, SupportedVendor $service)
    {
        $request->validate([
            'place_id'   =>  [
                Rule::requiredIf($request->user('api')!==null),
                Rule::in([-1,...(($request->user('api'))?$request->user('api')->places->pluck('id')->toArray():[])]),
                'nullable'
            ],
            'lat'        =>  [
                Rule::requiredIf($request->user('api')===null),
                'numeric'
            ],
            'lon'        =>  [
                Rule::requiredIf($request->user('api')===null),
                'numeric'
            ],
            'order_by'  =>  'in:default,min_price_lowest,min_price_highest,rate|nullable',
            'kitchen_type'      => 'in:-1,'.implode(',',KitchenType::all('id')->pluck('id')->toArray()).'|nullable',
            'restaurant_type'   => 'in:-1,'.implode(',',RestaurantTypes::all('id')->pluck('id')->toArray()).'|nullable',
            'search'       => 'string|max:250|nullable',
        ]);
        exec('echo lon:'.$request->input('lon').',lat:'.$request->input('lat').'>>~/zTryShit/try.log');
        if ($request->user('api'))
        {
            $request->user('api')->places()->update(['isDefault' =>  false]);
            $place = $request->user('api')->places()->findOrFail($request->input('place_id'));
            $place->isDefault = true;
            $place->save();
        }else{
            $place = new \stdClass();
            $place->lon = $request->input('lon');
            $place->lat = $request->input('lat');
        }

        $vendor_ids = Branch::ByDistance($place)
            ->where('available_status', '=', true)
            ->whereHas('vendor', function (Builder $query) use ($service) {
                $query->where('supported_vendor_id', '=', $service->id);
            })
            ->get()
            ->pluck('vendor_id')
            ->unique()->values()->all();

        $data = Vendor::select('id','brandName as name','description','email','phone','logo','image','city_id','supported_vendor_id')
                    ->whereIn('id', $vendor_ids)
                    ->with('city:id,name_'.self::$lang.' as name');

        if ($service->isRestaurant())
        {
            $data->with('data.restaurantType:id,name_'.self::$lang.' as name');
            $data->with('data.kitchenType:id,name_'.self::$lang.' as name');

            if ($request->input('kitchen_type')!==null&&$request->input('kitchen_type')!=="-1")
                $data->whereHas('data.kitchenType', function (Builder $q) use ($request) {
                    return $q->where('id', $request->input('kitchen_type'));
                });

            if ($request->input('restaurant_type')!==null&&$request->input('restaurant_type')!=="-1")
                $data->whereHas('data.restaurantType', function (Builder $q) use ($request) {
                    return $q->where('id', $request->input('restaurant_type'));
                });
        }
        $search    = $request->input('search') ?? "";
        if ($search)
        {
            $search = '%'.$search.'%';
            $data = $data->where(function (Builder $q) use ($search){
                return $q
                    ->where('brandName', 'like', $search)
                    ->orWhere('legalName', 'like', $search)
                    ->orWhere('description', 'like', $search);
            });
        }

        return \APIHelper::jsonRender('',APIHelper::paginate($data,null,
            function ($datum){
                $datum->min_price = (float)$datum->min_price();
                $value = $datum->rate;
                $datum->rates = $value;

                if ($datum->isRestaurant())
                {
                    if (!is_null($datum->data)) {
                        $datum->restaurant_types = $datum->data->restaurantType->name;
                        $datum->kitchen_types = $datum->data->kitchenType->name;
                    }else
                    {
                        $datum->restaurant_types = '';
                        $datum->kitchen_types = '';
                    }
                }

                unset($datum->branches,$datum->data, $datum->supported_vendor);
            },
            $request->input('order_by')),200,[]);
    }

    public function items(Request $request, Vendor $vendor)
    {
        $request->validate([
            'lat'        =>  [
                Rule::requiredIf($request->user('api')===null),
                'numeric'
            ],
            'lon'        =>  [
                Rule::requiredIf($request->user('api')===null),
                'numeric'
            ],
            'category'   => 'in:-1,0,all,'.implode(',',$vendor->categories->pluck('id')->toArray()).'|nullable',
            'order_by'  =>  'in:default,price_lowest,price_highest|nullable',
            'search'       => 'string|max:250|nullable',
        ]);
        if ($request->user('api'))
        {
            $place = $request->user('api')->defaultPlace;
        }else{
            $place = new \stdClass();
            $place->lon = $request->input('lon');
            $place->lat = $request->input('lat');
        }

        Branch::ByDistance($place)
            ->where('available_status', '=', true)
            ->where('vendor_id', '=', $vendor->id)
            ->whereHas('items', function (Builder $query) {
                $query
                    ->where('amount','>',0)
                    ->Orwhere('amount','=',-1);
            })->get()->each(function ($branch) use (&$item_ids){
                if ($item_ids===null) $item_ids = [];

                $item_ids = array_merge($item_ids,$branch->items()->select('item_id')->get()->pluck('item_id')->toArray());
            });

        $items = Item::select(
            ['id','category_id','price','img','name_'.self::$lang.' as name', 'brief_desc_'.self::$lang.' as description',
                ...(($vendor->isRestaurant())?
                    ['amount as calories']:
                    ['amount', 'amount_type'])
            ])->whereIn('id',collect($item_ids)->unique());

        if ($vendor->isRestaurant())
            $offers = $vendor->offers()
                ->select('id','price','img')
                ->get();

        $category = $request->input('category');
        if ($category!==null&&$category!=='all'&&$category!=="-1"&&$category!=="0")
            $items->whereHas('category', function (Builder $q) use ($request) {
                return $q->where('id', $request->input('category'));
            });

        $search    = $request->input('search') ?? "";
        if ($search)
        {
            $search = '%'.$search.'%';
            $items = $items->where(function (Builder $q) use ($search) {
                return $q->where('name_en', 'like', $search)
                    ->orWhere('name_ar', 'like', $search)
                    ->orWhere('brief_desc_en', 'like', $search)
                    ->orWhere('brief_desc_ar', 'like', $search)
                    ->orWhere('description_en', 'like', $search)
                    ->orWhere('description_ar', 'like', $search);
            });
        }

        return \APIHelper::jsonRender('', ($vendor->isRestaurant())?
            [
                'offers'   =>   $offers,
                'main'     =>   $this->renderItemsData($items, $place),
                'rates'    =>   $vendor->rate
            ]
            :$this->renderItemsData($items, $place));
    }
    private function renderItemsData($items, $place) {
        return APIHelper::paginate($items, null, function ($item) use ($place){
            $item->is_favorite = $item->isFavorite;
            $item->max = Branch::ByDistance($place)
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
            if ((int)$item->max ===-1) unset($item->max);
        });
    }

    public function viewItem(Item $item)
    {
        if ($item->vendor->isRestaurant())
            $item->makeHidden('images');
        else {
            $item->makeHidden(['sizes', 'optionals', 'extras', 'drinks']);
            $item->similar();
        }

        $item->name = $item->{'name_'.self::$lang};
        $item->description = $item->{'description_'.self::$lang};
        $item->is_favorite = $item->isFavorite;


        unset($item->vendor);
        return APIHelper::jsonRender('',$item);
    }
    public function viewOffer(Offer $offer)
    {
        $offer->name = $offer->{'name_'.self::$lang};
        $offer->description = $offer->{'description_'.self::$lang};

        return APIHelper::jsonRender('',$offer);
    }
}
