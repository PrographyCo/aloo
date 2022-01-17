<?php

use Illuminate\Support\Facades\Route;
use \App\Http\Controllers\Api\V1_0_0\Data\Help\DataController as Data;

Route::group([
    'prefix' => 'data'
], function (){
    Route::get('/faq/{service}', [Data::class, 'faq']);
    Route::get('/about',[Data::class,'about']);
    Route::get('/privacy/{service}', [Data::class, 'privacy']);
    Route::post('/clientService/{service}', [Data::class, 'clientService']);
    Route::get('/cities', [Data::class, 'cities']);
    Route::get('/banks', [Data::class, 'banks']);
    Route::get('/services', [Data::class, 'services']);
    Route::get('/restaurant', [Data::class, 'restaurantType']);
    Route::get('/kitchen', [Data::class, 'kitchenType']);
    Route::get('/vendor/{vendor}/categories', [Data::class, 'categories']);
    Route::get('/packages', [Data::class, 'packages']);
});
