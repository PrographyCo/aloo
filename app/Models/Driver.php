<?php

namespace App\Models;

use App\Helpers\APIHelper;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;

class Driver extends Authenticatable
{
    use HasFactory, Notifiable, HasApiTokens;

    protected $fillable = [
        'img',
        'name',
        'idNumber',
        'phone',
        'email',
        'city_id',
        'gender',
        'password',
        'bankNumber',
        'id_img',
        'license',
        'iban',
        'confirm',
        'ban',
        'custid'
    ];

    protected $casts = [
        'email_verified_at' => 'datetime',
        'phone_verified_at' => 'datetime',
        'ban'               =>  'boolean',
        'confirm'           =>  'boolean',
    ];

    protected $hidden = [
        'bankNumber',
        'id_img',
        'license',
        'iban',
        'ban',
        'password',
        'created_at',
        'updated_at',
        'email_verified_at',
        'phone_verified_at',
        'bankRecipientName',
        'custid'
    ];
    public function hasVerifiedPhone()
    {
        return ! is_null($this->phone_verified_at);
    }

    public function getImgAttribute($value)
    {
        return APIHelper::getImageUrl($value, 'driver');
    }

    public function getIbanAttribute($value)
    {
        return APIHelper::getImageUrl($value, 'driver');
    }

    public function cars()
    {
        return $this->hasMany(Car::class);
    }

    public function transactions()
    {
        return $this->hasMany(DriverTransaction::class);
    }

    public function wallet()
    {
        return $this->cars()->with('wallet')->get()->sum('wallet.amount');
    }
}
