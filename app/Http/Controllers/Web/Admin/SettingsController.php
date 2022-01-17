<?php

namespace App\Http\Controllers\Web\Admin;

use App\Http\Controllers\Controller;
use App\Models\Config;
use Illuminate\Http\Request;

class SettingsController extends Controller
{
    public function settings()
    {
        $configs = Config::all();
        return view('web.admin.settings.settings.index', [
            'android'   => $configs->where('key','android')->first()->value,
            'ios'       => $configs->where('key','ios')->first()->value,
            'distance'  => $configs->where('key','distance')->first()->value,
            'delivery'  => $configs->where('key','delivery_reservation')->first()->value,
        ]);
    }

    public function changeIosAppStatus()
    {
        $config = Config::where('key', 'ios')->first();
        $config->value = !$config->value;
        $config->save();
        return redirect()->route('admin.settings.general')->with('success', 'change status done');
    }

    public function changeAndroidAppStatus()
    {
        $config = Config::where('key', 'android')->first();
        $config->value = !$config->value;
        $config->save();
        return redirect()->route('admin.settings.general')->with('success', 'change status done');
    }


    public function changeDistance(Request $request)
    {
        if (!$request->input('distance')){
        return redirect()->back()->with('errors', 'the distance value is missed');
        }
        $config = Config::where('key', 'distance')->first();
        $config->value = $request->input('distance');
        $config->save();
        return redirect()->route('admin.settings.general')->with('success', 'change distance done');
    }
    public function changeDelivery(Request $request)
    {
        if (!$request->input('delivery')){
            return redirect()->with('errors', 'the delivery value is missed');
        }
        $config = Config::where('key', 'delivery_reservation')->first();
        $config->value = $request->input('delivery');
        $config->save();
        return redirect()->route('admin.settings.general')->with('success', 'change delivery done');
    }
}
