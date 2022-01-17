<?php

namespace App\Http\Controllers\Web\Vendor;

use App\DataTables\Vendor\DrinkDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Vendor\Drink\StoreRequest;
use App\Models\Drink;
use App\Models\Item;
use Illuminate\Http\Request;

class DrinkController extends Controller
{

    public function index()
    {
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        return (new DrinkDataTable())->render('web.vendor.drink.index');
    }


    public function create()
    {
        if (!\Auth::user()->isRestaurant()){
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        }
        return view('web.vendor.drink.create.create');
    }


    public function store(StoreRequest $request)
    {
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        $drink = new Drink();
        $drink->name_ar          = $request->input('name_ar');
        $drink->name_en          = $request->input('name_en');
        $drink->price            = $request->input('price');
        $drink->calories         = $request->input('calories');
        $drink->vendor_id        = \Auth::id();
        $drink->save();

        return redirect()->route('vendor.drink.index')->with('success', 'drink created successfully');
    }

    public function edit(Drink $drink)
    {
        $drink = \Auth::user()->drinks()->findOrFail($drink->id);
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        return view('web.vendor.drink.edit.edit', [
            'drink' => $drink
        ]);
    }


    public function update(StoreRequest $request, Drink $drink)
    {
        $drink = \Auth::user()->drinks()->findOrFail($drink->id);
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        $drink->name_ar          = $request->input('name_ar');
        $drink->name_en          = $request->input('name_en');
        $drink->price            = $request->input('price');
        $drink->calories         = $request->input('calories');
        $drink->save();
        return redirect()->route('vendor.drink.index')->with('success', 'category updated successfully');
    }
}
