<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\KitchenTypeDataTable;
use App\DataTables\Admin\RestaurantTypeDataTable;
use App\Http\Controllers\Controller;
use App\Models\KitchenType;
use App\Models\RestaurantTypes;
use Illuminate\Http\Request;
use App\Http\Requests\Admin\Settings\Bank\StoreRequest;

class RestaurantTypeController extends Controller
{
    public function index(RestaurantTypeDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.restaurant.index');
    }
    public function store(StoreRequest $request)
    {
        $type = new RestaurantTypes();
        $type->name_en = $request->input('name_en');
        $type->name_ar = $request->input('name_ar');
        $type->save();
        return redirect()->route('admin.settings.restaurant.index')->with('success', 'restaurant created successfully');
    }
    public function update(StoreRequest $request, $type)
    {
        $type = RestaurantTypes::findOrFail($type);
        $type->name_en = $request->input('name_en');
        $type->name_ar = $request->input('name_ar');
        $type->save();

        return redirect()->route('admin.settings.restaurant.index')->with('success', 'restaurant updated successfully');
    }
}
