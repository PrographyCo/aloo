<?php

namespace Database\Factories;

use App\Models\LanguageField;
use Illuminate\Database\Eloquent\Factories\Factory;

class LanguageFieldFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = LanguageField::class;

    protected $fields = [
        [
            'table_name' => 'faqs',
            'fields'     => [
                'question', 'answer'
            ],
        ],
        [
            'table_name'    =>  'packages',
            'fields'        =>  [
                'name'
            ],
        ],
        [
            'table_name'    =>  'cities',
            'fields'        =>  [
                'name'
            ],
        ],
        [
            'table_name'    =>  'supported_vendors',
            'fields'        =>  [
                'name', 'description'
            ],
        ],
        [
            'table_name'    =>  'banks',
            'fields'        =>  [
                'name',
            ],
        ],
        [
            'table_name'    =>  'restaurant_types',
            'fields'        =>  [
                'name',
            ],
        ],
        [
            'table_name'    =>  'kitchen_types',
            'fields'        =>  [
                'name',
            ],
        ],
        [
            'table_name'    =>  'packages',
            'fields'        =>  [
                'name',
            ],
        ],
        [
            'table_name'    =>  'privacies',
            'fields'        =>  [
                'col',
            ],
        ]
    ];

    protected $next=0;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return $this->fields[$this->next++];
    }

    public static function getCount()
    {
        return count((new self())->fields);
    }
}
