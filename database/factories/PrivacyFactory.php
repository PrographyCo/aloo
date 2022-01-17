<?php

namespace Database\Factories;

use App\Models\About;
use App\Models\Privacy;
use Illuminate\Database\Eloquent\Factories\Factory;
use Mcamara\LaravelLocalization\LaravelLocalization;

class PrivacyFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Privacy::class;

    protected $fields = [
        [
            'id'    =>  'customer',
        ],
        [
            'id'    =>  'driver',
        ],
        [
            'id'    =>  'vendor',
        ]
    ];

    protected $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = $this->fields[$this->next++];
        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $prop)
        {
            $data[$lang] = ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(1500)
                :$this->faker->text(1500);
        }

        return $data;
    }

    public static function getCount()
    {
        return count((new self())->fields);
    }
}
