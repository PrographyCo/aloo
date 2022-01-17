<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CustomerRate extends Model
{
    use HasFactory;

    protected $fillable = [
        'car_id',
        'rate',
        'message'
    ];

    protected $hidden = [
        'car_id'
    ];

    public function driver()
    {
        return $this->belongsTo(Car::class);
    }
}
