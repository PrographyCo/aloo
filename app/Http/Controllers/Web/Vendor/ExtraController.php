<?php

namespace App\Http\Controllers\Web\Vendor;

use App\DataTables\Vendor\ExtraDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Vendor\Extra\StoreRequest;
use App\Models\Extra;
use Illuminate\Http\Request;

class ExtraController extends Controller
{

    public function index()
    {
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        return (new ExtraDataTable())->render('web.vendor.extra.index');
    }


    public function create()
    {
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        return view('web.vendor.extra.create.create');
    }


    public function store(StoreRequest $request)
    {
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        $extra = new Extra();
        $extra->name_ar          = $request->input('name_ar');
        $extra->name_en          = $request->input('name_en');
        $extra->price            = $request->input('price');
        $extra->calories         = $request->input('calories');
        $extra->vendor_id        = \Auth::id();
        $extra->save();
        return redirect()->route('vendor.extra.index')->with('success', 'extra created successfully');
    }

    public function edit(Extra $extra)
    {
        $extra = \Auth::user()->extras()->findOrFail($extra->id);
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        return view('web.vendor.extra.edit.edit', [
            'extra' => $extra
        ]);
    }


    public function update(StoreRequest $request, Extra $extra)
    {
        $extra = \Auth::user()->extras()->findOrFail($extra->id);
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        $extra->name_ar          = $request->input('name_ar');
        $extra->name_en          = $request->input('name_en');
        $extra->price            = $request->input('price');
        $extra->calories         = $request->input('calories');
        $extra->save();
        return redirect()->route('vendor.extra.index')->with('success', 'extra updated successfully');
    }
}
