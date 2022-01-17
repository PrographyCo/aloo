<?php

namespace Database\Factories;

use App\Models\About;
use App\Models\Bank;
use App\Models\City;
use Illuminate\Database\Eloquent\Factories\Factory;

class BankFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Bank::class;

    /**
     * Define the model's default state.
     *
     * @return array
     * @throws \Exception
     */
    public function definition()
    {
        $data = [];

        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props)
        {
            $data['name_'.$lang] = ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(20)
                :$this->faker->text(20);
        }

        return $data;
    }
}
