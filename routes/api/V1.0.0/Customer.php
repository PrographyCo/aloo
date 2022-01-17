<?php

use Carbon\Carbon;
use Illuminate\Support\Facades\Route;
use \App\Http\Controllers\Api\V1_0_0\Customer\Auth\ProfileController as Profile;
use \App\Http\Controllers\Api\V1_0_0\Customer\Auth\AuthController as Auth;
use \App\Http\Controllers\Api\V1_0_0\Customer\Main\PublicController as Main;
use \App\Http\Controllers\Api\V1_0_0\Customer\Main\CartController as Cart;
use \App\Http\Controllers\Api\V1_0_0\Customer\Main\OrdersController as Order;
use \App\Http\Controllers\Api\V1_0_0\Customer\Main\PackagesController as Packages;

// Auth:
Route::group([
    'prefix' => 'auth'
], function (){
    Route::post('login', [Auth::class, 'login']);
    Route::post('signup', [Auth::class, 'signup']);

    Route::post('send_verify/phone', [Auth::class, 'sendVerifyPhone']);
    Route::post('verify/phone', [Auth::class, 'verifyPhone']);

    Route::post('send_verify/email', [Auth::class, 'sendVerifyEmail']);
    Route::post('verify/email', [Auth::class, 'verifyEmail']);

    Route::post('password/forget', [Auth::class, 'forgetPassword']);
    Route::post('password/reset', [Auth::class, 'reset']);

    if (env('APP_ENV') === 'local' || env('app_env') === 'testing')
    {
        Route::delete('delete', [Auth::class, 'delete']);
    }

    Route::group(['middleware' => 'auth:api'], function () {
        Route::get('logout', [Auth::class, 'logout']);
    });
});

// Profile:
Route::group([
    'prefix' => 'profile',
    'middleware' => 'auth:api'
], function (){

    Route::get('show', [Profile::class, 'user']);
    Route::post('edit', [Profile::class, 'editProfile']);

    Route::get('places', [Profile::class, 'places']);

    Route::get('places/default', [Profile::class, 'defaultPlace']);

    Route::post('places/new', [Profile::class, 'placesNew']);
    Route::delete('places/delete/{place}', [Profile::class, 'deletePlace']);

    Route::post('token', [Profile::class, 'setFirebaseToken']);

    Route::get('wallet', [Profile::class, 'wallet']);
    Route::post('wallet/payment/apple', [Profile::class, 'applePay']);
});

// Favorite:
Route::group([
    'prefix' => 'favorite',
    'middleware' => 'auth:api'
], function (){
    Route::get('/{service}', [Profile::class, 'getFavoriteVendors']);
    Route::get('/vendor/{vendor}', [Profile::class, 'getFavoriteByVendorId']);
    Route::post('add', [Profile::class, 'addToFavorite']);
    Route::delete('delete/{fav}', [Profile::class, 'delFromFavorite']);
});

// Main:
Route::group([
    'prefix' => 'main',
], function (){
    Route::get('list/{service}', [Main::class, 'list']);
    Route::post('list/{service}', [Main::class, 'list']);
    Route::get('list/items/{vendor}', [Main::class, 'items']);
    Route::post('list/items/{vendor}', [Main::class, 'items']);
    Route::get('view/item/{item}', [Main::class, 'viewItem']);
    Route::get('view/offer/{offer}', [Main::class, 'viewOffer']);

    Route::group([
        'middleware' => 'auth:api',
        'prefix' => 'cart'
    ], function () {

        Route::get('/list/{vendor_type}', [Cart::class, 'list']);
        Route::post('/add', [Cart::class, 'add']);
        Route::get('/{item}/view', [Cart::class, 'view']);
        Route::post('/{item}/edit', [Cart::class, 'edit']);
        Route::delete('{item}/delete', [Cart::class, 'delete']);

        Route::get('/toOrder/{vendor_type}', [Cart::class, 'convertToOrder']);

        if (env('APP_ENV') === 'local' || env('APP_ENV') === 'testing')
        {
            Route::any('/addMoney',[Cart::class,'addMoney']);
        }
    });
});

// Orders:
Route::group([
    'prefix' => 'orders',
    'middleware' => 'auth:api'
], function (){
    Route::get('list/{supported_vendor}', [Order::class, 'listOrders']);
    Route::get('show/{order}', [Order::class, 'showOrder']);
});

// Rate:
Route::group([
    'prefix' => 'rate',
    'middleware' => 'auth:api'
], function (){
    Route::post('{type}/{order}', [Order::class, 'rate']);
});

// Packages:
Route::group([
    'prefix' => 'packages',
    'middleware' => 'auth:api'
], function (){
    Route::get('add/{package}', [Packages::class, 'add']);
    Route::get('cancel/{package}', [Packages::class, 'cancel']);
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
    })->middleware('auth:api');
}
