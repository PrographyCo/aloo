<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\KitchenTypeDataTable;
use App\Http\Controllers\Controller;
use App\Models\KitchenType;
use Illuminate\Http\Request;
use App\Http\Requests\Admin\Settings\Bank\StoreRequest;

class KitchenTypeController extends Controller
{
    public function index(KitchenTypeDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.kitchen.index');
    }
    public function store(StoreRequest $request)
    {
        $type = new KitchenType();
        $type->name_en = $request->input('name_en');
        $type->name_ar = $request->input('name_ar');
        $type->save();
        return redirect()->route('admin.settings.kitchen.index')->with('success', 'kitchen created successfully');
    }
    public function update(StoreRequest $request, $type)
    {
        $type = KitchenType::findOrFail($type);
        $type->name_en = $request->input('name_en');
        $type->name_ar = $request->input('name_ar');
        $type->save();

        return redirect()->route('admin.settings.kitchen.index')->with('success', 'kitchen updated successfully');
    }
}
