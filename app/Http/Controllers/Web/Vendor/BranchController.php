<?php

namespace App\Http\Controllers\Web\Vendor;

use App\DataTables\Vendor\Branch\ItemDataTable;
use App\DataTables\Vendor\Branch\ItemRestaurantDataTable;
use App\DataTables\Vendor\BranchDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Vendor\Branch\StoreRequest;
use App\Http\Requests\Vendor\Branch\UpdateRequest;
use App\Models\Branch;
use App\Models\City;
use App\Models\Item;
use Illuminate\Http\Request;

class BranchController extends Controller
{

    public function index(BranchDataTable $dataTable)
    {
        return $dataTable->render('web.vendor.branch.index');
    }


    public function create()
    {
        $cities           = City::all('id','name_'.self::$lang. ' as name');
        return view('web.vendor.branch.create.create', [
            'cities'           => $cities,
        ]);
    }

    public function store(StoreRequest $request)
    {
        $numbers = Branch::select('login_number')->get()->pluck('login_number')->toArray();
        do{
            $login_number = (string)random_int(1000000000, 9999999999);
        }while (in_array($login_number, $numbers, true));
        if ($login_number<0) $login_number*=-1;

        $branch = new Branch();
        $branch->name             = $request->input('name');
        $branch->vendor_id        = \Auth::id();
        $branch->password         = \Hash::make($request->input('password'));
        $branch->city_id          = $request->input('city');
        $branch->manager          = $request->input('manager');
        $branch->managerPhone     = $request->input('managerPhone');
        $branch->managerEmail     = $request->input('managerEmail');
        $branch->managerPosition  = $request->input('managerPosition');
        $branch->lon              = $request->input('longitude');
        $branch->lat              = $request->input('latitude');
        $branch->login_number     = $login_number;
        $branch->save();

        $branch->wallet()->create([
            'amount' => 0,
        ]);
        $items = Item::where('vendor_id', \Auth::id())->get('id')->pluck('id');
        foreach ($items as $item){
            $data[$item] = ['amount' => (\Auth::user()->supported_vendor_id == 3 ? -1 : 0)];
        }
        $branch->item()->sync($data ?? []);
        return redirect()->route('vendor.branch.show', $branch)->with('success', 'branch created successfully');
    }


    public function show(Branch $branch)
    {
        $branch = \Auth::user()->branches()->findOrFail($branch->id);
        $branch->load('vendor');
        if ($branch->vendor->isRestaurant()){
            return (new ItemRestaurantDataTable($branch->id))->render('web.vendor.branch.show.show', [
                'branch' => $branch
            ]);
        }
        return (new ItemDataTable($branch->id))->render('web.vendor.branch.show.show', [
            'branch' => $branch
        ]);
    }

    public function edit(Branch $branch)
    {
        $branch = \Auth::user()->branches()->findOrFail($branch->id);
        $cities = City::all('name_'.self::$lang . ' as name', 'id');
        return view('web.vendor.branch.edit.edit', [
            'branch' => $branch,
            'cities' => $cities
        ]);
    }

    public function update(UpdateRequest $request, Branch $branch)
    {
        $branch = \Auth::user()->branches()->findOrFail($branch->id);
        $branch->name            = $request->input('name');
        $branch->city_id         = $request->input('city');
        $branch->manager         = $request->input('manager');
        $branch->managerPhone    = $request->input('managerPhone');
        $branch->managerEmail    = $request->input('managerEmail');
        $branch->managerPosition = $request->input('managerPosition');
//        $branch->lat             = $request->input('latitude');
//        $branch->lon             = $request->input('longitude');
        $branch->save();
        return redirect()->route('vendor.branch.show', $branch)->with('success', 'branch updated successfully');
    }

    public function changeAmount(Branch $branch, Item $item, Request $request)
    {
        $branch = \Auth::user()->branches()->findOrFail($branch->id);
        $getItem = $branch->item()->findOrFail($item->id);
        if (\Auth::user()->isRestaurant()){
            $getItem->pivot->amount = $request->input('amount') === "true" ? -1 : 0;
        } else{
            $getItem->pivot->amount = $request->input('amount');
        }
        $getItem->pivot->save();
        return \APIHelper::jsonRender('change item amount done', []);

    }

    public function sendMoney(Request $request,Branch $branch)
    {
        $request->validate([
            'amount'    =>  'required|numeric'
        ]);

        $branch->wallet()->update([
            'amount'    =>  $branch->wallet->amount - $request->input('amount'),
        ]);

        $branch->walletTrack()->create([
            'price'     =>  $request->input('amount'),
            'process'   =>  'in',
            'direction' =>  [
                'branch_id'=>  $branch->id,
            ],
            'message'   =>  'Vendor Sent You Money',
            'order_type'=>  'get',
            'reason'    =>  'Vendor Sent You '.$request->input('amount').' RS',
            'isTransaction'=>true,
            'transaction_id'=>\Str::random(6)
        ]);

        return redirect()->route('vendor.branch.index')->with('success','transaction saved successfully');
    }
}
