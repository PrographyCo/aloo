<?php

namespace App\Http\Controllers\Web\Driver;

use App\Http\Controllers\Controller;
use App\Models\Order;
use Carbon\Carbon;

class MainController extends Controller
{
    public function dashboard()
    {
        $cars_id             = \Auth::user()->cars->pluck('id')->toArray();
        $countCars           = count($cars_id);
        $last10Orders        = Order::query()->with('customer', 'branch.vendor')->whereIn('car_id', $cars_id)->orderByDesc('created_at')->take(10)->get();
        $countMonthOrders    = Order::query()->whereIn('car_id', $cars_id)->whereMonth('created_at', Carbon::now()->month)->count();
        $sumMonthOrdersPrice = Order::query()->whereIn('car_id', $cars_id)->whereMonth('created_at', Carbon::now()->month)->sum('total_price');
        return view('web.driver.dashboard', [
            'countCars'            => $countCars,
            'last10Orders'         => $last10Orders,
            'countMonthOrders'     => $countMonthOrders,
            'sumMonthOrdersPrice'  => $sumMonthOrdersPrice,
        ]);
    }

    public function profile()
    {
        return view('web.driver.profile', [
            'user' => \Auth::user()
        ]);
    }
}
