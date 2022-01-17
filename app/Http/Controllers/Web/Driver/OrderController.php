<?php

namespace App\Http\Controllers\Web\Driver;

use App\DataTables\Driver\OrderDataTable;
use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Order;

class OrderController extends Controller
{
    public function index(OrderDataTable $dataTable)
    {
        Order::$show = true;
        return $dataTable->render('web.driver.order.index');
    }

    public function show(Order $order)
    {
        $order = Order::where('id',$order->id)->whereIn('car_id',\Auth::user()->cars->pluck('id')->toArray())->first();
        if (is_null($order))
            return redirect()->route('driver.order.index')->with('error', 'You Can not do that right now');

        $order->load([
            'items',
            'items.item',
            'customer',
            'customer.city',
            'branch.vendor',
            'branch.city',
            'car',
        ])->loadCount(['items'])->loadSum('items', 'amount');

        return view('web.driver.order.show.show', [
            'order' => $order,
        ]);
    }
}
