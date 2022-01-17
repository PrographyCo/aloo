<?php

namespace App\Http\Controllers\Web\Vendor;

use App\DataTables\Vendor\CategoryDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Vendor\Category\StoreRequest;
use App\Http\Requests\Vendor\Category\UpdateRequest;
use App\Models\Category;

class CategoryController extends Controller
{

    public function index(CategoryDataTable $dataTable)
    {
        return $dataTable->render('web.vendor.category.index');
    }


    public function create()
    {
        return view('web.vendor.category.create.create');
    }

    public function store(StoreRequest $request)
    {
        $category = new Category();
        $category->vendor_id = \Auth::id();
        $category->name_ar = $request->input('name_ar');
        $category->description_ar = $request->input('description_ar');
        $category->name_en = $request->input('name_en');
        $category->description_en = $request->input('description_en');
        $category->save();
        return redirect()->route('vendor.category.show', $category)->with('success', 'category created successfully');
    }

    public function show(Category $category)
    {
        $category = \Auth::user()->categories()->findOrFail($category->id);
        return view('web.vendor.category.show.show', [
            'category' => $category
        ]);
    }


    public function edit(Category $category)
    {
        $category = \Auth::user()->categories()->findOrFail($category->id);
        return view('web.vendor.category.edit.edit', [
            'category' => $category
        ]);
    }


    public function update(UpdateRequest $request, Category $category)
    {
        $category = \Auth::user()->categories()->findOrFail($category->id);
        $category->name_ar = $request->input('name_ar');
        $category->description_ar = $request->input('description_ar');
        $category->name_en = $request->input('name_en');
        $category->description_en = $request->input('description_en');
        $category->save();
        return redirect()->route('vendor.category.show', $category)->with('success', 'category updated successfully');
    }
}
