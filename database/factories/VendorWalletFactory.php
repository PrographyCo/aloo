<?php

namespace Database\Factories;

use App\Models\Branch;
use App\Models\Car;
use App\Models\Customer;
use App\Models\DriverWallet;
use App\Models\Item;
use App\Models\UserWallet;
use App\Models\Vendor;
use App\Models\VendorWallet;
use Illuminate\Database\Eloquent\Factories\Factory;

class VendorWalletFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = VendorWallet::class;

    public static $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $customer = Branch::all()[self::$next++];
        return [
            'branch_id'     => $customer->id,
            'amount'        =>  $this->faker->randomFloat(2,1000,9*10**6)
        ];
    }
}
