<?php

namespace Database\Factories;

use App\Models\Car;
use App\Models\Customer;
use App\Models\DriverWallet;
use App\Models\Item;
use App\Models\UserWallet;
use Illuminate\Database\Eloquent\Factories\Factory;

class DriverWalletFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = DriverWallet::class;

    public static $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $customer = Car::all()[self::$next++];
        return [
            'car_id'     => $customer->id,
            'amount'        =>  $this->faker->randomFloat(2,1000,9*10**6)
        ];
    }
}
