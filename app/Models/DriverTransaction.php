<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class DriverTransaction extends Model
{
    use HasFactory;

    protected $fillable = [
        'driver_id',
        'amount',
        'direction'
    ];

    protected $hidden = [
        'driver_id',
        'direction'
    ];

    public function getAmountAttribute($value)
    {
        return $value * (($this->direction==='in')?1:-1);
    }

    public function driver()
    {
        return $this->belongsTo(Driver::class);
    }
}
