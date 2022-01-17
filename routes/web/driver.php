<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Web\Driver\AuthController as Auth;
use App\Http\Controllers\Web\Driver\MainController as Main;
use App\Http\Controllers\Web\Driver\CarController as Car;
use App\Http\Controllers\Web\Driver\OrderController as Order;

Route::get('login',static function (){
    if (\Illuminate\Support\Facades\Auth::guard('web-drivers')->check())
        return redirect()->route('driver.dashboard');
    return view('web.driver.login');
})->name('login');

Route::post('signIn', [Auth::class, 'login'])->name('signIn');

Route::group(['middleware' => ['web', 'auth:web-drivers']], static function (){
    Route::get('logout', [Auth::class, 'logout'])->name('logout');
    Route::get('dashboard', [Main::class, 'dashboard'])->name('dashboard');
    Route::get('profile', [ Main::class, 'profile'])->name('profile');

    Route::get('orders', [Order::class, 'index'])->name('orders.index');
    Route::get('orders/{order}', [Order::class, 'show'])->name('orders.show');

    Route::resource('car', Car::class)->except('delete');
    Route::post('car/{car}/changePassword', [Car::class, 'changePassword'])->name('car.changePassword');
    Route::post('car/{car}/wallet', [Car::class, 'sendMoney'])->name('car.wallet');

});
