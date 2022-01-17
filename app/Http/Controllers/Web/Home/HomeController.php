<?php

namespace App\Http\Controllers\Web\Home;

use App\Http\Controllers\Controller;
use App\Http\Requests\Home\DriverRegisterRequest;
use App\Http\Requests\Home\VendorRegisterRequest;
use App\Models\Driver;
use App\Models\Vendor;

class HomeController extends Controller
{
    public function registerVendor(VendorRegisterRequest $request)
    {
        $vendor = new Vendor();
        $vendor->legalName           = $request->input('legalName');
        $vendor->brandName           = $request->input('brandName');
        $vendor->commercialNo        = $request->input('commercialNo');
        $vendor->city_id             = $request->input('city');
        $vendor->description         = $request->input('description');
        $vendor->supported_vendor_id = $request->input('supported_vendor');
        $vendor->email               = $request->input('email');
        $vendor->phone               = $request->input('phone');
        $vendor->bank_id             = $request->input('bank');
        $vendor->bankIBAN            = $request->input('iban');
        $vendor->bankRecipientName   = $request->input('beneficiaryName');
        $vendor->confirm             = false;
        $vendor->ban                 = true;
        $vendor->password            = \Hash::make(\Str::random(10));

        $commercialRecord = \Storage::disk('vendors')->putFile('', $request->file('commercialRecord'));
        $vendor->commercialRecord =  $commercialRecord;
        $logo = \Storage::disk('vendors')->putFile('', $request->file('logo'));
        $vendor->logo  = $logo;
        if ($request->input('supported_vendor') != 3){
            $image = \Storage::disk('vendors')->putFile('', $request->file('image'));
            $vendor->image = $image;
        } else {
            $vendor->image = $logo;
        }

        $speech = \Storage::disk('vendors')->putFile('', $request->file('speech'));
        $vendor->speech = $speech;
        $vendor->save();

        if($vendor->isRestaurant()){
            $vendor->data()->create([
                'restaurant_type_id' => $request->input('restaurant_type'),
                'kitchen_type_id' => $request->input('kitchen_type'),
            ]);
        }
        return redirect()->route('home')->with('success', 'your register is waiting approve');
    }

    public function registerDriver(DriverRegisterRequest $request)
    {
        $driver = new Driver();
        $driver->name                = $request->input('name');
        $driver->phone               = $request->input('phone');
        $driver->email               = $request->input('email');
        $driver->password            = \Hash::make(\Str::random());
        $driver->bank_id             = $request->input('bank');
        $driver->bankRecipientName   = $request->input('beneficiaryName');
        $driver->confirm             = false;
        $driver->ban                 = true;

        $image = \Storage::disk('drivers')->putFile('', $request->file('img'));
        $driver->img = $image;
        $iban = \Storage::disk('drivers')->putFile('', $request->file('iban'));
        $driver->iban =  $iban;
        $driver->save();

        return redirect()->route('home')->with('success', 'your register is waiting approve');
    }
}
