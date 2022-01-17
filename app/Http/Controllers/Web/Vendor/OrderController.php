<?php

namespace App\Http\Controllers\Web\Vendor;

use App\DataTables\Vendor\OrderDataTable;
use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Order;
use Carbon\Carbon;

class OrderController extends Controller
{
    public function index(OrderDataTable $dataTable)
    {
        return $dataTable->render('web.vendor.order.index');
    }

    public function show(Order $order)
    {
        \Auth::user()->orders()->findOrFail($order->id);
        $order->load([
            'items',
            'items.item',
            'customer',
            'customer.city',
            'branch.vendor',
            'branch.city',
            'car',
        ])->loadCount(['items'])->loadSum('items', 'amount');

        return view('web.vendor.order.show.show', [
            'order' => $order,
        ]);
    }
    public function confirm(Order $order)
    {
        $order->status = 'confirmed by vendor';
        $order->save();

        APIHelper::notification($order->customer, 'Your order has been confirmed',"Your order has been confirmed\nOrder Id: #".$order->id."\n Order Placed In: ".Carbon::make($order->created_at)->format('Y-M-D H:i:s'));
        APIHelper::changeOrderStatusNumber($order,2);

        return redirect()->route('vendor.orders.index')->with('success', 'status changed successfully');
    }

    public function ready(Order $order)
    {
        $order->status = 'ready';
        $order->isReady = true;
        $order->save();

        APIHelper::changeOrderStatusNumber($order,3);

        return redirect()->route('vendor.orders.index')->with('success', 'status changed successfully');
    }

    public function cancel(Order $order)
    {
        $order->status = 'cancelled';
        $order->save();
        $order->cancel()->create([
            'user_id'   =>  request()->user()->id,
            'relationship'=>'vendor',
            'message'   =>  'Deleted By Vendor'
        ]);
        APIHelper::changeOrderStatusNumber($order,0);
        return redirect()->route('vendor.orders.index')->with('success', 'status changed successfully');
    }
}
