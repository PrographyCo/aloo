<?php

namespace App\Http\Controllers\Api\V1_0_0\Car\Auth;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Carbon\Carbon;
use App\Models\Car as User;

class AuthController extends Controller
{
    /**
     * Login user and create token
     *
     * @param  [string] email
     * @param  [string] password
     * @param  [boolean] remember_me
     * @return [string] access_token
     * @return [string] token_type
     * @return [string] expires_at
     */
    public function login(Request $request)
    {
        $request->validate([
            'login_number' => 'required|string|exists:'.(new User)->getTable().',login_number',
            'password'     => 'required|string',
//            'type' => 'required|in:android,ios'
        ]);

        $user = User::where('login_number','=',$request->input('login_number'))->first();

        if(!\Hash::check($request->input('password'), $user->password)) return APIHelper::error('unauthorized');
        if ($user->ban) return APIHelper::error('banned');

        Auth::login($user);
//        $user->phone_type = strtolower($request->input('type'));
//        $user->save();

        $tokenResult = $user->createToken('Car Access Token');
        $token = $tokenResult->token;
        $token->expires_at = Carbon::now()->addYears(3);
        $token->save();

        $user->token = new \stdClass();
        $user->token->access_token = $tokenResult->accessToken;
        $user->token->token_type = 'Bearer';
        $user->token->expires_at = Carbon::parse(
            $tokenResult->token->expires_at
        )->toDateTimeString();
        return APIHelper::jsonRender('success.login',$user);
    }

    /**
     * Logout user (Revoke the token)
     *
     * @return [string] message
     */
    public function logout(Request $request)
    {
        $request->user()->token()->revoke();
        return APIHelper::jsonRender('success.login',[]);
    }
}
