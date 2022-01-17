<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\Driver\CarDataTable;
use App\DataTables\Admin\DriverDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\Driver\StoreRequest;
use App\Http\Requests\Admin\Driver\UpdateRequest;
use App\Models\Bank;
use App\Models\Driver;
use App\Models\Vendor;
use Illuminate\Http\Request;

class DriverController extends Controller
{

    public function index(DriverDataTable $dataTable)
    {
        return $dataTable->render('web.admin.driver.index', [
            'title' => 'all drivers'
        ]);
    }


    public function create()
    {
        $banks            = Bank::all('id','name_'.self::$lang. ' as name');
        return view('web.admin.driver.create.create', [
            'banks' => $banks
        ]);
    }

    public function store(StoreRequest $request): \Illuminate\Http\RedirectResponse
    {
        $driver = new Driver();
        $driver->name                = $request->input('name');
        $driver->phone               = $request->input('phone');
        $driver->email               = $request->input('email');
        $driver->password            = \Hash::make($request->input('password'));
        $driver->bank_id             = $request->input('bank');
        $driver->bankRecipientName   = $request->input('bankRecipientName');

        $image = \Storage::disk('drivers')->putFile('', $request->file('img'));
        $driver->img = $image;
        $iban = \Storage::disk('drivers')->putFile('', $request->file('iban'));
        $driver->iban =  $iban;
        $driver->save();

        return redirect()->route('admin.driver.show', $driver)->with('success', 'driver created successfully');
    }

    public function show(Driver $driver)
    {
        return (new CarDataTable($driver->id))->render('web.admin.driver.show.show', [
            'driver' => $driver
        ]);
    }

    public function edit(Driver $driver)
    {
        return view('web.admin.driver.edit.edit', [
            'driver' => $driver
        ]);
    }

    public function update(UpdateRequest $request, Driver $driver)
    {
        $driver->name       = $request->input('name');
        $driver->email      = $request->input('email');
        if ($request->hasFile('img')){
            $image = \Storage::disk('drivers')->putFile('', $request->file('img'));
            $driver->img = $image;
        }
        $driver->save();

        return redirect()->route('admin.driver.show', $driver)->with('success', 'driver updated successfully');
    }

    public function confirm(Driver $driver)
    {
        $driver->confirm = true;
        $driver->save();
        return redirect()->route('admin.driver.show', $driver)->with('success', 'driver has confirmed');
    }

    public function stopDriver(Driver $driver)
    {

        $driver->ban = !$driver->ban;
        $driver->save();
        $driver->cars()->update([
            'ban' => true
        ]);
        foreach ($driver->cars as $car) {
            $car->token()->revoke();
        }
        return redirect()->route('admin.driver.show', $driver)->with('success', 'change customer ban (now is '.($driver->ban?null:'not').' ban )');
    }


    public function showWallets(Request $request,Driver $driver)
    {
        $request->validate([
            'amount'    =>  'required|numeric'
        ]);

        $driver->transactions()->create([
            'amount'    =>  $request->input('amount'),
            'direction' =>  'in'
        ]);

        return redirect()->route('admin.driver.index')->with('success','transaction saved successfully');
    }

}
