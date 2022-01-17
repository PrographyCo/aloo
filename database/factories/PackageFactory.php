<?php

namespace Database\Factories;

use App\Models\Package;
use Illuminate\Database\Eloquent\Factories\Factory;

class PackageFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    protected $model = Package::class;
    public function definition()
    {
        $data = [
            'price' =>  $this->faker->randomFloat(2,0,10**5),
            'orders'=>  random_int(0,30),
            'days'  =>  random_int(20,45),
            'type'  =>  $this->faker->randomElement(['discount','freeDelivery']),
        ];

        if ($data['type'] === 'discount')
        {
            $data['discount_type'] = $this->faker->randomElement(['%','SR']);
            if ($data['discount_type'] === '%')
                $data['discount'] = random_int(10,50);
            else
                $data['discount'] = random_int(150,1000);
        }
        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props) {
            $data['name_'.$lang] = ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(30)
                :$this->faker->text(30);
        }
        return $data;
    }
}
