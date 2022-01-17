<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\AboutDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\Settings\About\StoreRequest;
use App\Models\About;

class AboutController extends Controller
{
    public function index(AboutDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.About.index');
    }

    public function update(StoreRequest $request, About $about)
    {
        $about->link = $request->input('link');
        $about->save();

        return redirect()->route('admin.settings.about')->with('success', 'About Data Saved successfully');
    }
}
