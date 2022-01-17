<?php

namespace Database\Factories;

use App\Models\Branch;
use App\Models\Car;
use App\Models\Customer;
use App\Models\Driver;
use App\Models\Order;
use Illuminate\Database\Eloquent\Factories\Factory;

class OrderFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Order::class;

    protected static $countNumber = 80;
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $id = ceil(self::$countNumber/20);
        $customer   = (self::$countNumber>0) ? Customer::find($id) : Customer::inRandomOrder()->first();
        $branch     = (self::$countNumber>0) ? Branch::find($id) : Branch::inRandomOrder()->first();
        $car        = (self::$countNumber-->0) ? $id : Car::inRandomOrder()->first()->id;
        $status     = $this->faker->randomElement(['unconfirmed', 'confirmed by vendor', 'confirmed by driver', 'driver waiting', 'ready', 'in-delivery', 'driver arrived', 'delivered', 'delivery confirmed', 'done', 'cancelled']);

        return [
            'customer_id'   =>  $customer->id,
            'place_id'      =>  $customer->places->first()->id,
            'branch_id'     =>  $branch->id,
            'status'        =>  $status,
            'total_price'   =>  $this->faker->randomFloat(2,50,200),
            'car_id'        =>  ($status==='confirmed by vendor')?$this->faker->randomElement([$car, null]):$car,
            'start_time'    =>  $this->faker->time,
            'deliver_time'  =>  $this->faker->time,
            'distance'      =>  ($status==='unconfirmed'||$status==='confirmed by vendor')?null:$this->faker->randomFloat(4,0,20),
        ];
    }
}
