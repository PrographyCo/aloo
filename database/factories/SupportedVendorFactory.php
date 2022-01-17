<?php

namespace Database\Factories;

use App\Models\SupportedVendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class SupportedVendorFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = SupportedVendor::class;

    protected $fields = [
        [
            'name_en'   =>  'supermarkets',
            'name_ar'   =>  'سوبرماركت',

            'img'       =>  'supermarkets.png'
        ],
        [
            'name_en'   =>  'pharmacies',
            'name_ar'   =>  'صيدليات',

            'img'   =>  'pharmacies.png'
        ],
        [
            'name_en'   =>  'restaurants',
            'name_ar'   =>  'مطاعم',

            'img'   =>  'restaurants.png'
        ],
    ];

    protected $next=0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = $this->fields[$this->next++];

        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props)
        {
            $data['description_'.$lang] = ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(150)
                :$this->faker->text(150);
        }
        return $data;
    }

    public static function getCount()
    {
        return count((new self())->fields);
    }
}
