<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CustomerPasswordReset extends Model
{
    use HasFactory;
    protected $table = 'password_resets_customers';

    protected $fillable = [
        'phone',
        'token'
    ];
    protected $hidden = [
        'created_at'
    ];

}
