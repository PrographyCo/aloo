<?php

namespace Database\Factories;

use App\Models\Category;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class CategoryFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Category::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = [
            'vendor_id' => Vendor::inRandomOrder()->first()->id,
        ];

        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props) {
            $data['name_'.$lang]  =  ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realTextBetween(20,60)
                :$this->faker->text(20);

            $data['description_'.$lang]   =  ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realTextBetween(150,450)
                :$this->faker->paragraph(3);
        }
        $category = Category::inRandomOrder()->first();
        $data['parent_id'] = $category?$category->id:null;

        return $data;
    }
}
