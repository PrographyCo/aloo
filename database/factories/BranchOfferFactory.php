<?php

namespace Database\Factories;

use App\Models\BranchOffer;
use App\Models\Offer;
use Illuminate\Database\Eloquent\Factories\Factory;

class BranchOfferFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = BranchOffer::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $offer = Offer::inRandomOrder()->first();
        return [
            'branch_id' => $offer->vendor->branches()->inRandomOrder()->first()->id,
            'offer_id'  => $offer->id,
        ];
    }
}
