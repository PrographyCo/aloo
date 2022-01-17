<?php

use Carbon\Carbon;
use Illuminate\Support\Facades\Route;
use \App\Http\Controllers\Api\V1_0_0\Car\Main\MainController as Main;
use \App\Http\Controllers\Api\V1_0_0\Car\Auth\AuthController as Auth;
use \App\Http\Controllers\Api\V1_0_0\Car\Order\OrderController as Order;

Route::group(['prefix' => 'auth'], function (){
    Route::post('login', [Auth::class, 'login']);
    Route::group(['middleware' => 'auth:api-cars'], function () {
        Route::get('logout', [Auth::class, 'logout']);
    });
});

Route::group(['middleware' => 'auth:api-cars'], function (){
    Route::get('profile', [Main::class, 'profile']);
    Route::get('profile/data', [Main::class, 'data']);
    Route::get('wallet', [Main::class, 'wallet']);
    Route::post('token', [Main::class, 'setFirebaseToken']);

    Route::group(['prefix' => 'orders'], function (){

        Route::get('get', [Order::class, 'orders']);

        Route::get('view/{order}', [Order::class, 'order']);

        Route::get('view/current/{order}', [Order::class, 'viewOrderData']);

        Route::post('status/{order}/cancel', [Order::class, 'orderCancel']);

        Route::post('status/{order}/confirm', [Order::class, 'confirm']);
        Route::post('status/{order}/waiting', [Order::class, 'waiting']);
        Route::post('status/{order}/toCustomer', [Order::class, 'toCustomer']);
        Route::post('status/{order}/arrived', [Order::class, 'arrived']);
        Route::post('status/{order}/delivered', [Order::class, 'delivered']);

        Route::post('rate/{order}', [Order::class, 'rate']);

        Route::get('current', [Order::class, 'getOrderCurrent']);
//        Route::get('cancel', [Order::class, 'getOrderCancel']);

        if (env('APP_ENV')==='local' || env('APP_ENV')==='testing')
        {
            Route::delete('reset', [Order::class, 'reset']);
        }
    });
});

// Push Notification:
if (env('APP_ENV')==='local' || env('APP_ENV')==='testing')
{
    Route::get('/push',function (\Illuminate\Http\Request $request){
        $request->validate([
            'title' =>  'required|string',
            'body'  =>  'required|string'
        ]);

        return \App\Helpers\APIHelper::notification($request->user(), $request->input('title'), $request->input('body')."\nOrder Id: #1234\n Order Placed In: ".Carbon::make(Carbon::now())->format('Y-M-D H:i:s'));
    })->middleware('auth:api-cars');
}
