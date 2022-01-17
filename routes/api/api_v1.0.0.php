<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

require 'V1.0.0/Data.php';
Route::group(['prefix' => 'customer'], function (){
    require 'V1.0.0/Customer.php';
});
Route::group(['prefix' => 'car'], function (){
    require 'V1.0.0/Car.php';
});
Route::group(['prefix' => 'branch'], function (){
    require 'V1.0.0/Branch.php';
});
require 'V1.0.0/Fallbacks.php';

//Notification:
Route::get('/{type}/profile/notification', [\App\Http\Controllers\Api\V1_0_0\Customer\Auth\ProfileController::class,'notifications']);
