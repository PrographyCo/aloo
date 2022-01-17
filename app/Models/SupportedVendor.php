<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class SupportedVendor extends Model
{
    use HasFactory;

    protected $guarded = [];

    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function getImgAttribute($value)
    {
        return \APIHelper::getImageUrl($value, 'supported_vendor');
    }

    public function isRestaurant()
    {
        return ($this->name_en === 'restaurants');
    }

    public function vendors()
    {
        return $this->hasMany(Vendor::class);
    }
}
