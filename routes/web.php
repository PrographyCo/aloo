<?php

use App\Models\City;
use App\Models\KitchenType;
use App\Models\RestaurantTypes;
use App\Models\SupportedVendor;
use Illuminate\Support\Facades\App;
use Illuminate\Support\Facades\Route;
use Illuminate\Http\Request;
use App\Http\Controllers\Web\Home\HomeController as Home;



//>>>>>>>>>>>>>>>>>>>>>>>>>>> start home pages

Route::get('/', function () {
    $lang = Session::get('lang') ?? config('app.fallback_locale');
    return view('web.home.'.$lang.'.home');
})->name('home');

Route::get('/vendor-register', function () {
    $lang     = Session::get('lang') ?? config('app.fallback_locale');
    $cities   = City::all('id','name_'.$lang. ' as name');
    $supportedVendors = SupportedVendor::all('id','name_' . $lang . ' as name');
    $restaurantType  = RestaurantTypes::all('id','name_' . $lang . ' as name');
    $kitchenType     = KitchenType::all('id','name_' . $lang . ' as name');
    $banks = \App\Models\Bank::all('id','name_' . $lang . ' as name');

    return view('web.home.'.$lang.'.vendor_register', [
        'cities'            => $cities,
        'banks'             => $banks,
        'supportedVendors'  => $supportedVendors,
        'restaurantType'    => $restaurantType,
        'kitchenType'       => $kitchenType,
    ]);
})->name('vendor.register');
Route::post('vendor-register', [Home::class, 'registerVendor'])->name('vendor.register.send');


Route::get('/driver-register', function () {
    $lang    = Session::get('lang') ?? config('app.fallback_locale');
    $cities  = City::all('id','name_'.$lang. ' as name');
    $banks   = \App\Models\Bank::all('id','name_'.$lang. ' as name');
    return view('web.home.'.$lang.'.driver_register', [
        'cities' => $cities,
        'banks' => $banks,
    ]);
})->name('driver.register');

Route::post('driver-register', [Home::class, 'registerDriver'])->name('driver.register.send');

Route::get('/changeLang', function (Request $request) {
    Session::put('lang', $request->get('lang'));
    return redirect()->route('home');
})->name('changeLang');


//>>>>>>>>>>>>>>>>>>>>>>>>>>> end home pages


Route::group(['prefix' => config('app.adminUrl'), 'as' => 'admin.'], static function (){
    require 'web/admin.php';
});

Route::group(['prefix' => config('app.vendorUrl'), 'as' => 'vendor.'], static function (){
    require 'web/vendor.php';
});

Route::group(['prefix' => config('app.driverUrl'), 'as' => 'driver.'], static function (){
    require 'web/driver.php';
});


Route::group([
    'as' => 'images.'
], function (){
    Route::get('customer/images/{hash}', function ($image_name) {
        return Storage::disk('customers')->download($image_name);
    })->name('customer');

    Route::get('drivers/images/{hash}', function ($image_name) {
        return Storage::disk('drivers')->download($image_name);
    })->name('driver');

    Route::get('vendors/images/{hash}', function ($image_name) {
        return Storage::disk('vendors')->download($image_name);
    })->name('vendor');

    Route::get('items/images/{hash}', function ($image_name) {
        return Storage::disk('items')->download($image_name);
    })->name('item');

    Route::get('offers/images/{hash}', function ($image_name) {
        return Storage::disk('offers')->download($image_name);
    })->name('offer');

    Route::get('supported_vendor/images/{hash}', function ($image_name) {
        return Storage::disk('supported_vendor')->download($image_name);
    })->name('supported_vendor');

    Route::get('clientService/images/{hash}', function ($image_name) {
        return Storage::disk('clientServices')->download($image_name);
    })->name('clientService');
});


// Payment things:
Route::get('{lang}/payment/{token}/pay', function ($lang,$token){
    if (!array_key_exists($lang,\LaravelLocalization::getSupportedLocales())) return abort(404,'hi');
    $min = json_decode(\App\Helpers\EncDecHelper::dec(urldecode($token)),true)['min'];
    App::setLocale($lang);
    return view('Payment.'.$lang.'.index',compact('min','lang','token'));
})->name('payment');
Route::post('{lang}/payment/{token}/submit', function (Request $request,$lang,$token){
    if (!array_key_exists($lang,\LaravelLocalization::getSupportedLocales())) return abort(404);

    $url = (new \App\Helpers\ARBHelper($token))->getmerchanthostedPaymentid(
        $request->input('card'),
        $request->input('expiryMM'),
        $request->input('expiryYY'),
        $request->input('CVV'),
        $request->input('name'),
        $request->input('price')
    );
    if (!is_null($url))
        return redirect($url);

    return abort(403);
})->name('payment.post');

Route::any('/payment/error', function () { return 'error'; })->name('payment.error');
Route::any('/payment/success', function () {
    $arbPg = new \App\Helpers\ARBHelper();
    $result  = $arbPg->getresult(\request()->input('trandata'));
    if ($arbPg->isSuccess($result))
    {
        $id = json_decode(\App\Helpers\EncDecHelper::dec(urldecode($result[0]['udf1'])), true)['id'];
        $customer = \App\Models\Customer::find($id);

        $transId = $result[0]['transId'];
        $amount = $result[0]['amt'];

        $customer->wallet->update(
            ['amount'   =>  $customer->wallet->amount + $amount],
        );
        $customer->walletTrack()->create([
            'price'     =>  $amount,
            'process'   =>  'in',
            'direction' =>  $result,
            'message'   =>  'Deposited',
            'order_type'=>  'get',
            'reason'    =>  'This Amount Deposited In Customer\'s Wallet',
            'isTransaction'=>true,
            'transaction_id'=>$transId
        ]);

        return 'success';
    }
    return 'rejected';
})->name('payment.success');


//STC:

Route::get('{lang}/payment/stc/{token}/pay', function ($lang,$token){
    if (!array_key_exists($lang,\LaravelLocalization::getSupportedLocales())) return abort(404,'hi');
    $min = json_decode(\App\Helpers\EncDecHelper::dec(urldecode($token)), true, 512, JSON_THROW_ON_ERROR)['min'];
    App::setLocale($lang);
    return view('Payment.'.$lang.'.stc',compact('min','lang','token'));
})->name('payment.stc');
Route::any('{lang}/payment/stc/{token}/verify', function (Request $request,$lang,$token){
    if (!array_key_exists($lang,\LaravelLocalization::getSupportedLocales())) return abort(404,'hi');
    $response = APIHelper::stcPaymentRequest($request->input('phone'),$request->input('price'));
    if ($response->status()!==200){
        return redirect()->back()->withErrors(['public'=>$response->json()['Text']]);
    }

    return view('Payment.'.$lang.'.stcVerify')->with([
        'lang'  =>  $lang,
        'token' =>  $token,
        'phone' =>  $request->input('phone'),
        'price' =>  $request->input('price'),
        'OtpReference'  =>  $response->json()['DirectPaymentAuthorizeV4ResponseMessage']['OtpReference'],
        'STCPayPmtReference'=>  $response->json()['DirectPaymentAuthorizeV4ResponseMessage']['STCPayPmtReference']
    ]);

})->name('payment.stc.verify');
Route::post('{lang}/payment/stc/{token}/post', function (Request $request, $lang, $token) {
    if (!array_key_exists($lang,\LaravelLocalization::getSupportedLocales())) return abort(404,'hi');
    $data = json_decode(\App\Helpers\EncDecHelper::dec(urldecode($token)), true, 512, JSON_THROW_ON_ERROR);
    $response = \App\Helpers\APIHelper::stcPaymentApprove($request->input('OtpReference'),$request->input('verify_code'),$request->input('STCPayPmtReference'));

    if ($response->status()!==200) return redirect()->back()->withErrors(['public'=>$response->json()['Text']])->withInput(['phone'=>$request->input('phone'),'price' =>  $request->input('price')]);

    if ($response->json()['DirectPaymentConfirmV4ResponseMessage']['PaymentStatusDesc']==='Paid')
    {
        $customer = \App\Models\Customer::find($data['id']);
        $customer->wallet->update(
            ['amount'   =>  $customer->wallet->amount + $response->json()['DirectPaymentConfirmV4ResponseMessage']['Amount']],
        );
        $customer->walletTrack()->create([
            'price'     =>  $response->json()['DirectPaymentConfirmV4ResponseMessage']['Amount'],
            'process'   =>  'in',
            'direction' =>  $response->json(),
            'message'   =>  'Deposited',
            'order_type'=>  'get',
            'reason'    =>  'This Amount Deposited In Customer\'s Wallet',
            'isTransaction'=>true,
            'transaction_id'=>$response->json()['DirectPaymentConfirmV4ResponseMessage']['TokenId']
        ]);

        return 'success';
    }
    return $response->json()['DirectPaymentConfirmV4ResponseMessage']['PaymentStatusDesc']==='Paid';
})->name('payment.stc.post');
