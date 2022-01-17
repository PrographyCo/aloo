<?php

namespace Database\Factories;

use App\Models\Config;
use Illuminate\Database\Eloquent\Factories\Factory;

class ConfigFactory extends Factory
{

    protected $model = Config::class;
    /**
     * Define the model's default state.
     *
     * @return array
     */
    protected static $next = 0;
    protected $fields = [
        [
            'key' => 'android',
            'value' => 'true',
        ],[
            'key' => 'ios',
            'value' => 'true',
        ],[
            'key' => 'distance',
            'value' => '7',
        ],
        [
            'key' => 'delivery_reservation',
            'value' => 500,
        ]
    ];

    public function definition()
    {
        return $this->fields[self::$next++];
    }
}
