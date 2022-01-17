<?php

namespace App\Http\Middleware;

use App\Helpers\APIHelper;
use App\Models\Config;
use Closure;
use Illuminate\Http\Request;

class CheckAppType
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
        if($request->user('api'))
        {
            $config = Config::where('key', $request->user('api')->phone_type)->first();
            if(is_null($config) || strtolower($config->value)==='false') return APIHelper::error('osNotSupported');
        }
        return $next($request);
    }
}
