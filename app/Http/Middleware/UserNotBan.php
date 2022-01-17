<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class UserNotBan
{
    public function handle(Request $request, Closure $next)
    {
        if ($request->user() && $request->user()->ban)
        {
            return redirect()->route('home')->with('errors', 'you cannot login right now please call us');
        }
        return $next($request);
    }
}
