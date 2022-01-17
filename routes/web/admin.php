<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Web\Admin\AuthController as Auth;
use App\Http\Controllers\Web\Admin\MainController as Main;
use App\Http\Controllers\Web\Admin\VendorController as Vendor;
use App\Http\Controllers\Web\Admin\DriverController as Driver;
use App\Http\Controllers\Web\Admin\OrderController as Order;
use App\Http\Controllers\Web\Admin\CustomerController as Customer;
use App\Http\Controllers\Web\Admin\CityController as City;
use App\Http\Controllers\Web\Admin\PrivacyController as Privacy;
use App\Http\Controllers\Web\Admin\BankController as Bank;
use App\Http\Controllers\Web\Admin\KitchenTypeController as kitchen;
use App\Http\Controllers\Web\Admin\RestaurantTypeController as restaurant;
use App\Http\Controllers\Web\Admin\PackagesController as package;
use App\Http\Controllers\Web\Admin\FaqController as faq;
use App\Http\Controllers\Web\Admin\ClientServiceController as ClientService;
use App\Http\Controllers\Web\Admin\AboutController as About;
use App\Http\Controllers\Web\Admin\SettingsController as Setting;

Route::get('login',[Auth::class, 'loginView'])->name('login');
Route::post('signIn', [Auth::class, 'login'])->name('signIn');

Route::group(['middleware' => ['web', 'auth:web']], static function (){
//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> auth and dashboard <<<<<<<<<<<<<<<<<<<<<<<<<<

    Route::get('logout', [Auth::class, 'logout'])->name('logout');
    Route::get('dashboard', [Main::class, 'dashboard'])->name('dashboard');
    Route::post('save-token', [Auth::class, 'setFirebaseToken'])->name('save-token');
    Route::get('profile', [ Main::class, 'profile'])->name('profile');

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> auth and dashboard <<<<<<<<<<<<<<<<<<<<<<<<<<


//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start order <<<<<<<<<<<<<<<<<<<<<<<<<<

    Route::get('orders', [Order::class, 'index'])->name('order.index');
    Route::get('orders/{order}', [Order::class, 'show'])->name('order.show');
    Route::post('orders/{order}/changeDeliver', [Order::class, 'changeDeliver'])->name('order.changeDeliver');
    Route::get('orders/index/new', [Order::class, 'orderNew'])->name('order.datatable.new');
    Route::get('orders/index/deliver', [Order::class, 'orderDeliver'])->name('order.datatable.deliver');
    Route::get('orders/index/cancel', [Order::class, 'orderCancel'])->name('order.datatable.cancel');
    Route::get('orders/index/completed', [Order::class, 'orderCompleted'])->name('order.datatable.completed');

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> end order <<<<<<<<<<<<<<<<<<<<<<<<<<


//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start register <<<<<<<<<<<<<<<<<<<<<<<<<<
    Route::group(['prefix' => 'register', 'as' => 'register.'], function (){
        Route::get('vendor', [Main::class, 'vendorForm'])->name('vendor');
        Route::get('driver', [Main::class, 'driverForm'])->name('driver');
    });
//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> end register <<<<<<<<<<<<<<<<<<<<<<<<<<


//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start vendor <<<<<<<<<<<<<<<<<<<<<<<<<<
    Route::resource('vendor', Vendor::class)->except('destroy');
    Route::get('vendor/{vendor}/service/{type}', [Vendor::class, 'serviceDataTable'])->name('vendor.datatable');
    Route::get('vendor/index/restaurant', [Vendor::class, 'vendorRestaurant'])->name('vendor.datatable.restaurant');
    Route::get('vendor/index/supermarket', [Vendor::class, 'vendorSupermarket'])->name('vendor.datatable.supermarket');
    Route::get('vendor/index/pharmacy', [Vendor::class, 'vendorPharmacy'])->name('vendor.datatable.pharmacy');
    Route::get('vendor/{vendor}/confirm', [Vendor::class, 'confirm'])->name('vendor.confirm');
    Route::post('vendor/{vendor}/ban', [Vendor::class, 'stopVendor'])->name('vendor.ban');

    Route::post('vendor/{vendor}/wallet', [Vendor::class, 'showWallets'])->name('vendor.wallet');

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> end vendor <<<<<<<<<<<<<<<<<<<<<<<<<<


//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start driver <<<<<<<<<<<<<<<<<<<<<<<<<<

    Route::resource('driver', Driver::class)->except('destroy');
    Route::get('driver/{driver}/confirm', [Driver::class, 'confirm'])->name('driver.confirm');
    Route::post('driver/{driver}/ban', [Driver::class, 'stopDriver'])->name('driver.ban');
    Route::post('driver/{driver}/wallet', [Driver::class, 'showWallets'])->name('driver.wallet');


//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> end driver <<<<<<<<<<<<<<<<<<<<<<<<<<

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start customer <<<<<<<<<<<<<<<<<<<<<<<<<<

    Route::resource('customer', Customer::class)->only(['index', 'show', 'update', 'destroy']);
    Route::get('customer/index/todayNew', [Customer::class, 'todayNew'])->name('customer.datatable.todayNew');

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> end customer <<<<<<<<<<<<<<<<<<<<<<<<<<

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start settings <<<<<<<<<<<<<<<<<<<<<<<<<<

    Route::group(['prefix' => 'settings', 'as' => 'settings.'], function (){
        Route::resource('city', City::class)->only(['index', 'store']);
        Route::post('city/{city}',[City::class, 'update']);

        Route::resource('privacy', Privacy::class)->only(['index', 'store']);
        Route::post('privacy/{privacy}',[Privacy::class, 'update']);

        Route::resource('bank', Bank::class)->only(['index', 'store']);

        Route::resource('kitchen', kitchen::class)->only(['index', 'store']);
        Route::post('kitchen/{kitchen}',[kitchen::class, 'update']);

        Route::resource('restaurant', restaurant::class)->only(['index', 'store']);
        Route::post('restaurant/{restaurant}',[restaurant::class, 'update']);

        Route::resource('packages', package::class)->except('show');
        Route::resource('faq', faq::class)->except('show');

        Route::get('clientService',[ClientService::class,'index'])->name('clientService');
        Route::get('about',[About::class,'index'])->name('about');
        Route::post('about/{about}',[About::class,'update'])->name('about.update');

        Route::get('', [Setting::class, 'settings'])->name('general');

        Route::post('changeIosStatus', [Setting::class, 'changeIosAppStatus'])->name('change.iosStatus');
        Route::post('changeAndroidAppStatus', [Setting::class, 'changeAndroidAppStatus'])->name('change.AndroidStatus');
        Route::post('changeDistance', [Setting::class, 'changeDistance'])->name('change.distance');
        Route::post('changeDelivery', [Setting::class, 'changeDelivery'])->name('change.delivery');
    });
//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>> end settings <<<<<<<<<<<<<<<<<<<<<<<<<<


});
