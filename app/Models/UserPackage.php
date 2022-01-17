<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class UserPackage extends Model
{
    use HasFactory;

    protected $fillable = [
        'package_id',
        'status',
        'expiry_date',
        'orders'
    ];

    protected $hidden = [
        'package_id',
        'customer_id',
        'updated_at',
        'created_at'
    ];

    protected $casts = [
        'expiry_date'   =>  'timestamp'
    ];

    public function user()
    {
        return $this->belongsTo(Customer::class);
    }

    public function package()
    {
        return $this->belongsTo(Package::class);
    }
}
