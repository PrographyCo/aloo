<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class City extends Model
{
    use HasFactory;

    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function fillable(array $fillable)
    {
        return \APIHelper::getLangFrom('name');
    }

    public function vendors()
    {
        return $this->hasMany(Vendor::class);
    }

    public function customers()
    {
        return $this->hasMany(Customer::class);
    }

    public function drivers()
    {
        return $this->hasMany(Driver::class);
    }

    public function price()
    {
        return $this->hasOne(DriverCityPrice::class);
    }
}
