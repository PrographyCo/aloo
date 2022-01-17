<?php

namespace App\Http\Controllers\Web\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Vendor as User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class AuthController extends Controller
{

    public function loginView()
    {
        if (Auth::guard('web-vendors')->check())
            return redirect()->route('vendor.dashboard');
        return view('web.vendor.login');
    }

    public function login(Request $request)
    {
        $this->validate($request,[
            'username'     => 'required|string|exists:'.(new User)->getTable().',phone',
            'password'     => 'required|string',
            'remember_me'  => 'boolean'
        ],[
            'username.exists' => 'login field username or password not correct'
        ]);

        $user = User::where('phone','=',$request->input('username'))->first();

        if(!\Hash::check($request->input('password'), $user->password) || $user->ban)
            return redirect()->back()->with('error', 'login field username or password not correct');

        Auth::guard('web-vendors')->login($user, $request->input('remember_me'));
        return redirect()->route('vendor.dashboard')->with('success', 'login successfully');
    }

    public function logout(Request $request)
    {
        Auth::logout();

        $request->session()->invalidate();

        $request->session()->regenerateToken();

        return redirect('/')->with('success', 'logout successfully');
    }
}
