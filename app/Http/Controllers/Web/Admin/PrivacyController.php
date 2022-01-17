<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\PrivacyDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\Settings\Privacy\StoreRequest;
use App\Models\Privacy;

class PrivacyController extends Controller
{
    public function index(PrivacyDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.privacy.index');
    }

    public function update(StoreRequest $request, $privacy)
    {
        $privacy = Privacy::where('id', $privacy)->first();
        $privacy->en = $request->input('en');
        $privacy->ar = $request->input('ar');
        $privacy->save();

        return redirect()->route('admin.settings.privacy.index')->with('success', 'Privacy created successfully');
    }
}
