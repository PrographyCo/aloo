<?php

namespace App\Models;

use App\Helpers\EncDecHelper;
use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CustomerCart extends Model
{
    use HasFactory;

    protected $fillable = [
        'supported_vendor_id'
    ];

    protected $hidden = [
        'supported_vendor_id',
        'customer_id',
        'created_at',
        'updated_at'
    ];

    public function customer()
    {
        return $this->belongsTo(Customer::class);
    }
    public function supported_vendor()
    {
        return $this->belongsTo(SupportedVendor::class);
    }

    public function getTotal()
    {
        $value = $this->items()->sum('total_price');
        if (request()->user('api'))
        {
            $package = request()->user('api')->getDiscountPackage();
            if ($package) {
                $package=$package->package;
                $discount = (int)$package->discount;
                if ($package->discount_type === '%')
                    $value -= ($discount * $value / 100);
                else
                    $value -= $discount;
                if ($value < 0) $value = 0;
            }
        }
        return (float)number_format($value,2,'.','');
    }
    public function getDeliveryPrice()
    {
        $count = $this->items->pluck('branch_id')->unique()->count();
        if (request()->user('api'))
        {
            $package = request()->user('api')->getDeliveryPackage();
            if ($package)
            {
                $ordersCount = (int)$package->orders;
                $count -= $ordersCount;
                if($count<0) $count=0;
            }
        }
        return $count * 500;
    }

    public function toOrder($placeId) : \stdClass
    {
        $user = request()->user();
        if (is_null($user->wallet)) {
            $user->wallet()->create([
                'amount' => 0
            ]);
        }

        $return = new \stdClass();
        $return->status = false;
        $return->message = 'errors.cartNoItem';
        $return->data = [];
        $return->code = 499;

        if ($user->wallet->amount < ($this->getTotal() + $this->getDeliveryPrice()))
        {
            $return->message = 'errors.NoMoney';
            $data = [
                'id'    =>  $user->id,
                'min'   =>  ($this->getTotal() + $this->getDeliveryPrice()) - $user->wallet->amount
            ];
            $return->data = [
                'url'   =>  route('payment',['lang'=>Controller::$lang,'token'=>urlencode(EncDecHelper::enc(json_encode($data)))]),
                'stc'   =>  route('payment.stc',['lang'=>Controller::$lang,'token'=>urlencode(EncDecHelper::enc(json_encode($data)))])
            ];
            return $return;
        }

        if ($placeId!==null)
            $place = $user->places()->findOrFail($placeId)->first();
        else
            $place = $user->defaultPlace;

        if ($this->items->count()>0){
            $return->status = true;

            foreach ($this->items->pluck('branch_id')->unique()->sort()->toArray() as $branchId) {
                $total = $this->items()->where('branch_id', '=', $branchId)->get()->sum('total_price');
                $order = $user->orders()->create([
                    'branch_id' => $branchId,
                    'place_id' => $place->id,
                    'total_price' => $total
                ]);
                $delivery = (float)Config::where('key','delivery_reservation')->first()->value;
                if (request()->user('api')->getDeliveryPackage()){
                    $delivery = 0;
                    $package = request()->user('api')->getDeliveryPackage();
                    $package->orders--;
                    $package->save();
                }

                $this->customer->wallet->amount -= ($total+$delivery);
                $this->customer->wallet->reserved += ($total + (float)Config::where('key','delivery_reservation')->first()->value);
                $this->customer->wallet->save();

                $this->customer->walletTrack()->create([
                    'price'     =>  $total,
                    'process'   =>  'reserve',
                    'direction' =>  [
                        'order' =>  $order->id,
                        'for'   =>  $branchId
                    ],
                    'message'   =>  'Reserved For Order #'.$order->id,
                    'order_type'=>  'reserve',
                    'reason'    =>  'This Amount Reserved For An order You Made',
                    'isTransaction'=>false,
                    'transaction_id'=>null
                ]);

                $this->customer->walletTrack()->create([
                    'price'     =>  $delivery,
                    'process'   =>  'reserve',
                    'direction' =>  [
                        'order' =>  $order->id,
                        'for'   =>  'delivery'
                    ],
                    'message'   =>  'Reserved For delivery in Order #'.$order->id,
                    'order_type'=>  'reserve',
                    'reason'    =>  'This Amount Reserved For the delivery of An order You Made',
                    'isTransaction'=>false,
                    'transaction_id'=>null
                ]);

                foreach ($this->items()->where('branch_id', '=', $branchId)->get() as $item) {
                    if (is_null($item->offer_id))
                    {
                        $branchItem = BranchItems::where('item_id', $item->item_id)->where('branch_id','=',$branchId)->first();
                        if (is_null($branchItem)) dd($branchItem, $item, $branchId);
                        if ($branchItem->amount!=-1&&$branchItem->amount<$item->amount) {
                            $return->message = 'errors.NoAmountInBranch';
                            $item->amount = $branchItem->amount;
                            $item->save();
                            continue;
                        }
                    }
                    $order->items()->create([
                        'item_id' => $item->item_id,
                        'offer_id' => $item->offer_id,
                        'amount' => $item->amount,
                        'item_price' => $item->total_price,
                        'data' => $item->data
                    ]);
                    if (is_null($item->offer_id))
                    {
                        if ($branchItem->amount>0) {
                            $branchItem->amount -= $item->amount;
                            $branchItem->save();
                        }
                    }

                    $item->delete();
                }
                $order->makeRealtime();
                $return->data['orders'][] = $order->load('items');
            }
        }

        return $return;
    }

    public function items()
    {
        return $this->hasMany(CartItem::class)->with(['item:id,name_'.Controller::$lang.' as name,img',
            'offer:id,name_'.Controller::$lang.' as name,img']);
    }

    public function isRestaurant()
    {
        return ($this->supported_vendor->name_en === 'restaurants');
    }
}
