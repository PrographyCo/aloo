<?php

namespace Database\Factories;

use App\Models\Drink;
use App\Models\Extra;
use App\Models\Item;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class ItemFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Item::class;

    public static $isRand = true;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $amount_type = ['piece', 'kgm', 'letter'];
        shuffle($amount_type);

        if (self::$isRand)
        {
            $vendor = Vendor::inRandomOrder()->first();
        }else{
            $vendor = Vendor::find($this->faker->numberBetween(1,4));
        }

        $category = $vendor->categories->first();
        if ($vendor->isRestaurant()) $amount_type = ['calories'];
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
            'amount_type'=> $amount_type[0],
            'optionals' =>  [],
            'sizes'     =>  [],
            'extras'    =>  [],
            'drinks'    =>  [],
            'images'    =>  []
        ];

        if ($vendor->isRestaurant())
        {
            $data['optionals'] = [
                'tomato',
                'potato',
                'anything'
            ];
            $data['sizes'] = [
                'S' =>  24,
                'M' =>  40,
                'B' =>  60
            ];
            $data['extras'] = Extra::inRandomOrder()->select('id')->limit(3)->get()->pluck('id')->toArray();
            $data['drinks'] = Drink::inRandomOrder()->select('id')->limit(3)->get()->pluck('id')->toArray();
        }else
        {
            $data['images'] = ['default.jpg','default.jpg'];
        }

        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props) {
            $data['name_'.$lang]  =  ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(10)
                :$this->faker->text(10);

            $data['brief_desc_'.$lang]    =  ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(100)
                :$this->faker->text(100);

            $data['description_'.$lang]   =  ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(150)
                :$this->faker->text(150);
        }

        return $data;
    }
}
