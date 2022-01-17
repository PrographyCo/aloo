<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class UserWallet extends Model
{
    use HasFactory;

    protected $fillable = [
        'customer_id',
        'amount',
        'reserved'
    ];

    protected $hidden = [
        'created_at',
        'updated_at',
        'customer_id',
        'reserved'
    ];

    protected $primaryKey = 'customer_id';

    public function user()
    {
        return $this->belongsTo(Customer::class);
    }
}
