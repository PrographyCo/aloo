<?php

namespace Database\Factories;

use App\Models\City;
use App\Models\Customer;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Str;

class CustomerFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Customer::class;

    protected $fields = [
        [
            'img'   =>  'default.jpg',
            'name' => 'sohaib',
            'phone' => '0000000001',
            'email' => 'sohaib@test.com',
            'gender' => 'male',
            'password' => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ], //'password' => '123456789'
        [
            'img'   =>  'default.jpg',
            'name' => 'wijdan',
            'phone' => '0000000002',
            'email' => 'wijdan@test.com',
            'gender' => 'female',
            'password' => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ],
        [
            'img'   =>  'default.jpg',
            'name' => 'heba',
            'phone' => '0000000003',
            'email' => 'heba@test.com',
            'gender' => 'female',
            'password' => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ],
        [
            'img'   =>  'default.jpg',
            'name' => 'hany',
            'phone' => '0000000004',
            'email' => 'hany@test.com',
            'gender' => 'male',
            'password' => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ]
    ];
    protected $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = $this->fields[$this->next++];
        $data['city_id'] = City::inRandomOrder()->first()->id;
        $data['email_verified_at'] = now();
        $data['phone_verified_at'] = now();
	$data['phone_type'] = 
$this->faker->randomElement(['android','ios']);
        return $data;
    }

    /**
     * Indicate that the model's email address should be unverified.
     *
     * @return \Illuminate\Database\Eloquent\Factories\Factory
     */
    public function unverified()
    {
        return $this->state(function (array $attributes) {
            return [
                'email_verified_at' => null,
            ];
        });
    }
}
