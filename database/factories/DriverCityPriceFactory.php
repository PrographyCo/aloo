<?php

namespace Database\Factories;

use App\Models\City;
use App\Models\DriverCityPrice;
use Illuminate\Database\Eloquent\Factories\Factory;

class DriverCityPriceFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = DriverCityPrice::class;

    protected static $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $city = City::all()[self::$next++];
        return [
            'city_id'   => $city->id,
            'price'     =>  $this->faker->randomFloat(4,10,20)
        ];
    }
}
