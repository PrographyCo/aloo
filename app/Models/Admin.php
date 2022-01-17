<?php

namespace App\Models;

use App\Helpers\APIHelper;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;

class Admin extends Authenticatable
{
    use HasFactory;

    public function getGenderAttribute($value)
    {
        return __('api/order.genders.'.strtolower($value));
    }

    public function getImgAttribute($value)
    {
        return APIHelper::getImageUrl($value, 'driver');
    }

    public function notifications()
    {
        return $this->belongsTo(Notification::class,'id','user_id')->where('type','=','admin');
    }
}
