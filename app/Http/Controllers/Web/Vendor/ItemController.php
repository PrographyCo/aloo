<?php

namespace App\Http\Controllers\Web\Vendor;

use App\DataTables\Vendor\Item\BranchDataTable;
use App\DataTables\Vendor\ItemDataTable;
use App\DataTables\Vendor\ItemRestaurantDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Vendor\Item\StoreRequest;
use App\Http\Requests\Vendor\Item\UpdateRequest;
use App\Models\Branch;
use App\Models\Drink;
use App\Models\Extra;
use App\Models\Item;
use Illuminate\Http\Request;

class ItemController extends Controller
{

    public function index()
    {
        if (\Auth::user()->supported_vendor_id == 3)
            return (new ItemRestaurantDataTable())->render('web.vendor.item.index');

        return (new ItemDataTable())->render('web.vendor.item.index');
    }


    public function create()
    {
        $categories = \Auth::user()->categories()->get(['id', 'name_' . self::$lang . ' as name']);
        if (\Auth::user()->supported_vendor_id == 3) {
            $drinks = Drink::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();
            $extras = Extra::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();
            return view('web.vendor.item.create.create_restaurant', [
                'drinks'      => $drinks,
                'extras'      => $extras,
                'categories'  => $categories,
            ]);
        }

        return view('web.vendor.item.create.create', [
            'categories'  => $categories,
        ]);
    }


    public function store(StoreRequest $request)
    {
        $item = new Item();
        $item->name_ar          = $request->input('name_ar');
        $item->brief_desc_ar    = $request->input('brief_desc_ar');
        $item->description_ar   = $request->input('description_ar');
        $item->name_en          = $request->input('name_en');
        $item->brief_desc_en    = $request->input('brief_desc_en');
        $item->description_en   = $request->input('description_en');
        $item->price            = $request->input('price');
        $item->amount           = $request->input('amount');
        $item->category_id      = $request->input('category');
        $item->vendor_id        = \Auth::id();

        $img = \Storage::disk('items')->putFile('', $request->file('img'));
        $item->img = $img;

        if (\Auth::user()->supported_vendor_id == 3)
        {
            $item->amount_type  = 'calories';
            $item->optionals    = $request->input('optionals');
            $item->sizes        = $request->input('size');
            $item->extras       = $request->input('extras');
            $item->drinks       = $request->input('drinks');
        } else {
            $item->amount_type  = $request->input('amount_type');

            foreach ($request->file('images') ?? [] as $img)
            {
                $images[] = \Storage::disk('items')->putFile('', $img);
            }
            $item->images = $images ?? [];

        }
        $item->save();

        $branches = Branch::where('vendor_id', \Auth::id())->get('id')->pluck('id');
        foreach ($branches as $branch){
            $data[$branch] = ['amount' => (\Auth::user()->supported_vendor_id == 3 ? -1 : 0)];
        }
        $item->branches()->sync($data ?? []);

        return redirect()->route('vendor.item.show', $item)->with('success', 'item created successfully');
    }


    public function show(Item $item)
    {
        $item = \Auth::user()->items()->findOrFail($item->id);
        if (\Auth::user()->supported_vendor_id != 3)
            Item::$withDetails = false;
        return (new BranchDataTable($item))->render('web.vendor.item.show.show', [
            'item' => $item
        ]);
    }


    public function edit(Item $item)
    {
        $item = \Auth::user()->items()->findOrFail($item->id);
        Item::$withDetails = false;
        $categories = \Auth::user()->categories()->get(['id', 'name_' . self::$lang . ' as name']);
        if (\Auth::user()->supported_vendor_id == 3) {

            $drinks = Drink::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();
            $extras = Extra::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();
            return view('web.vendor.item.edit.edit_restaurant', [
                'drinks'      => $drinks,
                'extras'      => $extras,
                'categories'  => $categories,
                'item'        => $item
            ]);
        }
        else{
            return view('web.vendor.item.edit.edit', [
                'categories'  => $categories,
                'item'        => $item,
            ]);
        }
    }


    public function update(UpdateRequest $request, Item $item)
    {
        $item = \Auth::user()->items()->findOrFail($item->id);
        $item->name_ar          = $request->input('name_ar');
        $item->brief_desc_ar    = $request->input('brief_desc_ar');
        $item->description_ar   = $request->input('description_ar');
        $item->name_en          = $request->input('name_en');
        $item->brief_desc_en    = $request->input('brief_desc_en');
        $item->description_en   = $request->input('description_en');
        $item->price            = $request->input('price');
        $item->amount           = $request->input('amount');
        $item->category_id      = $request->input('category');

        if ($request->hasFile('img')) {
            $img = \Storage::disk('items')->putFile('', $request->file('img'));
            $item->img = $img;
        }
        if (\Auth::user()->supported_vendor_id == 3)
        {
            $item->optionals    = $request->input('optionals');
            $item->sizes        = $request->input('size');
            $item->extras       = $request->input('extras');
            $item->drinks       = $request->input('drinks');
        } else {
            $item->amount_type  = $request->input('amount_type');
            if ($request->hasFile('images')){
                foreach ($request->file('images') ?? [] as $img)
                    $images[] = \Storage::disk('items')->putFile('', $img);
                $item->images = $images ?? [];
            }
        }
        $item->save();

        return redirect()->route('vendor.item.show', $item)->with('success', 'item updated successfully');
    }
}
