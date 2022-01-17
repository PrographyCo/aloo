<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class VendorWalletTrack extends Model
{
    use HasFactory;

    protected $fillable = [
        'branch_id',
        'price',
        'process',
        'direction',
        'message',
        'order_type',
        'reason',
        'isTransaction',
        'transaction_id'
    ];

    protected $hidden = [
        'process',
        'direction',
        'created_at',
        'updated_at',
        'branch_id',
        'isTransaction',
        'transaction_id'
    ];

    protected $casts = [
        'direction' =>  'json',
        'isTransaction'=> 'boolean'
    ];

    public function user()
    {
        return $this->belongsTo(Branch::class);
    }
    public function getDateAttribute($value)
    {
        return date('Y-m-d H:i A', strtotime($value));
    }

    public function getPriceAttribute($value)
    {
        return $value * ($this->process === 'out'? -1:1);
    }

    public function getOrderTypeAttribute($value)
    {
        return __('api/order.types.'.$value);
    }
}
