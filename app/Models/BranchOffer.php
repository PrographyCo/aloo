<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class BranchOffer extends Model
{
    use HasFactory;

    protected $fillable = [
        'branch_id',
        'offer_id'
    ];

    protected $hidden = [
        'branch_id',
        'offer_id',
        'created_at',
        'updated_at'
    ];

    public function branch()
    {
        return $this->belongsTo(Branch::class);
    }
    public function offer()
    {
        return $this->belongsTo(Offer::class);
    }
}
