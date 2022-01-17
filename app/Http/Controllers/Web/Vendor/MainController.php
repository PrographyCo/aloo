<?php

namespace App\Http\Controllers\Web\Vendor;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Admin as User;
use App\Models\Customer;
use App\Models\Item;
use App\Models\Order;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class MainController extends Controller
{
    public function dashboard()
    {
        $last10Orders        = \Auth::user()->orders()->with('customer', 'branch.vendor')->orderByDesc('orders.created_at')->take(10)->get();
        $topItems            = Item::query()->where('vendor_id', Auth::id())->withSum('orderItem', 'amount')->orderByDesc('order_item_sum_amount')->take(10)->get();
        $countMonthOrders    = \Auth::user()->orders()->whereMonth('orders.created_at', Carbon::now()->month)->count();
        $countItem           = Item::query()->where('vendor_id', Auth::id())->count();
        $sumMonthOrdersPrice = \Auth::user()->orders()->whereMonth('orders.created_at', Carbon::now()->month)->sum('total_price');

        return view('web.vendor.dashboard', [
            'last10Orders'         => $last10Orders,
            'topItems'             => $topItems,
            'sumMonthOrdersPrice'  => $sumMonthOrdersPrice,
            'countMonthOrders'     => $countMonthOrders,
            'countItem'            => $countItem,
        ]);
    }

    public function profile()
    {
        return view('web.vendor.profile', [
            'user' => \Auth::user()
        ]);
    }
}
