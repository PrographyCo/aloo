<?php

namespace Database\Factories;

use App\Models\faq;
use Illuminate\Database\Eloquent\Factories\Factory;
use Mcamara\LaravelLocalization\LaravelLocalization;

class faqFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = faq::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $data = [];

        foreach (\LaravelLocalization::getSupportedLocales() as $lang => $prop)
        {
            $data['question_'.$lang] = ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(250)
                :$this->faker->text(250);

            $data['answer_'.$lang] = ($lang==='ar')?
                (new \Faker\Provider\ar_SA\Text(new \Faker\Generator()))->realText(250)
                :$this->faker->text(250);
        }
        $data['type'] = $this->faker->randomElement(['customer', 'driver', 'vendor']);

        return $data;
    }
}
