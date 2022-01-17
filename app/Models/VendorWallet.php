<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class VendorWallet extends Model
{
    use HasFactory;

    protected $fillable = [
        'branch_id',
        'amount',
        'reserved'
    ];

    protected $hidden = [
        'created_at',
        'updated_at',
        'branch_id',
        'reserved'
    ];

    protected $primaryKey = 'branch_id';

    public function user()
    {
        return $this->belongsTo(Branch::class);
    }
}
