<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\BankDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\Settings\Bank\StoreRequest;
use App\Models\Bank;

class BankController extends Controller
{
    public function index(BankDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.bank.index');
    }

    public function store(StoreRequest $request)
    {
        $city = new Bank();
        $city->name_en = $request->input('name_en');
        $city->name_ar = $request->input('name_ar');
        $city->save();
        return redirect()->route('admin.settings.bank.index')->with('success', 'bank created successfully');
    }
}
