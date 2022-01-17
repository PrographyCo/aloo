<?php

namespace Database\Factories;

use App\Models\Customer;
use App\Models\Item;
use App\Models\UserFavorite;
use Illuminate\Database\Eloquent\Factories\Factory;

class UserFavoriteFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = UserFavorite::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $item = Item::inRandomOrder()->first();
        return [
            'customer_id' => Customer::inRandomOrder()->first()->id,
            'item_id'   => $item->id,
            'vendor_id' =>  $item->vendor_id,
            'supported_vendor_id'   =>  $item->vendor->supported_vendor->id,
        ];
    }
}
