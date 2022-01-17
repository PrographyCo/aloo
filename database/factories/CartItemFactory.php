<?php

namespace Database\Factories;

use App\Models\CartItem;
use App\Models\City;
use App\Models\CustomerCart;
use App\Models\Item;
use App\Models\Offer;
use Illuminate\Database\Eloquent\Factories\Factory;

class CartItemFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = CartItem::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        Item::$withDetails = false;
        $item = Item::inRandomOrder()->first();
        $item->type = 'item';

        $offer = Offer::inRandomOrder()->first();
        $offer->type = 'offer';

        $item = $this->faker->randomElement([$item,$offer]);

        $data = new \stdClass();
        $branch = $item->vendor->branches()->inRandomOrder()->first();
        $cart = CustomerCart::where('supported_vendor_id','=',$item->vendor->supported_vendor_id)->inRandomOrder()->first();
        if ($branch === null)
        {
            $branch = $item->vendor->branches()->create([
                'name'            => $this->faker->name,
                'login_number'    => $this->faker->unique->phoneNumber,
                'password'        => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
                'manager'         => $this->faker->name,
                'managerEmail'    => $this->faker->email,
                'managerPhone'    => $this->faker->phoneNumber,
                'managerPosition' => $this->faker->jobTitle,
                'available_status'=> $this->faker->boolean,
                'lon'             => $this->faker->longitude,
                'lat'             => $this->faker->latitude,
                'city_id'         => City::inRandomOrder()->first()->id,
            ]);
        }

        if ($cart->isRestaurant())
        {
            $data->size = ($item->type==='item')?$this->faker->randomElement(['S','M','B']): $item->size;
            $data->with = ($item->type==='item')?$this->faker->randomElements($item->optionals): $item->with;
            $data->without = ($item->type==='item')?$this->faker->randomElements($item->optionals): $item->without;
            $data->drinks = ($item->type==='item')?$this->faker->randomElements($item->drinks): $item->drinks;
            $data->extras = ($item->type==='item')?$this->faker->randomElements($item->extras): $item->extras;
        }

        $amount = $this->faker->numberBetween(1,100);
        return [
            'customer_cart_id'  =>  $cart->id,
            'item_id'   =>  ($item->type==='item')?$item->id:null,
            'offer_id'  =>  ($item->type==='offer')?$item->id:null,
            'branch_id' =>  $branch->id,
            'amount'    =>  $amount,
            'data'      =>  $data,
            'unit_price'=>  $item->price,
            'total_price'=> $item->price * $amount
        ];
    }
}
