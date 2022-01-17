<?php

namespace App\Http\Middleware;

use App\Helpers\APIHelper;
use Closure;
use Illuminate\Http\Request;

class APIVersion
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle(Request $request, Closure $next, $guard)
    {
        config(['app.api.version' => $guard]);
        return APIHelper::mustHaveHeader('accept', 'application/json',$next);
    }
}
