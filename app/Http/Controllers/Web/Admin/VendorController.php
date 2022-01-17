<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\PharmacyVendorDataTable;
use App\DataTables\Admin\RestaurantVendorDataTable;
use App\DataTables\Admin\SupermarketVendorDataTable;
use App\DataTables\Admin\Vendor\BranchDataTable;
use App\DataTables\Admin\Vendor\CategoryDataTable;
use App\DataTables\Admin\Vendor\DrinkDataTable;
use App\DataTables\Admin\Vendor\ExtraDataTable;
use App\DataTables\Admin\Vendor\ItemDataTable;
use App\DataTables\Admin\Vendor\ItemRestaurantDataTable;
use App\DataTables\Admin\VendorDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\Vendor\StoreRequest;
use App\Http\Requests\Admin\Vendor\UpdateRequest;
use App\Models\Bank;
use App\Models\City;
use App\Models\Driver;
use App\Models\KitchenType;
use App\Models\RestaurantTypes;
use App\Models\SupportedVendor;
use App\Models\Vendor;
use Illuminate\Http\Request;

class VendorController extends Controller
{

    public function index(VendorDataTable $dataTable)
    {
        return $dataTable->render('web.admin.vendor.index', [
            'title' => 'all vendors'
        ]);
    }


    public function vendorRestaurant(RestaurantVendorDataTable $dataTable)
    {
        return $dataTable->render('web.admin.vendor.index', [
            'title' => 'all Restaurant vendor'
        ]);
    }

    public function vendorSupermarket(SupermarketVendorDataTable $dataTable)
    {
        return $dataTable->render('web.admin.vendor.index', [
            'title' => 'all supermarket vendor'
        ]);
    }

    public function vendorPharmacy(PharmacyVendorDataTable $dataTable)
    {
        return $dataTable->render('web.admin.vendor.index', [
            'title' => 'all pharmacy vendor'
        ]);
    }

    public function create()
    {
        $cities           = City::all('id','name_'.self::$lang. ' as name');
        $supportedVendors = SupportedVendor::all('id','name_'.self::$lang. ' as name');
        $banks            = Bank::all('id','name_'.self::$lang. ' as name');
        $restaurant_type  = RestaurantTypes::all('id','name_'.self::$lang. ' as name');
        $kitchen_type     = KitchenType::all('id','name_'.self::$lang. ' as name');
        return view('web.admin.vendor.create.create', [
            'cities'           => $cities,
            'supportedVendors' => $supportedVendors,
            'banks'            => $banks,
            'restaurant_type'  => $restaurant_type,
            'kitchen_type'     => $kitchen_type,
        ]);
    }

    public function store(StoreRequest $request)
    {
        $vendor = new Vendor();
        $vendor->legalName            = $request->input('legalName');
        $vendor->brandName            = $request->input('brandName');
        $vendor->password             = \Hash::make($request->input('password'));
        $vendor->commercialNo         = $request->input('commercialNo');
        $vendor->city_id              = $request->input('city');
        $vendor->supported_vendor_id  = $request->input('supported_vendor');
        $vendor->description          = $request->input('description');
        $vendor->email                = $request->input('email');
        $vendor->phone                = $request->input('phone');
        $vendor->bank_id              = $request->input('bank');
        $vendor->bankRecipientName    = $request->input('bankRecipientName');
        $vendor->bankIBAN             = $request->input('bankIBAN');

        $commercialRecord = \Storage::disk('vendors')->putFile('', $request->file('commercialRecord'));
        $vendor->commercialRecord =  $commercialRecord;
        $vendor->logo  = \Storage::disk('vendors')->putFile('', $request->file('logo'));
        $vendor->image = ($request->input('supported_vendor')!=="3")?\Storage::disk('vendors')->putFile('', $request->file('image')):$vendor->logo;

        $speech = \Storage::disk('vendors')->putFile('', $request->file('speech'));
        $vendor->speech = $speech;
        $vendor->save();

        if($vendor->isRestaurant()){
            $vendor->data()->create([
                'restaurant_type_id' => $request->input('restaurant_type'),
                'kitchen_type_id' => $request->input('kitchen_type'),
            ]);
        }
        return redirect()->route('admin.vendor.show', $vendor)->with('success', 'vendor created successfully');
    }


    public function show(Vendor $vendor)
    {
        $vendor->loadCount('branches', 'items');
        if ($vendor->isRestaurant()){
            $itemsTable = (new ItemRestaurantDataTable($vendor->id))->html();
            $extraTable = (new DrinkDataTable($vendor->id))->html();
            $drinkTable = (new ExtraDataTable($vendor->id))->html();
        } else {
            $itemsTable = (new ItemDataTable($vendor->id))->html();
        }
        $branchesTable  = (new BranchDataTable($vendor->id))->html();
        $categoryTable  = (new CategoryDataTable($vendor->id))->html();

        return view('web.admin.vendor.show.show', [
            'vendor'        => $vendor,
            'itemsTable'    => $itemsTable,
            'extraTable'    => $extraTable ?? null,
            'drinkTable'    => $drinkTable ?? null,
            'branchesTable' => $branchesTable,
            'categoryTable' => $categoryTable
        ]);
    }

    public function edit(Vendor $vendor)
    {
        $cities           = City::all('id','name_'.self::$lang. ' as name');
        $banks            = Bank::all('id','name_'.self::$lang. ' as name');
        return view('web.admin.vendor.edit.edit', [
            'vendor'           => $vendor,
            'cities'           => $cities,
            'banks'            => $banks,
        ]);
    }

    public function update(UpdateRequest $request, Vendor $vendor)
    {
        $vendor->description        = $request->input('description');
        $vendor->city_id            = $request->input('city');
        $vendor->bank_id            = $request->input('bank');
        $vendor->bankRecipientName  = $request->input('bankRecipientName');
        $vendor->bankIBAN           = $request->input('bankIBAN');

        if($request->hasFile('logo'))
            $vendor->logo = \Storage::disk('vendors')->putFile('', $request->file('logo'));

        if (!$vendor->isRestaurant())
            if($request->hasFile('image'))
                $vendor->image = \Storage::disk('vendors')->putFile('', $request->file('image'));

        $vendor->save();
        return redirect()->route('admin.vendor.show', $vendor)->with('success', 'vendor updated successfully');
    }


    public function serviceDataTable(Vendor $vendor, $table)
    {
        switch ($table){
            case 'item':
                if ($vendor->isRestaurant()){
                    return (new ItemRestaurantDataTable($vendor->id))->render('web.admin.vendor.show.show');
                } else {
                    return (new ItemDataTable($vendor->id))->render('web.admin.vendor.show.show');
                }
            case 'branch':
                return (new BranchDataTable($vendor->id))->render('web.admin.vendor.show.show');
            case 'category':
                return (new CategoryDataTable($vendor->id))->render('web.admin.vendor.show.show');
            case 'drink':
                if ($vendor->isRestaurant())
                    return (new DrinkDataTable($vendor->id))->render('web.admin.vendor.show.show');
            case 'extra':
            if ($vendor->isRestaurant())
                return (new ExtraDataTable($vendor->id))->render('web.admin.vendor.show.show');
        }
        return [];
    }

    public function confirm(Vendor $vendor)
    {
        $vendor->confirm = true;
        $vendor->save();
        return redirect()->route('admin.vendor.show', $vendor)->with('success', 'vendor has confirmed');
    }


    public function stopVendor(Vendor $vendor)
    {
        $vendor->ban = !$vendor->ban;
        $vendor->save();
        $vendor->branches()->update([
            'ban' => true
        ]);
        return redirect()->route('admin.vendor.show', $vendor)->with('success', 'change customer ban (now is '.($vendor->ban?null:'not').' ban )');
    }

    public function showWallets(Request $request,Vendor $vendor)
    {
        $request->validate([
            'amount'    =>  'required|numeric'
        ]);

        $vendor->transactions()->create([
            'amount'    =>  $request->input('amount'),
            'direction' =>  'in'
        ]);

        return redirect()->route('admin.vendor.index')->with('success','transaction saved successfully');
    }
}
