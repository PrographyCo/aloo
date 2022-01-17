<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class DriverCityPrice extends Model
{
    use HasFactory;
    protected $fillable = [
        'price'
    ];

    protected $hidden = [
        'city_id',
        'created_at',
        'updated_at'
    ];

    protected $casts = [
        'price' =>  'float'
    ];

    protected $primaryKey = 'city_id';

    public function city()
    {
        $this->belongsTo(City::class);
    }
}
