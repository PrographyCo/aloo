<?php

use Illuminate\Support\Facades\Route;

Route::fallback(function(){
    return \APIHelper::jsonRender('errors.notFound', [], 404,[],['public'=>['404 Not Found']]);
})->name('api.fallback.404');
