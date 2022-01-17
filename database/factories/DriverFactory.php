<?php

namespace Database\Factories;
use App\Models\Bank;
use App\Models\City;
use App\Models\Customer;
use App\Models\Driver;
use App\Models\Vendor;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Str;

class DriverFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Driver::class;

    protected $fields = [
        [
            'img'       => 'default.jpg',
            'name'      => 'sohaib',
            'phone'     => '0000000001',
            'email'     => 'sohaib@test.com',
            'password'  => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ], //'password' => '123456789'
        [
            'img'       => 'default.jpg',
            'name'      => 'wijdan',
            'phone'     => '0000000002',
            'email'     => 'wijdan@test.com',
            'password'  => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ],
        [
            'img'       => 'default.jpg',
            'name'      => 'heba',
            'phone'     => '0000000003',
            'email'     => 'heba@test.com',
            'password'  => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ],
        [
            'img'      => 'default.jpg',
            'name'     => 'hany',
            'phone'    => '0000000004',
            'email'    => 'hany@test.com',
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
        if ($this->next<4)
        {
            $data = $this->fields[$this->next++];
        }else {
            $data = [
                'img'      => 'default.jpg',
                'name'     => $this->faker->name,
                'phone'    => $this->faker->unique->phoneNumber,
                'email'    => $this->faker->unique->email,
                'password' => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
            ];
        }

        $data['bank_id'] = Bank::inRandomOrder()->first()->id;
        $data['iban'] = $this->faker->iban(null,'', random_int(24,35));
        $data['bankRecipientName'] = $this->faker->text(random_int(25,100));
        $data['phone_verified_at'] = now();
        return $data;
    }
}

