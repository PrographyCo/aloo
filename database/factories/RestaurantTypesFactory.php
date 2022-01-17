<?php

namespace Database\Factories;

use App\Models\KitchenType;
use App\Models\RestaurantTypes;
use Illuminate\Database\Eloquent\Factories\Factory;

class RestaurantTypesFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = RestaurantTypes::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = [];
        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props)
        {
            $data['name_'.$lang] = ($lang==='ar')?
                'مأكولات'
                :'food';
        }
        return $data;
    }
}
