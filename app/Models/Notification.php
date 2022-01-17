<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Notification extends Model
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'type',
        'title',
        'body'
    ];

    protected $hidden = [
        'created_at',
        'updated_at',
        'type',
        'user_id'
    ];

    public function user()
    {
        return $this->belongsTo('\App\Models\\'.ucfirst($this->type));
    }
}
