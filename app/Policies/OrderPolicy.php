<?php

namespace App\Policies;

use App\Models\Branch;
use App\Models\Car;
use App\Models\Order;
use Illuminate\Auth\Access\HandlesAuthorization;
use Illuminate\Auth\Access\Response;

class OrderPolicy
{
    use HandlesAuthorization;

    /**
     * Create a new policy instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    public function showVendor(Branch $branch, Order $order)
    {
        return $order->branch_id == $branch->id
            ? Response::allow()
            : Response::deny();
    }

    public function showCar(Car $car, Order $order)
    {
        return $order->carId === null || $order->car_id == $car->id
            ? Response::allow()
            : Response::deny();
    }
}
