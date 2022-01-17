<?php

namespace Database\Factories;

use App\Models\Bank;
use App\Models\City;
use App\Models\SupportedVendor;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;

class VendorFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Vendor::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'legalName' => $this->faker->text(20),
            'brandName' => $this->faker->text(20),
            'commercialNo' => $this->faker->numberBetween(100000000,999999999),
            'city_id' => City::inRandomOrder()->first()->id,
            'description' => $this->faker->paragraph,
            'email' => $this->faker->unique->email,
            'phone' => $this->faker->unique->phoneNumber,
            'supported_vendor_id' => SupportedVendor::inRandomOrder()->first()->id,
            'password' => bcrypt('12345678'),

            'image' =>  'default.jpg',

            'bank_id' => Bank::inRandomOrder()->first()->id,
            'bankIBAN' => $this->faker->iban(null,'', random_int(24,35)),
            'bankRecipientName' => $this->faker->text(random_int(25,100)),

            'commercialRecord' => 'default.jpg',
            'logo' => 'default.jpg',
            'speech' => 'default.jpg',

            'email_verified_at' => now(),
            'phone_verified_at' => now(),
        ];
    }
}
