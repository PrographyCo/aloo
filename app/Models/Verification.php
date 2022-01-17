<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Verification extends Model
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'user_type',
        'verify_for',
        'code'
    ];

    protected $hidden = [
        'created_at',
        'updated_at',
        'user_type'
    ];
}
