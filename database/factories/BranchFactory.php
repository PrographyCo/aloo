<?php

namespace Database\Factories;

use App\Models\Branch;
use App\Models\City;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Facades\Hash;

class BranchFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Branch::class;

    protected $fields = [
        [
            'name'            => 'sohaib',
            'login_number'    => '0000000001',
            'password'        => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
            'manager'         => 'sohaib',
            'managerEmail'    => 'sohaib@test.com',
            'managerPhone'    => '0000000001',
            'managerPosition' => 'admin',
            'vendor_id'       => 1,
            'city_id'         => 1,
            'available_status'=> 1,
            'lon'             => '18.6269490000',
            'lat'             => '-52.0013000000'
        ],
        [
            'name'            => 'wijdan',
            'login_number'    => '0000000002',
            'password'        => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
            'manager'         => 'wijdan',
            'managerEmail'    => 'wijdan@test.com',
            'managerPhone'    => '0000000002',
            'managerPosition' => 'admin',
            'vendor_id'       => 2,
            'city_id'         => 1,
            'available_status'=> 1,
            'lon'             => '18.6269490000',
            'lat'             => '-52.0013000000'
        ],
        [
            'name'            => 'heba',
            'login_number'    => '0000000003',
            'password'        => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
            'manager'         => 'heba',
            'managerEmail'    => 'heba@test.com',
            'managerPhone'    => '0000000003',
            'managerPosition' => 'admin',
            'vendor_id'       => 3,
            'city_id'         => 1,
            'available_status'=> 1,
            'lon'             => '18.6269490000',
            'lat'             => '-52.0013000000'
        ],
        [
            'name'            => 'hany',
            'login_number'    => '0000000004',
            'password'        => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
            'manager'         => 'hany',
            'managerEmail'    => 'hany@test.com',
            'managerPhone'    => '0000000004',
            'managerPosition' => 'admin',
            'vendor_id'       => 4,
            'city_id'         => 1,
            'available_status'=> 1,
            'lon'             => '18.6269490000',
            'lat'             => '-52.0013000000'
        ],
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
        }else {
            $data = [
                'name'             => $this->faker->name,
                'login_number'     => $this->faker->unique->phoneNumber,
                'password'         => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
                'manager'          => $this->faker->name,
                'managerEmail'     => $this->faker->email,
                'managerPhone'     => $this->faker->phoneNumber,
                'managerPosition'  => $this->faker->jobTitle,
                'vendor_id'        => Vendor::inRandomOrder()->first()->id,
                'available_status' => $this->faker->boolean,
                'lon'             => '18.6269490000',
                'lat'             => '-52.0013000000'
            ];
        }
        $data['city_id'] = City::inRandomOrder()->first()->id;
        return $data;
    }
}
