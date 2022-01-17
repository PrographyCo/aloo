<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\Order\OrderCancelDataTable;
use App\DataTables\Admin\Order\OrderCompletedDataTable;
use App\DataTables\Admin\Order\OrderDataTable;
use App\DataTables\Admin\Order\OrderDelierDataTable;
use App\DataTables\Admin\Order\OrderNewDataTable;
use App\Http\Controllers\Controller;
use App\Models\Car;
use App\Models\Order;
use Illuminate\Http\Request;

class OrderController extends Controller
{
    public function index(OrderDataTable $dataTable)
    {
        $delivers = Car::all()->pluck('name', 'id')->toArray();
        return $dataTable->render('web.admin.order.index', [
            'deliver' => $delivers
        ]);
    }

    public function orderNew(OrderNewDataTable $dataTable)
    {
        $delivers = Car::all()->pluck('name', 'id')->toArray();
        return $dataTable->render('web.admin.order.index', [
            'deliver' => $delivers
        ]);
    }

    public function orderDeliver(OrderDelierDataTable $dataTable)
    {
        $delivers = Car::all()->pluck('name', 'id')->toArray();
        return $dataTable->render('web.admin.order.index', [
            'deliver' => $delivers
        ]);
    }

    public function orderCancel(OrderCancelDataTable $dataTable)
    {
        $delivers = Car::all()->pluck('name', 'id')->toArray();
        return $dataTable->render('web.admin.order.orderShowList', [
            'deliver' => $delivers,
            'title' => 'orders list cancel'
        ]);
    }

    public function orderCompleted(OrderCompletedDataTable $dataTable)
    {
        $delivers = Car::all()->pluck('name', 'id')->toArray();
        return $dataTable->render('web.admin.order.orderShowList', [
            'deliver' => $delivers,
            'title' => 'orders list completed'
        ]);
    }

    public function show(Order $order)
    {
        $order->load([
            'items',
            'items.item',
            'customer',
            'customer.city',
            'branch.vendor',
            'branch.city',
            'car',
        ])->loadCount(['items'])->loadSum('items', 'amount');

        return view('web.admin.order.show.show', [
            'order' => $order,
        ]);
    }

    public function changeDeliver(Order $order, Request $request)
    {
        if (!$deliver = $request->input('deliver_id')){
            return 'deliver not found';
        }
        Car::findOrFail($deliver);
        $order->car_id = $deliver;
        $order->save();
        return 'done change deliver';
    }
}
