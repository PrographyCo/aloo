<?php

namespace App\Http\Controllers\Api\V1_0_0\Car\Main;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class MainController extends Controller
{
    /**
     * profile
     */
    public function profile()
    {
        $user = request()->user();
        $city = $user->city->{'name_'.self::$lang};
        unset($user->city);
        $user->city = $city;
        return \APIHelper::jsonRender('',$user);
    }

    public function data()
    {
        // TODO: Do The Timing Shit
        $time = [
            'today' =>  1,
            'week'  =>  7,
            'month' =>  30,
            'year'  =>  365
        ];

        \request()->validate([
            'time'  =>  'in:'.implode(',',array_keys($time))
        ]);

        $user = request()->user();
        $return = new \stdClass();

        $return->name = $user->name;
        $return->img = $user->driver->img;
        $return->rate = $user->rate->first()->rate??0;
        $return->rides = $user->orders->count();
        $return->hours = $user->sum_orders_times->first()->delivery_time_sum;
        $return->income = number_format($user->sum_orders_prices(), 2);
        $return->exchanged = $user->transactions->sum('price');

        return \APIHelper::jsonRender('',$return,200,[]);
    }

    /**
     *  GET the wallet data
     */
    public function wallet()
    {
        $user = \request()->user();
        return \APIHelper::jsonRender('',[
            'wallet' => $user->wallet,
            'wallet_details' => $user->walletTrack()->limit(10)->orderBy('id', 'desc')->get(),
        ]);
    }

    public function setFirebaseToken(Request $request)
    {
        $request->validate([
            'token' =>  'required|string'
        ]);

        $request->user()->FToken = $request->input('token');
        $request->user()->save();

        return \APIHelper::jsonRender('success.tokenAdded',[]);
    }
}
