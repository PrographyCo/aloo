<?php

namespace Database\Factories;

use App\Models\Branch;
use App\Models\Customer;
use App\Models\Drink;
use App\Models\Driver;
use App\Models\Extra;
use App\Models\Item;
use App\Models\Order;
use App\Models\OrderItem;
use Illuminate\Database\Eloquent\Factories\Factory;

class OrderItemFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = OrderItem::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        Item::$withDetails = false;
        $order = Order::inRandomOrder()->first();
        $branch = $order->branch()->inRandomOrder()->first();
        $item = $branch->vendor->items()->inRandomOrder()->first();

        if ($item===null)
        {
            $amount_type = ['piece', 'kgm', 'letter'];
            shuffle($amount_type);

            $category = $branch->vendor->categories->first();
            if ($branch->vendor->isRestaurant()) $amount_type = ['calories'];
            if ($category===null) $category = $branch->vendor->categories()->create([
                'parent_id' =>  null,
                'name_en'  =>  $this->faker->text(10),
                'description_en'   =>  $this->faker->paragraph(3),
                'name_ar'  =>  $this->faker->text(10),
                'description_ar'   =>  $this->faker->paragraph(3)
            ]);

            $vendorItemData = [
                'vendor_id' =>  $branch->vendor->id,
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

            if ($branch->vendor->isRestaurant())
            {
                $vendorItemData['optionals'] = [
                    'tomato',
                    'potato',
                    'anything'
                ];
                $vendorItemData['sizes'] = [
                    'S' =>  24,
                    'M' =>  40,
                    'B' =>  60
                ];
                $vendorItemData['extras'] = Extra::inRandomOrder()->select('id')->limit(3)->get()->pluck('id')->toArray();
                $vendorItemData['drinks'] = Drink::inRandomOrder()->select('id')->limit(3)->get()->pluck('id')->toArray();
            }else
            {
                $vendorItemData['images'] = ['img1.png','img2.png'];
            }

            foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props) {
                $vendorItemData['name_'.$lang]  =  $this->faker->text(10);
                $vendorItemData['brief_desc_'.$lang]    =  $this->faker->paragraph(2);
                $vendorItemData['description_'.$lang]   =  $this->faker->paragraph(3);
            }
            $item = $branch->vendor->items()->create($vendorItemData);
        }

        $data = [
            'order_id'  =>  $order->id,
            'item_id'   =>  $item->id,
            'amount'    =>  $this->faker->numberBetween(0,100),
            'item_price'=>  $this->faker->randomFloat(2,50,200),
            'data'      =>  [
                'with'  =>  [],
                'without'=> [],
                'size'  =>  "",
                'drinks'=>  [],
                'extras'=>  []
            ]
        ];

        if ($branch->vendor->isRestaurant())
        {
            $data['data'] = [
                'size'      =>  $this->faker->randomElement(['S','M','B']),
                'with'      =>  $this->faker->randomElements($item->optionals),
                'without'   =>  $this->faker->randomElements($item->optionals),
                'drinks'    =>  $this->faker->randomElements($item->drinks),
                'extras'    =>  $this->faker->randomElements($item->extras)
            ];
        }

        return $data;
    }
}
