<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class BranchRate extends Model
{
    use HasFactory;

    protected $fillable = [
        'customer_id',
        'rate',
        'message'
    ];

    protected $hidden = [
        'customer_id'
    ];

    public function customer()
    {
        return $this->belongsTo(Customer::class);
    }
}
