<?php

namespace App\Http\Controllers\Api\V1_0_0\Branch\Orders;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Admin;
use App\Models\Order;
use Carbon\Carbon;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class OrdersController extends Controller
{
    protected $orderFilter = [
        'default'   =>  [
            'order' =>  'desc',
            'by'    =>  'id'
        ],
        'price_lowest'  =>  [
            'order' =>  'asc',
            'by'    =>  'total_price'
        ],
        'item_count_lowest'=>[
            'order' =>  'asc',
            'by'    =>  'items_sum_amount'
        ],
        'price_highest' =>  [
            'order' =>  'desc',
            'by'    =>  'total_price'
        ],
        'item_count_highest'=>[
            'order' =>  'desc',
            'by'    =>  'items_sum_amount'
        ]
    ];
    protected $filter = [
        'default'           => ['unconfirmed'],
        'cancelled'         => ['cancelled'],
        'current'           => ['confirmed by vendor', 'confirmed by driver', 'driver waiting', 'ready', 'in-delivery', 'driver arrived', 'delivered', 'delivery confirmed', 'done'],
    ];

    public function getOrders(Request $request, $filter='default')
    {
        $request->validate([
            'order_by'     => 'string|in:default,price_lowest,item_count_lowest,price_highest,item_count_highest|nullable',
            'search'       => 'string|max:250|nullable',
        ]);

        if ($request->input('order_by')===null)
            $orderFilter = $this->orderFilter['default'];
        else
            $orderFilter = $this->orderFilter[strtolower($request->input('order_by'))];

        $orderBy   = $request->input('order_by') ? $orderFilter['by'] : "";
        $orderType = $request->input('order_by') ? $orderFilter['order'] : "";
        $search    = $request->input('search') ?? "";

        $orders = $request->user()->orders();
        if ($search)
        {
            $search = '%'.$search.'%';
            $orders = $orders
                ->where(function (Builder $q) use ($search) {
                    $q->where('id', 'like', $search)
                        ->orWhere(function(Builder $query) use ($search){
                            $query->whereHas('customer', function(Builder $query) use ($search){
                                $query->where('name', 'like', $search);
                            });
                        })
                        ->orWhere(function(Builder $query) use ($search){
                            $query->whereHas('items.item', function(Builder $query) use ($search){
                                foreach (\LaravelLocalization::getSupportedLocales() as $lang => $prop)
                                {
                                    $query->where('name_'.$lang, 'like', $search);
                                }
                            });
                        });
                })
                ->whereIn('status', $this->filter[$filter]);
        }
        $orders = $orders->whereIn('status', $this->filter[$filter]);

        if ($orderBy!=='')
            $orders = $orders->orderBy($orderBy, $orderType);

        return \APIHelper::jsonRender('', \APIHelper::paginate($orders,null, function ($order) {
            $order->date = $order->created_at;
        }));
    }

    public function getOrderData(Order $order)
    {
        if (\Gate::denies('showVendor', $order))
        {
            return \APIHelper::error('noPermission');
        }
        $order->load(['items.item:id,name_'.self::$lang.' as name,img', 'customer:id,name,phone',])->loadSum('items', 'amount');
        $order->date = $order->created_at;
        return \APIHelper::jsonRender('', $order);

    }

    public function orderCancel(Order $order, Request $request)
    {
        $request->validate([
            'message'     => 'string|required|max:440',
        ]);
        if (\Gate::denies('showVendor', $order))
            return \APIHelper::error('noPermission');

        if ($order->status === 'unconfirmed')
            return \APIHelper::error('orderNotConf');

        if ($order->status !== 'driver waiting' && $order->status !== 'confirmed By driver' && $order->status !== 'confirmed By Vendor')
            return \APIHelper::error('orderCantCancel');

        APIHelper::notification($order->customer, 'Your order has been cancelled',"Your order has been cancelled\nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        foreach (Admin::whereNotNull('FToken')->get() as $admin){
            APIHelper::notification($admin, 'order canceled',
                $order->branch->vendor->brandName . ' canceled an order link :' . route('admin.order.show', $order)."\nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        }

        $order->status = 'cancelled';
        $order->save();
        $order->cancel()->create([
            'user_id'      => Auth::id(),
            'relationship' => 'branch',
            'message'      => $request->input('message'),
        ]);

        $order->customer->wallet()->update([
            'amount' =>  $order->customer->wallet->amount + $order->total_price
        ]);
        $order->customer->walletTrack()->create([
            'price'     =>  $order->total_price,
            'process'   =>  'in',
            'direction' =>  [
                'order' =>  $order->id,
                'branch_id'=>  $order->branch_id
            ],
            'message'   =>  'Vendor Canceled Order #'.$order->id,
            'order_type'=>  'back',
            'reason'    =>  'Your Order Had Been Canceled By Vendor',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        $order->branch->wallet->update([
            'reserved' =>  $order->branch->wallet->reserved - $order->total_price
        ]);
        $order->branch->walletTrack()->create([
            'price'     =>  $order->total_price,
            'process'   =>  'out',
            'direction' =>  [
                'order' =>  $order->id,
                'customer_id'=>  $order->customer_id
            ],
            'message'   =>  'for canceling Order #'.$order->id,
            'order_type'=>  'pay',
            'reason'    =>  'This Amount For An order You Have Canceled',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        return \APIHelper::jsonRender('success.changeStatus', $order);
    }

    public function orderConfirm(Order $order)
    {
        if (\Gate::denies('showVendor', $order))
            return \APIHelper::error('noPermission');

        $status = strtolower($order->status);
        if ($status !== 'unconfirmed')
            return \APIHelper::error('orderTaken');

        APIHelper::notification($order->customer, 'Your order has been confirmed',"Your order has been confirmed \nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        APIHelper::changeOrderStatusNumber($order,2);

        $order->status = 'confirmed by vendor';
        $order->save();

        $order->customer->wallet()->update([
            'reserved' =>  $order->customer->wallet->reserved - $order->total_price
        ]);
        $order->customer->walletTrack()->create([
            'price'     =>  $order->total_price,
            'process'   =>  'out',
            'direction' =>  [
                'order' =>  $order->id,
                'branch_id'=>  $order->branch_id
            ],
            'message'   =>  'Pay For Order #'.$order->id,
            'order_type'=>  'pay',
            'reason'    =>  'This Amount Payed For An order You Made',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        $order->branch->wallet->update([
            'reserved' =>  $order->branch->wallet->reserved + $order->total_price
        ]);
        $order->branch->walletTrack()->create([
            'price'     =>  $order->total_price,
            'process'   =>  'reserve',
            'direction' =>  [
                'order' =>  $order->id,
                'customer_id'=>  $order->customer_id
            ],
            'message'   =>  'Payed for Order #'.$order->id,
            'order_type'=>  'reserve',
            'reason'    =>  'This Amount For An order You Have To Make',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        return \APIHelper::jsonRender('success.changeStatus', $order);
    }

    public function orderReady(Order $order)
    {
        if (\Gate::denies('showVendor', $order))
            return \APIHelper::error('noPermission');

        $status = strtolower($order->status);
        if ($status !== 'confirmed by vendor' && $status !== 'driver waiting')
            return \APIHelper::error('orderNotConf.');

        APIHelper::changeOrderStatusNumber($order,3);

        $order->branch->wallet->update([
            'reserved' =>  $order->branch->wallet->reserved - $order->total_price,
            'amount'   =>  $order->branch->wallet->amount + $order->total_price
            ]);
        $order->branch->walletTrack()->create([
            'price'     =>  $order->total_price,
            'process'   =>  'in',
            'direction' =>  [
                'order' =>  $order->id,
                'customer_id'=>  $order->customer_id
            ],
            'message'   =>  'Payed for Order #'.$order->id,
            'order_type'=>  'get',
            'reason'    =>  'This Amount For An order You Have Made',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        $order->status = 'ready';
        $order->isReady = true;
        $order->save();
        return \APIHelper::jsonRender('success.changeStatus', $order);
    }

    public function reset()
    {
        \request()->user()->orders()->update([
            'status'    =>  'unconfirmed',
            'car_id'    =>  null,
            'distance'  =>  null,
            'start_time'=>  null,
            'delivery_price'=>null,
            'deliver_time'  =>null
        ]);

        return 'success';
    }
}
