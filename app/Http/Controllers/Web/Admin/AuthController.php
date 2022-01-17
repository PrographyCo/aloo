<?php

namespace App\Http\Controllers\Web\Admin;

use App\Http\Controllers\Controller;
use App\Models\Admin as User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class AuthController extends Controller
{
    public function loginView()
    {
        if (\Illuminate\Support\Facades\Auth::check())
            return redirect()->route('admin.dashboard');
        return view('web.admin.login');
    }
    public function login(Request $request)
    {

        $this->validate($request,[
            'username'     => 'required|string|exists:'.(new User)->getTable().',login_number',
            'password'     => 'required|string',
            'remember_me'  => 'boolean'
        ],[
            'username.exists' => 'login field username or password not correct'
        ]);

        $user = User::where('login_number','=',$request->input('username'))->first();

        if(!\Hash::check($request->input('password'), $user->password) || $user->ban)
            return redirect()->back()->with('error', 'login field username or password not correct');

        Auth::login($user, $request->input('remember_me'));
        return redirect()->route('admin.dashboard')->with('success', 'login successfully');
    }

    public function logout(Request $request)
    {
        Auth::logout();

        $request->session()->invalidate();

        $request->session()->regenerateToken();

        return redirect('/')->with('success', 'logout successfully');
    }

    public function setFirebaseToken(Request $request)
    {
        $request->validate([
            'token' =>  'required|string'
        ]);

        $request->user()->FToken = $request->input('token');
        $request->user()->save();

        return \APIHelper::jsonRender('success.tokenAdded',[]);
    }
}
