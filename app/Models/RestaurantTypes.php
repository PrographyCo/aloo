<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class RestaurantTypes extends Model
{
    use HasFactory;
    protected $table = 'restaurant_types';
    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function restaurantData()
    {
        return $this->hasOne(RestaurantData::class);
    }
}
