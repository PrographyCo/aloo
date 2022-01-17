<?php

namespace App\Http\Controllers\Api\V1_0_0\Car\Order;

use App\Helpers\APIHelper;
use App\Helpers\EncDecHelper;
use App\Http\Controllers\Controller;
use App\Jobs\OrderNotification;
use App\Models\Admin;
use App\Models\Branch;
use App\Models\Config;
use App\Models\DriverCityPrice;
use App\Models\Order;
use App\Models\OrderCancel;
use App\Models\Review;
use App\Models\SupportedVendor;
use Carbon\Carbon;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Query\Builder as Query;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use function PHPUnit\Framework\isNull;

class OrderController extends Controller
{
    public function getOrderCurrent()
    {
        \request()->validate([
            'type'    =>  'exists:'.(new SupportedVendor())->getTable().',id||nullable'
        ]);

        $orders = Auth::user()->orders()
            ->select('id','total_price as total', 'status','branch_id','place_id','created_at','customer_id');

        if (\request('type')!==null)
        {
            $orders->whereHas('branch.vendor.supported_vendor', function (Builder $query) {
                $query->where('id', '=', \request('type'));
            });
        }
        $orders->orderBy('id','desc');

        return \APIHelper::jsonRender('', \APIHelper::paginate($orders,null, function ($order) {
            $order->vendor_type = $order->branch->vendor->supported_vendor()->select('id','name_'.Controller::$lang.' as name')->first();
            $order->customer_name = $order->customer->name;
            $order->date = $order->created_at;
            unset($order->branch, $order->place->lon, $order->place->lat, $order->customer);
        }));
    }

    public function viewOrderData(Order $order)
    {
        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        $order->date = $order->created_at;
        $order->makeVisible(['distance','delivery_price']);

        $order->load(['customer:id,name,phone,city_id', 'items.item:id,img,amount,amount_type,name_'.self::$lang.' as name','place']);
        $order->customer->rate = $order->customer->getCustomerRate->avg('rate')??0;
        $order->delivery_time = (strtotime($order->deliver_time) - strtotime($order->start_time)) / 60;

        $order->order_type = $order->branch->vendor->type(true);

        unset($order->branch, $order->customer->getCustomerRate, $order->customer->city);

        return \APIHelper::jsonRender('', $order);
    }
    /**
     * order
     */
    public function orders(Request $request)
    {
        $request->validate([
            'lon'       =>  'required|numeric',
            'lat'       =>  'required|numeric',
            'order_by'  =>  'string|in:far,close,Close,Far,default|nullable',
            'type'      =>  'integer|in:-1,'.implode(',',SupportedVendor::all('id')->pluck('id')->toArray()).'|nullable'
        ]);

        $confDistance = Config::where('key', 'distance')->first();
        $distance = (!is_null($confDistance)) ? $confDistance->value : 7;

        $equation = "6371 * acos(cos(radians(" . $request->input('lat') . "))
                * cos(radians(lat))
                * cos(radians(lon) - radians(" . $request->input('lon') . "))
                + sin(radians(" .$request->input('lat'). "))
                * sin(radians(lat)))";

        $orders = Order::select('id','total_price as total','branch_id','place_id','created_at','customer_id')
            ->whereNotIn('id',
                array_values(OrderCancel::select('order_id')
                    ->whereIn('relationship', ['Customer','Branch'])
                    ->Orwhere(function (Builder $q) {
                        $q->where('user_id', '=', Auth::id())->where('relationship','=','Car');
                    })
                    ->get()->pluck('order_id')->unique()->toArray())
            )->where(function($query) use ($equation, $request,$distance) {

                $query->whereNull('car_id')
                    ->whereIn('status', ['confirmed by vendor','ready'])
                    ->whereHas('branch', function (Builder $query) use ($equation, $distance){

                        $query->where(\DB::raw($equation), '<=', $distance)
                            ->where('available_status','!=' ,false);

                    })->orderBy($equation, ($request->input('order_by')!==null && strtolower($request->input('order_by')) === 'far') ? 'desc' : 'asc' );
            })->orderBy('id','desc');

        $type = (int)(\request('type'));
        if ($type>0)
        {
            $orders->whereHas('branch.vendor.supported_vendor', function (Builder $query) {
                $query->where('id', '=', \request('type'));
            });
        }


        return \APIHelper::jsonRender('', \APIHelper::paginate($orders,null, function ($order) {
            $order->vendor_type = $order->branch->vendor->supported_vendor()->select('id','name_'.Controller::$lang.' as name')->first();
            $order->customer_name = $order->customer->name;
            $order->date = $order->created_at;
            unset($order->branch, $order->place->lon, $order->place->lat, $order->customer);
        }));
    }

    function order(Order $order)
    {
        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        $order->date = $order->created_at;

        $order->load(['customer:id,name,phone', 'items.item:id,img,amount,amount_type,name_'.self::$lang.' as name']);
        $order->customer->rate = $order->customer->getCustomerRate->avg('rate')??0;

        unset($order->branch, $order->place->lon,$order->place->lat, $order->customer->getCustomerRate);

        return \APIHelper::jsonRender('', $order);
    }

    public function confirm(Order $order)
    {
        \request()->validate([
            'distance'  =>  'required|numeric'
        ]);

        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        $status = strtolower($order->status);
        if ($status !== 'confirmed by vendor' && $status!== 'ready')
            return \APIHelper::error('orderTakeErr');

        if (env('APP_ENV')!=='local' && env('APP_ENV')!=='testing')
        {
            if(Auth::user()->orders()
                ->whereNotIn('status', ['done','cancelled'])
                ->get())
                return \APIHelper::error('OrderDeliver');
        }

        $isCancel = $order->cancel()->where('user_id', Auth::id())->where('relationship', 'car')->first();
        if ($isCancel)
            return \APIHelper::error('orderCancelAlready');

        $order->car_id = Auth::id();
        $order->status = 'confirmed by driver';
        $order->distance = \request('distance');
        $order->delivery_price = $order->distance * $order->car->city->price->price;
        $order->save();

        $delivery_price = $order->delivery_price;

        $order->customer->wallet()->update([
            'amount'   =>  $order->customer->wallet->amount + (500 - $delivery_price),
            'reserved' =>  $order->customer->wallet->reserved - 500
        ]);
        $order->customer->walletTrack()->create([
            'price'     =>  $delivery_price,
            'process'   =>  'out',
            'direction' =>  [
                'order' =>  $order->id,
                'for'   =>  'delivery',
                'car_id'=>  $order->car_id
            ],
            'message'   =>  'Payed For delivery in Order #'.$order->id,
            'order_type'=>  'pay',
            'reason'    =>  'This Amount Payed For the delivery of An order You Made',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);
        if (is_null($order->car->wallet)) $order->car->wallet()->create(['amount'=>0]);

        $order->car->wallet->update(['reserved' =>  $order->car->wallet->reserved + $delivery_price]);
        $order->car->walletTrack()->create([
            'price'     =>  $delivery_price,
            'process'   =>  'reserve',
            'direction' =>  [
                'order' =>  $order->id,
                'customer_id'=>  $order->customer_id
            ],
            'message'   =>  'Reserved delivery for Order #'.$order->id,
            'order_type'=>  'reserve',
            'reason'    =>  'This Amount Reserved For An order You Have To Deliver',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        return \APIHelper::jsonRender('success.orderConf', $order->load(['customer:id,name,phone', 'place:id,lat,lon']));
    }
    public function waiting(Order $order)
    {
        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        $status = strtolower($order->status);
        if ($status !== 'confirmed by driver')
            return \APIHelper::error('orderNotConf');

        $isCancel = $order->cancel()->where('user_id', Auth::id())->where('relationship', 'car')->first();
        if ($isCancel)
            return \APIHelper::error('orderCancelAlready');

        $order->car_id = Auth::id();
        $order->status = 'driver waiting';
        $order->save();

        APIHelper::notification($order->branch, 'Driver is Waiting', "Driver Is Waiting \nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        unset($order->branch);

        return \APIHelper::jsonRender('success.changeStatus', $order->load('customer:id,name,phone'));
    }
    public function toCustomer(Order $order)
    {
        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        if (!$order->isReady)
            return \APIHelper::error('orderNotReady');

        $isCancel = $order->cancel()->where('user_id', Auth::id())->where('relationship', 'car')->first();
        if ($isCancel)
            return \APIHelper::error('orderCancelAlready');

        APIHelper::changeOrderStatusNumber($order,4);

        $order->car_id = Auth::id();
        $order->status = 'in-delivery';
        $order->start_time = now();
        $order->save();

        APIHelper::notification($order->customer, 'Driver is on his way', "Driver Is on his way\nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        unset($order->customer);

        return \APIHelper::jsonRender('success.changeStatus', $order->load('place:id,lat,lon'));
    }
    public function arrived(Order $order)
    {
        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        $status = strtolower($order->status);
        if ($status !== 'in-delivery')
            return \APIHelper::error('orderNotConf');

        $isCancel = $order->cancel()->where('user_id', Auth::id())->where('relationship', 'car')->first();
        if ($isCancel)
            return \APIHelper::error('orderCancelAlready');

        $order->car_id = Auth::id();
        $order->status = 'driver arrived';
        $order->save();

        APIHelper::changeOrderStatusNumber($order,5);

        APIHelper::notification($order->customer, 'Driver has arrived', "Driver has arrived\nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        unset($order->customer);

        return \APIHelper::jsonRender('success.changeStatus', []);
    }
    public function delivered(Order $order)
    {
        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        $status = strtolower($order->status);
        if ($status !== 'driver arrived')
            return \APIHelper::error('driverNotInPlace');

        $isCancel = $order->cancel()->where('user_id', Auth::id())->where('relationship', 'car')->first();
        if ($isCancel)
            return \APIHelper::error('orderCancelAlready');

        $order->car_id = Auth::id();
        $order->status = 'delivered';
        $order->deliver_time = now();
        $order->save();

        $order->car->wallet->update([
            'amount'   =>  $order->car->wallet->amount + $order->delivery_price,
            'reserved' =>  $order->car->wallet->reserved - $order->delivery_price
            ]);
        $order->car->walletTrack()->create([
            'price'     =>  $order->delivery_price,
            'process'   =>  'in',
            'direction' =>  [
                'order' =>  $order->id,
                'customer_id'=>  $order->customer_id
            ],
            'message'   =>  'Delivery for Order #'.$order->id,
            'order_type'=>  'get',
            'reason'    =>  'This Amount For An order You Delivered',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        APIHelper::notification($order->customer, 'Driver Just Delivered Your Order', "Please Confirm You Had Delivered The Order\nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        APIHelper::changeOrderStatusNumber($order,6);
        unset($order->customer);

        return \APIHelper::jsonRender('success.orderDeliver', []);
    }

    public function orderCancel(Order $order, Request $request)
    {
        $request->validate([
            'message'     => 'string|required|max:440',
        ]);

        if (\Gate::denies('showCar', $order))
            return \APIHelper::error('noPermission');

        $status = strtolower($order->status);
        if ($status !== 'confirmed by driver' && $status !== 'driver waiting' && $status !== 'ready')
            return \APIHelper::error('orderCantCancel');

        $order->customer->wallet()->update([
            'reserved' =>  $order->customer->wallet->reserved + $order->delivery_price
        ]);
        $order->customer->walletTrack()->create([
            'price'     =>  $order->delivery_price,
            'process'   =>  'reserve',
            'direction' =>  [
                'order' =>  $order->id,
                'for'   =>  'delivery',
                'car_id'=>  $order->car_id
            ],
            'message'   =>  'Back From delivery in Order #'.$order->id,
            'order_type'=>  'back',
            'reason'    =>  'This Amount Back From the delivery of An order You Made',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        $order->car->wallet(
            ['reserved' =>  $order->car->wallet->reserved - $order->delivery_price],
        );
        $order->car->walletTrack()->create([
            'price'     =>  $order->delivery_price,
            'process'   =>  'out',
            'direction' =>  [
                'order' =>  $order->id,
                'customer_id'=>  $order->customer_id
            ],
            'message'   =>  'Delivery for Order #'.$order->id,
            'order_type'=>  'get',
            'reason'    =>  'This Amount For An order You Delivered',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        $order->status = 'Confirmed By Vendor';
        $order->car_id = null;
        $order->distance = null;
        $order->delivery_price = null;
        $order->save();

        foreach (Admin::whereNotNull('FToken')->get() as $admin){
            APIHelper::notification($admin, 'order driver canceled',
                'driver ' . $order->car->name . ' canceled an order link :' . route('admin.order.show', $order)."\nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        }
        OrderNotification::dispatch($order)->delay(now()->addMinutes(3));

        $order->cancel()->create([
            'user_id'      => Auth::id(),
            'relationship' => 'Car',
            'message'      => $request->input('message'),
        ]);
        return \APIHelper::jsonRender('success.orderCancel', []);
    }

    public function rate(Request $request,Order $order)
    {
        $request->validate([
            'rate'  =>  'required|numeric|in:1,2,3,4,5',
            'message'=> 'required|string|max:440'
        ]);
        if ($order->isRated('customer')) return APIHelper::error('orderAlreadyRated');

        $order->customer->rates()->create([
            'car_id'    =>  Auth::id(),
            'rate'      =>  $request->input('rate'),
            'message'   =>  $request->input('message')
        ]);

        APIHelper::changeOrderStatusNumber($order,null, true);

        return APIHelper::jsonRender('success.success',[]);
    }

    public function reset()
    {
        \request()->user()->orders()->update([
            'status'    =>  'confirmed by vendor',
            'car_id'    =>  null,
            'distance'  =>  null,
            'start_time'=>  null,
            'delivery_price'=>null,
            'deliver_time'  =>null
        ]);

        return 'success';
    }
}
