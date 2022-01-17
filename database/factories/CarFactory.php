<?php

namespace Database\Factories;
use App\Models\Car;
use App\Models\City;
use App\Models\Driver;
use Illuminate\Database\Eloquent\Factories\Factory;

class CarFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Car::class;

    protected $fields = [
        [
            'driver_id'     => 1,
            'login_number'  => '0000000001',
            'license'       => 'default.jpg',
            'name'          => 'sohaib',
            'phone'         => '0000000001',
            'email'         => 'sohaib@test.com',
            'gender'        => 'male',
            'password'      => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ], //'password' => '123456789'
        [
            'driver_id'     => 1,
            'login_number'  => '0000000002',
            'license'       => 'default.jpg',
            'name'          => 'wejdan',
            'phone'         => '0000000002',
            'email'         => 'heba@test.com',
            'gender'        => 'male',
            'password'      => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ], //'password' => '123456789'
        [
            'driver_id'     => 1,
            'login_number'  => '0000000003',
            'license'       => 'default.jpg',
            'name'          => 'hany',
            'phone'         => '0000000003',
            'email'         => 'wejdan@test.com',
            'gender'        => 'male',
            'password'      => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ], //'password' => '123456789'
        [
            'driver_id'     => 1,
            'login_number'  => '0000000004',
            'license'       => 'default.jpg',
            'name'          => 'heba',
            'phone'         => '0000000004',
            'email'         => 'hany@test.com',
            'gender'        => 'male',
            'password'      => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ], //'password' => '123456789'
    ];
    protected $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        if ($this->next<4)
        {
            $data = $this->fields[$this->next++];
        }else{
            $data = [
                'driver_id'     => Driver::inRandomOrder()->first()->id,
                'login_number'  => $this->faker->unique->randomNumber(6),
                'license'       => 'default.jpg',
                'name'          => $this->faker->name,
                'phone'         => $this->faker->unique->phoneNumber,
                'email'         => $this->faker->email,
                'gender'        => $this->faker->randomElement(['male','female','other']),
                'password'      => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
            ];
        }

        $data['idNumber'] = $this->faker->buildingNumber;
        $data['id_img'] = 'sdvsdvsdv';
        $data['city_id'] = City::inRandomOrder()->first()->id;
        return $data;
    }
}

