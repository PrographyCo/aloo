<?php

namespace Database\Factories;

use App\Models\Customer;
use App\Models\CustomerPlaces;
use Illuminate\Database\Eloquent\Factories\Factory;

class CustomerPlacesFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = CustomerPlaces::class;

    protected static $data = [];
    protected static $id = 1;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        if (self::$id<=4 && !isset(self::$data[self::$id]))
        {
            $id = Customer::find(self::$id)->id;
            $lat = '-52.0013000000';
            $lon = '18.6269490000';

            self::$data[self::$id++] = [$lon,$lat];
        }else{
            $id = Customer::inRandomOrder()->first()->id;
            $lat = $this->faker->latitude;
            $lon = $this->faker->longitude;
        }

        return [
            'customer_id'   =>  $id,
            'lon'   =>  $lon,
            'lat'   =>  $lat,
            'name'  =>  $this->faker->text(15),
            'location_name' => $this->faker->streetAddress,
            'isDefault' => false
        ];
    }
}
