<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class OrderCancel extends Model
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'relationship',
        'message'
    ];

    protected $hidden = [
        'created_at',
        'updated_at'
    ];

//
//    function userCancel()
//    {
//        return $this->belongsTo($this->relationship, 'user_id', 'id');
//    }
}
