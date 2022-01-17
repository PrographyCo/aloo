<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class DriverWalletTrack extends Model
{
    use HasFactory;

    protected $fillable = [
        'car_id',
        'price',
        'process',
        'direction',
        'message',
        'order_type',
        'reason',
        'isTransaction',
        'transaction_id'
    ];

    protected $casts = [
        'direction' =>  'json',
        'isTransaction'=> 'boolean'
    ];

    protected $hidden = [
        'car_id',
        'process',
        'direction',
        'created_at',
        'updated_at',
        'updated_at',
        'isTransaction',
        'transaction_id'
    ];

    public function getPriceAttribute($value)
    {
        return $value * ($this->process === 'out'? -1:1);
    }

    public function getOrderTypeAttribute($value)
    {
        return __('api/order.types.'.$value);
    }

    public function getDateAttribute($value)
    {
        return date('H:i A, d/m/Y',strtotime($value));
    }

    public function user()
    {
        return $this->belongsTo(car::class);
    }
}
