<?php

namespace App\Http\Controllers\Web\Driver;

use App\DataTables\Driver\CarDataTable;
use App\DataTables\Driver\CarOrderDataTable;
use App\Http\Controllers\Controller;
use App\Http\Requests\Driver\Car\ChangePasswordRequest;
use App\Http\Requests\Driver\Car\StoreRequest;
use App\Http\Requests\Driver\Car\UpdateRequest;
use App\Models\Car;
use App\Models\City;
use Illuminate\Http\Request;

class CarController extends Controller
{

    public function index(CarDataTable $dataTable)
    {
        return $dataTable->render('web.driver.car.index');
    }


    public function create()
    {
        $cities           = City::all('id','name_'.self::$lang. ' as name');
        return view('web.driver.car.create.create', [
            'cities'      => $cities,
        ]);
    }

    public function store(StoreRequest $request)
    {
        $numbers = Car::select('login_number')->get()->pluck('login_number')->toArray();
        do{
            $login_number = (string)random_int(1000000000, 9999999999);
        }while (in_array($login_number, $numbers, true));
        if ($login_number<0) $login_number*=-1;

        $car = new Car();
        $car->name             = $request->input('name');
        $car->driver_id        = \Auth::id();
        $car->password         = \Hash::make($request->input('password'));
        $car->city_id          = $request->input('city');
        $car->gender           = $request->input('gender');
        $car->idNumber         = $request->input('idNumber');
        $car->phone            = $request->input('phone');
        $car->email            = $request->input('email');
        $car->login_number     = $login_number;

        $car->license        = \Storage::disk('cars')->putFile('', $request->file('license'));
        $car->id_img         = \Storage::disk('drivers')->putFile('', $request->file('id_img'));
        $car->save();

        $car->wallet()->create([
            'amount' => 0,
        ]);

        return redirect()->route('driver.car.show', $car)->with('success', 'car created successfully');
    }


    public function show(Car $car)
    {
        $car = \Auth::user()->cars()->findOrFail($car->id);
        return (new CarOrderDataTable($car))->render('web.driver.car.show.show', [
            'car' => $car
        ]);
    }

    public function edit(Car $car)
    {
        $car = \Auth::user()->cars()->findOrFail($car->id);
        $cities           = City::all('id','name_'.self::$lang. ' as name');
        return view('web.driver.car.edit.edit', [
            'cities'      => $cities,
            'car'         => $car,
        ]);
    }

    public function update(UpdateRequest $request, Car $car)
    {
        $car = \Auth::user()->cars()->findOrFail($car->id);
        $car->name             = $request->input('name');
        $car->city_id          = $request->input('city');
        $car->gender           = $request->input('gender');
        $car->idNumber         = $request->input('idNumber');
        $car->phone            = $request->input('phone');
        $car->email            = $request->input('email');
        if ($request->hasFile('license')){
            $license        = \Storage::disk('cars')->putFile('', $request->file('license'));
            $car->license   = $license;
        }
        if ($request->hasFile('id_img')){
            $id_img         = \Storage::disk('drivers')->putFile('', $request->file('id_img'));
            $car->id_img    =  $id_img;
        }
        $car->save();
        return redirect()->route('driver.car.show', $car)->with('success', 'car updated successfully');
    }


    public function changePassword(Car $car, ChangePasswordRequest $request)
    {
        $car = \Auth::user()->cars()->findOrFail($car->id);
        $password = $request->input('password');
        $car->password = \Hash::make($password);
        $car->save();
        return redirect()->route('driver.car.show', $car);
    }

    public function sendMoney(Request $request,Car $car)
    {
        $request->validate([
            'amount'    =>  'required|numeric'
        ]);

        $car->wallet()->update([
            'amount'    =>  $car->wallet->amount - $request->input('amount'),
        ]);

        $car->walletTrack()->create([
            'price'     =>  $request->input('amount'),
            'process'   =>  'in',
            'direction' =>  [
                'car_id'=>  $car->id,
            ],
            'message'   =>  'Your Head Master Sent You Money',
            'order_type'=>  'get',
            'reason'    =>  'Your Head Master Sent You '.$request->input('amount').' RS',
            'isTransaction'=>true,
            'transaction_id'=>\Str::random(6)
        ]);

        return redirect()->route('driver.car.index')->with('success','transaction saved successfully');
    }
}
