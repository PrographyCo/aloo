<?php

namespace Database\Factories;

use App\Models\About;
use App\Models\City;
use Illuminate\Database\Eloquent\Factories\Factory;

class CityFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = City::class;

    protected static $next = 0;
    protected $fields = [
        [
            'name_ar'   =>  'الرياض',
            'name_en'   =>  'Riyadh'
        ],[
            'name_ar'   =>  'جدة',
            'name_en'   =>  'Jeddah'
        ],[
            'name_ar'   =>  'الدمام',
            'name_en'   =>  'Dammam'
        ],
    ];

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return $this->fields[self::$next++];
    }
}
