<?php

namespace Database\Factories;

use App\Models\Branch;
use App\Models\Car;
use App\Models\Customer;
use App\Models\Drink;
use App\Models\Driver;
use App\Models\Extra;
use App\Models\Item;
use App\Models\Order;
use App\Models\OrderCancel;
use App\Models\OrderItem;
use Illuminate\Database\Eloquent\Factories\Factory;

class OrderCancelFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = OrderCancel::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        // order_id
        //user_id
        //relationship
        //message

        $order = Order::where('status','=','cancelled')->inRandomOrder()->first();
        $user = $this->faker->randomElement(array_merge([Customer::find($order->customer_id), Branch::find($order->branch_id)], ($order->car_id!==null)? [Car::find($order->car_id)]:[]));

        $data = [
            'order_id'  => $order->id,
            'user_id'   => $user->id,
            'relationship'=> class_basename($user),
            'message'   =>  $this->faker->paragraph(5)
        ];

        return $data;
    }
}
