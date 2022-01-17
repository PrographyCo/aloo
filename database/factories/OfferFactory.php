<?php

namespace Database\Factories;

use App\Models\Drink;
use App\Models\Extra;
use App\Models\Offer;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class OfferFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Offer::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {

        $vendor = Vendor::where('supported_vendor_id',3)->inRandomOrder()->first();

        $category = $vendor->categories->first();
        if ($category===null) $category = $vendor->categories()->create([
            'parent_id' =>  null,
            'name_en'  =>  $this->faker->text(10),
            'description_en'   =>  $this->faker->paragraph(3),
            'name_ar'  =>  $this->faker->text(10),
            'description_ar'   =>  $this->faker->paragraph(3)
        ]);

        $data = [
            'vendor_id' =>  $vendor->id,
            'category_id'=>  $category->id,
            'price'     =>  $this->faker->randomFloat(2,0,1000),
            'img'       =>  'default.jpg',
            'amount'    =>  random_int(0,100),
            'with' =>  [],
            'without' =>  [],
            'size'     =>  '',
            'extras'    =>  [],
            'drinks'    =>  []
        ];

            $data['with'] = $this->faker->randomElements([
                'tomato',
                'potato',
                'anything'
            ]);
            $data['without'] = $this->faker->randomElements([
                'tomato',
                'potato',
                'anything'
            ]);
            $data['size'] = $this->faker->randomElement([
                'S',
                'M',
                'B'
            ]);
            $data['extras'] = Extra::inRandomOrder()->select('id')->limit(3)->get()->pluck('id')->toArray();
            $data['drinks'] = Drink::inRandomOrder()->select('id')->limit(3)->get()->pluck('id')->toArray();

        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props) {
            $data['name_'.$lang]  =  ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(10)
                :$this->faker->text(10);

            $data['description_'.$lang]   =  ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(150)
                :$this->faker->text(150);
        }

        return $data;
    }
}
