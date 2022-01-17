<?php

namespace Database\Factories;

use App\Models\About;
use Illuminate\Database\Eloquent\Factories\Factory;

class AboutFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = About::class;

    protected static $fields = [
        [
            'data' => 'email', 'type' => 'email',
        ],
        [
            'data' => 'whatsapp', 'type' =>  'phoneNumber',
        ],
        [
            'data' => 'linkedin', 'type' =>  'url',
        ],
        [
            'data' => 'instagram', 'type' =>  'url',
        ],
        [
            'data' => 'twitter', 'type' =>  'url',
        ]
    ];

    protected static $next = 0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = self::$fields[self::$next++];
        $data['link'] = $this->faker->{$data['type']};
        unset($data['type']);
        return $data;
    }

    public static function getCount()
    {
        return count(self::$fields);
    }
}
