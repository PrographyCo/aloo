<?php

namespace Database\Factories;

use App\Models\Branch;
use App\Models\Car;
use App\Models\Customer;
use App\Models\Item;
use App\Models\UserWallet;
use App\Models\UserWalletTrack;
use App\Models\Vendor;
use App\Models\VendorWalletTrack;
use Illuminate\Database\Eloquent\Factories\Factory;

class VendorWalletTrackFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = VendorWalletTrack::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $bool = $this->faker->boolean;
        return [
            'branch_id'        => Branch::inRandomOrder()->first()->id,
            'price'         =>  $this->faker->randomFloat(2,1000,5000),
            'process'       =>  $this->faker->randomElement(['in','out']),
            'direction'     =>  [
                'customer'  =>  Customer::inRandomOrder()->first()->id
            ],
            'message'   =>  $this->faker->text(50),
            'order_type'=>  $this->faker->randomElement(['back','pay']),
            'reason'    =>  $this->faker->paragraph(2),
            'isTransaction' =>  $bool,
            'transaction_id'    =>  ($bool)? $this->faker->text(16): null
        ];
    }
}
