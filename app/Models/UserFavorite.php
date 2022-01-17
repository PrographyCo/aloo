<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class UserFavorite extends Model
{
    use HasFactory;

    protected $fillable = [
        'customer_id',
        'supported_vendor_id',
        'vendor_id',
        'item_id',
        'data'
    ];

    protected $hidden = [
        'customer_id',
        'supported_vendor_id',
        'vendor_id',
        'item_id',
        'created_at',
        'updated_at'
    ];

    protected $casts = [
        'data'  =>  'json',
        'customer_id' => 'integer',
        'vendor_id' => 'integer',
        'item_id' => 'integer',
    ];

    public function user()
    {
        return $this->belongsTo(Customer::class);
    }

    public function vendor()
    {
        return $this->belongsTo(Vendor::class);
    }

    public function supported_vendor()
    {
        return $this->belongsTo(SupportedVendor::class);
    }

    public function item()
    {
        return $this->belongsTo(Item::class);
    }
}
