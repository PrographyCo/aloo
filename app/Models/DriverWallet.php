<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class DriverWallet extends Model
{
    use HasFactory;

    protected $fillable = [
        'car_id',
        'amount',
        'reserved'
    ];

    protected $hidden = [
        'created_at',
        'updated_at',
        'car_id',
        'reserved'
    ];

    protected $primaryKey = 'car_id';

    public function user()
    {
        return $this->belongsTo(Car::class);
    }
}
