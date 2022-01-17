<?php

namespace Database\Factories;

use App\Models\Branch;
use App\Models\BranchItems;
use App\Models\Drink;
use App\Models\Extra;
use App\Models\SupportedVendor;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Facades\Hash;

class BranchItemsFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = BranchItems::class;

    protected static $next = 0;
    protected static $branch_ids = [];
    protected static $branch_next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $branch = Branch::find(self::$branch_ids[self::$branch_next]);
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
            'branch_id'     =>  $branch->id,
            'item_id'       =>  $item->id,
            'amount'        =>  (!$branch->vendor->isRestaurant())?$this->faker->numberBetween(0,50):-1
        ];
        if (self::$next++>=5)
        {
            self::$branch_next++;
            self::$next = 0;
        }

        return $data;
    }

    public static function getCount()
    {
        self::$branch_ids = Branch::all('id')->pluck('id');
        return Branch::count() * 5;
    }
}
