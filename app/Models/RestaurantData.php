<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class RestaurantData extends Model
{
    use HasFactory;

    protected $fillable = [
        'vendor_id',
        'restaurant_type_id',
        'kitchen_type_id'
    ];

    protected $hidden = [
        'vendor_id',
        'created_at',
        'updated_at',
        'restaurant_type_id',
        'kitchen_type_id'
    ];

    public function vendor()
    {
        return $this->belongsTo(Vendor::class);
    }
    public function restaurantType()
    {
        return $this->belongsTo(RestaurantTypes::class);
    }
    public function kitchenType()
    {
        return $this->belongsTo(KitchenType::class);
    }
}
