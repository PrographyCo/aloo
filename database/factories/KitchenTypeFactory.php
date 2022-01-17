<?php

namespace Database\Factories;

use App\Models\KitchenType;
use Illuminate\Database\Eloquent\Factories\Factory;

class KitchenTypeFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = KitchenType::class;

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
                'شرقي'
                :'eastern';
        }
        return $data;
    }
}
