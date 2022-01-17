<?php

namespace App\Http\Controllers\Api\V1_0_0\Customer\Auth;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Config;
use App\Models\Customer as User;
use App\Models\CustomerPasswordReset;
use App\Models\SupportedVendor;
use App\Models\Verification;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Carbon\Carbon;
use Illuminate\Testing\Fluent\Concerns\Has;
use Illuminate\Validation\Rule;

class AuthController extends Controller
{
    /**
     * Create user
     *
     * @param  [string] name
     * @param  [string] email
     * @param  [string] password
     * @param  [string] password_confirmation
     * @return [string] message
     */
    public function signup(Request $request)
    {
        $request->validate([
            'name' => 'required|string',
            'phone' => 'required|string|unique:'.(new User)->getTable().',phone',
            'gender'=>  'required|in:male,female,other,prefer not to say',
            'city'  =>  'required|numeric|exists:cities,id',
            'password' => 'required|string|confirmed|min:8'
        ]);

        $user = User::create([
            'name' => $request->input('name'),
            'phone' => $request->input('phone'),
            'password' => bcrypt($request->input('password')),
            'gender' => $request->input('gender'),
            'city_id' => $request->input('city')
        ]);

        APIHelper::sendVerification('phone', $user);

        return APIHelper::jsonRender('success.signup', $user);
    }

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
            'phone' => 'required|string|exists:'.(new User)->getTable().',phone',
            'password' => 'required|string|min:8',
            'type' => 'required|in:android,ios'
        ]);
        $config = Config::where('key', strtolower($request->input('type')))->first();
        if(is_null($config) || strtolower($config->value)==='false') return APIHelper::error('osNotSupported');

        $user = User::where('phone','=',$request->input('phone'))->first();

        if(!\Hash::check($request->input('password'), $user->password)) return APIHelper::error('unauthorized');
        if ($user->ban) return APIHelper::error('banned');
        if (!$user->hasVerifiedPhone()) return APIHelper::error('verifyPhoneFirst');

        Auth::login($user);
        $user->phone_type = strtolower($request->input('type'));
        $user->save();

        \DB::delete('Delete FROM '.(new CustomerPasswordReset())->getTable().' WHERE phone="'.$request->input('phone').'"');

        $tokenResult = $user->createToken('Customer Access Token');
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
     * Verify user's phone
     *
     * @returns [string] message
     */
    public function verifyPhone(Request $request)
    {
        $request->validate([
            'phone'       => 'required|string|exists:'.(new User)->getTable().',phone',
            'verify_code' => 'required|string'
        ]);

        $user = User::where('phone','=',$request->input('phone'))->first();

        if ($user->hasVerifiedPhone()) return APIHelper::error('alreadyVerified');

        $verify = Verification::where('user_id',$user->id)
            ->where('user_type',strtolower(class_basename(new User())))
            ->where('verify_for','phone')
            ->first();
        if (is_null($verify)) return APIHelper::error('noRecord');
        if (! \Hash::check($request->input('verify_code'), $verify->code)) return APIHelper::error('codeNotRight');

        $user->phone_verified_at = now();
        $user->save();
        $verify->delete();

        Auth::login($user);

        $tokenResult = $user->createToken('Customer Access Token');
        $token = $tokenResult->token;

        if ($request->remember_me) $token->expires_at = Carbon::now()->addWeeks(1);
        $token->save();

        $user->token = new \stdClass();
        $user->token->access_token = $tokenResult->accessToken;
        $user->token->token_type = 'Bearer';
        $user->token->expires_at = Carbon::parse(
            $tokenResult->token->expires_at
        )->toDateTimeString();

        return APIHelper::jsonRender('success.phoneVerify',$user);
    }

    /**
     * Verify user's email
     *
     * @returns [string] message
     */
    public function verifyEmail(Request $request)
    {
        $request->validate([
            'email' => 'required|string|exists:'.(new User)->getTable().',email',
            'verify_code' => 'required|string'
        ]);

        $user = User::where('email','=',$request->input('email'))->first();

        if ($user->hasVerifiedPhone()) return APIHelper::error('alreadyVerified');

        $verify = Verification::where('user_id',$user->id)
            ->where('user_type',strtolower(class_basename(new User())))
            ->where('verify_for','email')
            ->first();

        if (is_null($verify)) return APIHelper::error('noRecord');
        if (! \Hash::check($request->input('verify_code'), $verify->code)) return APIHelper::error('codeNotRight');

        $user->email_verified_at = now();
        $user->save();
        $verify->delete();
        return APIHelper::jsonRender('success.emailVerify',[]);
    }

    /**
     * Logout user (Revoke the token)
     *
     * @return [string] message
     */
    public function logout(Request $request)
    {
        $request->user()->token()->revoke();
        return APIHelper::jsonRender('success.logout',[]);
    }

    public function delete(Request $request)
    {
        $request->validate([
            'phone' => 'required|string|exists:'.(new User)->getTable().',phone',
        ]);

        if (User::where('phone', '=', $request->input('phone'))->delete())
        {
            return APIHelper::jsonRender('user Deleted Successfully',[]);
        }

        return APIHelper::error('error deleting user data');
    }


//    resend verify code phone
    public function sendVerifyPhone(Request $request,$phone=null)
    {
        $request->validate([
            'phone' => [
                Rule::requiredIf($phone===null),
                'string',
                'exists:'.(new User)->getTable().',phone'
            ],
        ]);

        $user = User::where('phone', $phone??$request->input('phone'))->first();

        if (!$user) return APIHelper::error('noUser');
        if ($user->hasVerifiedPhone()) return APIHelper::error('alreadyVerified');

        APIHelper::sendVerification('phone', $user);
        return APIHelper::jsonRender('success.codeSend', []);
    }

//    resend verify code for email
    public function sendVerifyEmail(Request $request)
    {
        $request->validate([
            'email' => 'required|string|exists:'.(new User)->getTable().',email',
        ]);

        $user = User::where('email', $request->input('email'))->first();

        if (!$user) return APIHelper::error('noUser');
        if ($user->hasVerifiedEmail()) return APIHelper::error('alreadyVerified');

        APIHelper::sendVerification('email', $user);
        return APIHelper::jsonRender('success.codeSend', []);
    }

    public function forgetPassword(Request $request)
    {
        $request->validate([
            'phone' => 'required|string|exists:'.(new User)->getTable().',phone',
        ]);
        $phone = $request->input('phone');

        $user = User::where('phone', $phone)->first();
        $code = (env('APP_ENV')==='testing' || env('APP_ENV')==='local')? 1234:random_int(1111,9999);
        $reset = CustomerPasswordReset::where('phone', $request->input('phone'))->first();
        if (!$reset)
            CustomerPasswordReset::create([
                'phone' =>  $user->phone,
                'token' =>  \Hash::make($code)
            ]);
        else
            $reset->update([
                'code'  =>  \Hash::make($code)
            ]);
        APIHelper::sendSMS($user->phone, 'Reset Password Code Is: '.$code );

        return
APIHelper::jsonRender('success.codeSend',['code'=>$code,
'env'=>(env('APP_ENV')==='testing' || env('APP_ENV')==='local')]);
    }

    public function reset(Request $request)
    {
        $request->validate([
            'phone' => 'required|string|exists:'.(new User)->getTable().',phone',
            'code'  => 'required|required',
            'password'=>'required|string'
        ]);

        $user = User::where('phone', $request->input('phone'))->first();
        $reset = CustomerPasswordReset::where('phone', $request->input('phone'))->first();

        if (!$reset) return APIHelper::error('resNoRecord');
        if (!\Hash::check($request->input('code'), $reset->token))
            return APIHelper::error('resCodeNotRight');

        \DB::delete('Delete FROM '.(new CustomerPasswordReset())->getTable().' WHERE phone="'.$request->input('phone').'"');
        $user->update([
            'password'  =>  \Hash::make($request->input('password'))
        ]);

        return APIHelper::jsonRender('success.resetSuc',[]);
    }
}
