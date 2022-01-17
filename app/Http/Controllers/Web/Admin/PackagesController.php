<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\PackagesDataTable;
use App\Http\Controllers\Controller;
use App\Models\Bank;
use App\Models\City;
use App\Models\KitchenType;
use App\Models\Package;
use App\Models\RestaurantTypes;
use App\Http\Requests\Admin\Settings\Package\StoreRequest;
use App\Models\SupportedVendor;

class PackagesController extends Controller
{
    public function index(PackagesDataTable $dataTable)
    {
//        dd(Package::find(1));
        Package::$hideName = false;
        return $dataTable->render('web.admin.settings.packages.index');
    }
    public function create()
    {
        return view('web.admin.settings.packages.create.create');
    }
    public function store(StoreRequest $request)
    {
        $package = new Package();
        $package->name_en = $request->input('name_en');
        $package->name_ar = $request->input('name_ar');
        $package->price = $request->input('price');
        $package->days = $request->input('days');
        $package->type = $request->input('type');

        $package->orders = ($package->type==='freeDelivery')?$request->input('orders'):0;
        $package->discount = ($package->type==='discount')?$request->input('discount'):null;
        $package->discount_type = ($package->type==='discount')?$request->input('discount_type'):null;

        $package->save();
        return redirect()->route('admin.settings.packages.index')->with('success', 'package created successfully');
    }

    public function edit(Package $package)
    {
        Package::$hideName = false;
        return view('web.admin.settings.packages.edit.edit', [
            'package'   =>  $package
        ]);
    }
    public function update(StoreRequest $request,Package $package)
    {
        Package::$hideName = false;
        $package->name_en = $request->input('name_en');
        $package->name_ar = $request->input('name_ar');
        $package->price = $request->input('price');
        $package->days = $request->input('days');

        $package->orders = ($package->type==='freeDelivery')?$request->input('orders'):0;
        $package->discount = ($package->type==='discount')?$request->input('discount'):null;
        $package->discount_type = ($package->type==='discount')?$request->input('discount_type'):null;
        $package->save();

        return redirect()->route('admin.settings.packages.index')->with('success', 'package updated successfully');
    }

    public function destroy(Package $package)
    {
        $package->delete();
        return redirect()->route('admin.settings.packages.index')->with('success', 'package deleted successfully');
    }
}
