<?php

namespace App\Http\Controllers\Api\V1_0_0\Customer\Main;

use App\Helpers\APIHelper;
use App\Helpers\EncDecHelper;
use App\Http\Controllers\Controller;
use App\Models\Package;
use Illuminate\Http\Request;
use Illuminate\Support\Carbon;

class PackagesController extends \App\Http\Controllers\Controller
{
    public function add(Request $request, Package $package)
    {
        if (!is_null($request->user()->packages()->first())) return APIHelper::error('PkgAlready');

        if ($request->user()->wallet->amount < $package->price) {
            $data = [
                'id'    =>  $request->user()->id,
                'min'   =>  $package->price - $request->user()->wallet->amount
            ];
            return APIHelper::error('NoMoney', [
                'url' => route('payment', ['lang' => Controller::$lang, 'token' => urlencode(EncDecHelper::enc(json_encode($data)))])
            ],[],[],499);
        }

        $request->user()->wallet()->update(
            ['amount'   =>  $request->user()->wallet->amount - $package->price],
        );
        $request->user()->walletTrack()->create([
            'price'     =>  $package->price,
            'process'   =>  'out',
            'direction' =>  [
                'package' =>  $package->id
            ],
            'message'   =>  'Payed For Package #'.$package->id.':'.$package->name_en,
            'order_type'=>  'pay',
            'reason'    =>  'This Amount Payed For A Package You Ordered',
            'isTransaction'=>false,
            'transaction_id'=>null
        ]);

        $oldPackage = $request->user()->packages()->where('package_id',$package->id)->where('status','done')->first();
        if ($oldPackage)
            $oldPackage->update([
                'orders'     =>   $oldPackage->amount + $package->orders,
                'expiry_date'=> (new Carbon($oldPackage->expiry_date))->addDays($package->days)
            ]);
        else
            $request->user()->packages()->create([
                'package_id'    =>  $package->id,
                'expiry_date'   =>  (new Carbon())->addDays($package->days),
                'orders'        =>  $package->orders,
            ]);

        return APIHelper::jsonRender('success.success',[],200,[]);
    }

    public function cancel(Request $request, Package $package)
    {
        $oldPackage = $request->user()->packages()->where('package_id',$package->id)->where('status','done')->first();
        $oldPackage->update([
            'status'    =>  'cancelled'
        ]);

        return APIHelper::jsonRender('success.success',[],200,[]);
    }
}
