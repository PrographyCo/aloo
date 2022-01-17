<?php

namespace Database\Factories;

use App\Models\Customer;
use App\Models\CustomerCart;
use App\Models\SupportedVendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class CustomerCartFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = CustomerCart::class;

    protected static $next = 1;
    protected $supported_venodr_ids;
    protected static $supported_venodr_id_next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $this->supported_venodr_ids = SupportedVendor::all('id')->pluck('id');
        $user = Customer::find(self::$next);

        $data = [
            'Customer_id'   =>  $user->id,
            'supported_vendor_id'   =>  $this->supported_venodr_ids[self::$supported_venodr_id_next++]
        ];
        if (self::$supported_venodr_id_next>=count($this->supported_venodr_ids))
        {
            self::$supported_venodr_id_next = 0;
            self::$next++;
        }
        return $data;
    }

    public static function getCount()
    {
        return Customer::count() * SupportedVendor::count();
    }
}
