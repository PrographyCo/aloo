<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class VendorTransaction extends Model
{
    use HasFactory;

    protected $fillable = [
        'vendor_id',
        'amount',
        'direction'
    ];

    protected $hidden = [
        'vendor_id',
        'direction'
    ];

    public function getAmountAttribute($value)
    {
        return $value * (($this->direction==='in')?1:-1);
    }

    public function vendor()
    {
        return $this->belongsTo(Vendor::class);
    }
}
