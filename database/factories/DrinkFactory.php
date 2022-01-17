<?php

namespace Database\Factories;

use App\Models\Drink;
use App\Models\Item;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class DrinkFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Drink::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'vendor_id' =>  Vendor::inRandomOrder()->where('supported_vendor_id','=',3)->first()->id,
            'name_en'   =>  $this->faker->text(30),
            'name_ar'   =>  (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(30),
            'price'     =>  $this->faker->randomFloat(2,0,1000),
            'calories'  =>  random_int(0,10000),
        ];
    }
}
