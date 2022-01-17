<?php

namespace App\Http\Middleware;

use App\Helpers\APIHelper;
use Closure;
use Illuminate\Http\Request;

class HaveType
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle(Request $request, Closure $next,...$guards)
    {
        $allowedTypes = ['email','backup_email','phone','backup_phone'];
        if (strtolower($request->route('type'))===$guards[0] && !in_array(strtolower($request->route('type')), $allowedTypes, true))
        {
            return APIHelper::error('verType',['allowed_types'=>$allowedTypes]);
        }

        return $next($request);
    }
}
