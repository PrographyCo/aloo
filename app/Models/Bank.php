<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Bank extends Model
{
    use HasFactory;

    protected $fillable = [
        'name_en',
        'name_ar',
        'bank_id',
    ];
    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function fillable(array $fillable)
    {
        return \APIHelper::getLangFrom('name');
    }

    public function vendors()
    {
        return $this->hasMany(Vendor::class);
    }
}
