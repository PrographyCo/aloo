<?php

namespace Database\Factories;

use App\Models\Admin;
use Illuminate\Database\Eloquent\Factories\Factory;

class AdminFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Admin::class;


    protected array $fields = [
        [
            'login_number'  => '0000000001',
            'img'           => 'default.jpg',
            'full_name'     => 'admin',
            'phone'     => '0000000001',
            'email'     => 'admin@aloo-app.com',
            'gender'    => 'male',
            'password'  => '$2y$10$dvX2RD.xjhb064CupOCBkup0mtFyYt1lCefTggZbws5mgNF.uq9dS', // 123456789
        ], //'password' => '123456789'
    ];
    protected int $next = 0;
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = $this->fields[$this->next++];
        $data['email_verified_at'] = now();
        $data['phone_verified_at'] = now();

        return $data;
    }
}
