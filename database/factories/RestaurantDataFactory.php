<?php

namespace Database\Factories;

use App\Models\KitchenType;
use App\Models\RestaurantData;
use App\Models\RestaurantTypes;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class RestaurantDataFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = RestaurantData::class;

    protected $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'vendor_id' =>  Vendor::where('supported_vendor_id','=',3)->get()[$this->next++]->id,
            'restaurant_type_id'    =>  RestaurantTypes::inRandomOrder()->first()->id,
            'kitchen_type_id'       =>  KitchenType::inRandomOrder()->first()->id,
        ];
    }

    public static function getCount()
    {
        return Vendor::where('supported_vendor_id','=',3)->count();
    }
}
