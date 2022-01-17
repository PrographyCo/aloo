<?php

namespace App\Models;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Offer extends Model
{
    use HasFactory;

    public static $withDetails = true;
    public static $forTable = false;

    public static $show = false;
    protected $visibleItems = [
        'name_en',
        'name_ar',
        'description_en',
        'description_ar',
    ];

    public function __construct(array $attributes = [])
    {
        foreach (APIHelper::getLangFrom('name,description') as $item)
        {
            $this->hidden[] = $item;
        }
        if (self::$show)
        {
            foreach ($this->visibleItems as $visibleItem)
            {
                $this->makeVisible($visibleItem);
            }
        }
        parent::__construct($attributes);
    }

    protected $hidden = [
        'vendor_id',
        'category_id',
        'created_at',
        'updated_at'
    ];

    protected $casts = [
        'with'    =>  'json',
        'without' =>  'json',
        'drinks'  =>  'json',
        'extras'  =>  'json',
    ];

    public function getDrinksAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            foreach ($value as $key => $item) {
                $value[$key] = Drink::select('id','name_'.Controller::$lang.' as name','price','calories')->find($item);
            }
            if (self::$forTable)
                return implode(',',collect($value)->pluck('name')->toArray());
        }
        return $value;
    }
    public function getExtrasAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            foreach ($value as $key => $item) {
                $value[$key] = Extra::select('id','name_'.Controller::$lang.' as name','price','calories')->find($item);
            }
            if (self::$forTable)
                return implode(',',collect($value)->pluck('name')->toArray());
        }
        return $value;
    }
    public function getWithAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            if (self::$forTable)
                return implode(',',$value);
        }
        return $value;
    }
    public function getWithoutAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            if (self::$forTable)
                return implode(',',$value);
        }
        return $value;
    }
    public function getSizeAttribute($value)
    {
        if (self::$withDetails)
        {
                return __('api/order.sizes.'.(strtolower($value)));
        }
        return $value;
    }
    public function getImgAttribute($value)
    {
        return APIHelper::getImageUrl($value,'offer');
    }
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

    public function vendor()
    {
        return $this->belongsTo(Vendor::class);
    }
}
