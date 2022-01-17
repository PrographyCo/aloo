<?php

namespace App\Http\Middleware;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;
use Mcamara\LaravelLocalization\LaravelLocalization;

class checkLanguage
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle(Request $request, Closure $next)
    {
        return APIHelper::mustHaveHeader('accept-language', array_keys(\LaravelLocalization::getSupportedLocales()),$next,function ($header){
            Controller::$lang = $header;
            App::setLocale($header);
        });
    }
}
