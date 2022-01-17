<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CustomerPlaces extends Model
{
    use HasFactory;

    protected $fillable = [
        'lon','lat','name','location_name','isDefault'
    ];

    protected $hidden = [
        'isDefault',
        'customer_id',
        'created_at',
        'updated_at'
    ];

    protected $casts = [
        'isDefault' =>  'boolean',
        'customer_id'   => 'integer'
    ];

    public function customer()
    {
        return $this->belongsTo(Customer::class);
    }

    public function orders()
    {
        $this->hasMany(Order::class);
    }
}
