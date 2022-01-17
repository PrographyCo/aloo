<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Web\Vendor\AuthController as Auth;
use App\Http\Controllers\Web\Vendor\MainController as Main;
use App\Http\Controllers\Web\Vendor\BranchController as Branch;
use App\Http\Controllers\Web\Vendor\ItemController as Item;
use App\Http\Controllers\Web\Vendor\CategoryController as Category;
use App\Http\Controllers\Web\Vendor\DrinkController as Drink;
use App\Http\Controllers\Web\Vendor\OffersController as Offers;
use App\Http\Controllers\Web\Vendor\ExtraController as Extra;
use App\Http\Controllers\Web\Vendor\OrderController as Order;

Route::get('login', [Auth::class, 'loginView'])->name('login');

Route::post('signIn', [Auth::class, 'login'])->name('signIn');

Route::group(['middleware' => ['web', 'auth:web-vendors']], static function (){
    Route::get('logout', [Auth::class, 'logout'])->name('logout');
    Route::get('dashboard', [Main::class, 'dashboard'])->name('dashboard');
    Route::get('profile', [ Main::class, 'profile'])->name('profile');

    Route::get('orders', [Order::class, 'index'])->name('orders.index');
    Route::get('orders/{order}', [Order::class, 'show'])->name('orders.show');
    Route::get('orders/confirm/{order}', [Order::class, 'confirm'])->name('orders.confirm');
    Route::get('orders/ready/{order}', [Order::class, 'ready'])->name('orders.ready');
    Route::delete('orders/cancel/{order}', [Order::class,'cancel'])->name('orders.cancel');

    Route::resource('branch', Branch::class)->except('destroy');
    Route::post('branch/{branch}/changeAmount/{item}', [Branch::class, 'changeAmount'])->name('branch.changeAmount');
    Route::post('branch/{branch}/wallet', [Branch::class, 'sendMoney'])->name('branch.wallet');

    Route::resource('item', Item::class)->except('destroy');
    Route::resource('category', Category::class)->except('destroy');
    Route::resource('drink', Drink::class)->except('destroy', 'show');
    Route::resource('offer', Offers::class)->except('destroy');
    Route::resource('extra', Extra::class)->except('destroy', 'show');
});
