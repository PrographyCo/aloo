<?php

use Carbon\Carbon;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\V1_0_0\Branch\Auth\AuthController as Auth;
use App\Http\Controllers\Api\V1_0_0\Branch\Main\MainController as Main;
use App\Http\Controllers\Api\V1_0_0\Branch\Orders\OrdersController as Order;


Route::group(['prefix' => 'auth'], function (){
    Route::post('login', [Auth::class, 'login']);
    Route::group(['middleware' => 'auth:api-branches'], function () {
        Route::get('logout', [Auth::class, 'logout']);
    });
});

Route::group(['middleware' => 'auth:api-branches'], function () {

    Route::get('getStatus', [Main::class, 'getStatus']);
    Route::post('changeAvailableStatus', [Main::class, 'changeStatusAvailable']);

    Route::get('profile', [Main::class, 'profile']);
    Route::get('wallet', [Main::class, 'wallet']);
    Route::get('data', [Main::class, 'data']);
    Route::post('token', [Main::class, 'setFirebaseToken']);

    Route::get('items', [Main::class, 'items']);

    Route::get('categories', [Main::class, 'category']);

    Route::group(['prefix' => 'order'], function () {
        Route::get('list/{filter?}', [Order::class, 'getOrders']);

        Route::get ('{order}', [Order::class, 'getOrderData']);
        Route::post('status/{order}/cancel', [Order::class, 'orderCancel']);
        Route::post('status/{order}/confirm', [Order::class, 'orderConfirm']);
        Route::post('status/{order}/ready', [Order::class, 'orderReady']);

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
    })->middleware('auth:api-branches');
}
