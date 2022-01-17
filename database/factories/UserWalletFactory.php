<?php

namespace Database\Factories;

use App\Models\Customer;
use App\Models\Item;
use App\Models\UserWallet;
use Illuminate\Database\Eloquent\Factories\Factory;

class UserWalletFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = UserWallet::class;

    public static $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $customer = Customer::all()[self::$next++];
        return [
            'customer_id'   => $customer->id,
            'amount'        =>  $this->faker->randomFloat(2,1000,9*10**6)
        ];
    }
}
