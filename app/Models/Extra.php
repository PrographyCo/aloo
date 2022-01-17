<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Extra extends Model
{
    use HasFactory;

    protected $hidden = [
        'vendor_id',
        'created_at',
        'updated_at'
    ];

    public function getPriceAttribute($value)
    {
        if (request()->user('api'))
        {
            $package = request()->user('api')->getDiscountPackage();
            if ($package) {
                $discount = (int)$package->discount;
                if ($package->discount_type === '%')
                    $value -= ($discount * $value / 100);
                else
                    $value -= $discount;
                if ($value < 0) $value = 0;
            }
        }
        return (float)number_format($value,2,'.','');
    }
}
