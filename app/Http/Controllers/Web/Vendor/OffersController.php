<?php

namespace App\Http\Controllers\Web\Vendor;

use App\DataTables\Vendor\OffersDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Vendor\Offer\UpdateRequest;
use App\Http\Requests\Vendor\Offer\StoreRequest;
use App\Models\Drink;
use App\Models\Extra;
use App\Models\Offer;

class OffersController extends Controller
{
    public function __construct()
    {
        Offer::$show = true;
        Offer::$forTable = true;
    }

    public function index()
    {
        if (!\Auth::user()->isRestaurant())
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        return (new OffersDataTable())->render('web.vendor.offers.index');
    }


    public function create()
    {
        if (!\Auth::user()->isRestaurant()){
            return redirect()->route('vendor.dashboard')->with('error', 'you are not restaurant');
        }
        $categories = \Auth::user()->categories()->get(['id', 'name_' . self::$lang . ' as name']);
        $drinks = Drink::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();
        $extras = Extra::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();

        return view('web.vendor.offers.create.create',[
            'drinks'      => $drinks,
            'extras'      => $extras,
            'categories'  => $categories,
        ]);
    }


    public function store(StoreRequest $request)
    {
        $offer = new Offer();
        $offer->vendor_id        = \Auth::id();
        $offer->name_ar          = $request->input('name_ar');
        $offer->description_ar   = $request->input('description_ar');
        $offer->name_en          = $request->input('name_en');
        $offer->description_en   = $request->input('description_en');
        $offer->price            = $request->input('price');
        $offer->amount           = $request->input('amount');
        $offer->category_id      = $request->input('category');
        $offer->img              = \Storage::disk('offers')->putFile('', $request->file('img'));
        $offer->with             = $request->input('with');
        $offer->without          = $request->input('without');
        $offer->size             = $request->input('size');
        $offer->extras           = $request->input('extras');
        $offer->drinks           = $request->input('drinks');
        $offer->save();


        return redirect()->route('vendor.offer.index', $offer)->with('success', 'offer created successfully');
    }


    public function edit(Offer $offer)
    {
        $offer = \Auth::user()->Offers()->findOrFail($offer->id);
        Offer::$withDetails = false;

        $categories = \Auth::user()->categories()->get(['id', 'name_' . self::$lang . ' as name']);
        $drinks = Drink::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();
        $extras = Extra::select('id', 'name_' .self::$lang . ' as name')->where('vendor_id', \Auth::id())->get();
        return view('web.vendor.offers.edit.edit', [
            'drinks'      => $drinks,
            'extras'      => $extras,
            'categories'  => $categories,
            'offer'        => $offer
        ]);
    }


    public function update(UpdateRequest $request, Offer $offer)
    {
        $offer = \Auth::user()->offers()->findOrFail($offer->id);
        $offer->name_ar          = $request->input('name_ar');
        $offer->description_ar   = $request->input('description_ar');
        $offer->name_en          = $request->input('name_en');
        $offer->description_en   = $request->input('description_en');
        $offer->price            = $request->input('price');
        $offer->amount           = $request->input('amount');
        $offer->category_id      = $request->input('category');
        $offer->with             = $request->input('with');
        $offer->without          = $request->input('without');
        $offer->size             = $request->input('size');
        $offer->extras           = $request->input('extras');
        $offer->drinks           = $request->input('drinks');

        if ($request->hasFile('img')) {
            $img = \Storage::disk('items')->putFile('', $request->file('img'));
            $offer->img = $img;
        }
        $offer->save();

        return redirect()->route('vendor.offer.index', $offer)->with('success', 'offer updated successfully');
    }
}
