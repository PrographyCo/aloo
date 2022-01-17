<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\DriverFormDataTable;
use App\DataTables\Admin\VendorFormDataTable;
use App\Http\Controllers\Controller;
use App\Models\Customer;
use App\Models\Item;
use App\Models\Order;
use Carbon\Carbon;

class MainController extends Controller
{
    public function dashboard()
    {
        $last10Orders       = Order::query()->with('customer', 'branch.vendor')->orderByDesc('created_at')->take(10)->get();
        $topItems           = Item::query()->with('vendor')->withSum('orderItem', 'amount')->orderByDesc('order_item_sum_amount')->take(10)->get();
        $countTodayCustomer = Customer::query()->whereDate('created_at', Carbon::today())->count();
        $countTodayOrders   = Order::query()->whereDate('created_at', Carbon::today())->count();
        $sumTodayOrdersPrice     = Order::query()->whereDate('created_at', Carbon::today())->sum('total_price');
        return view('web.admin.dashboard', [
            'last10Orders'         => $last10Orders,
            'topItems'             => $topItems,
            'countTodayCustomer'   => $countTodayCustomer,
            'countTodayOrders'     => $countTodayOrders,
            'sumTodayOrdersPrice'  => $sumTodayOrdersPrice,
        ]);
    }

    public function vendorForm(VendorFormDataTable $dataTable)
    {
        return $dataTable->render('web.admin.vendor.index', [
            'title' => 'all vendor waite confirm'
        ]);
    }

    public function driverForm(DriverFormDataTable $dataTable)
    {
        return $dataTable->render('web.admin.driver.index', [
            'title' => 'all driver that waite confirm'
        ]);
    }

    public function profile()
    {
        return view('web.admin.profile', [
            'user' => \Auth::user()
        ]);
    }
}
