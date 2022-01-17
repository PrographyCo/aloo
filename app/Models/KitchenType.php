<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class KitchenType extends Model
{
    use HasFactory;
    protected $table = 'kitchen_types';


    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function restaurandData()
    {
        return $this->hasOne(RestaurantData::class);
    }
}
