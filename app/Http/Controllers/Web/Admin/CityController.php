<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\CityDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\Settings\City\StoreRequest;
use App\Models\City;

class CityController extends Controller
{
    public function index(CityDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.city.index');
    }

    public function store(StoreRequest $request)
    {
        $city = new City();
        $city->name_en = $request->input('name_en');
        $city->name_ar = $request->input('name_ar');
        $city->price()->create([
            'price' => $request->input('price')
        ]);
        $city->save();
        return redirect()->route('admin.settings.city.index')->with('success', 'city created successfully');
    }

    public function update(StoreRequest $request, City $city)
    {
        $city->name_en = $request->input('name_en');
        $city->name_ar = $request->input('name_ar');
        $city->price->price = $request->input('price');
        $city->price->save();
        $city->save();

        return redirect()->route('admin.settings.city.index')->with('success', 'city created successfully');
    }
}
