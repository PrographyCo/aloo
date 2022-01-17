<?php

namespace App\Models;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Item extends Model
{
    use HasFactory;

    public static $withDetails = true;

    public static $show = false;
    protected $visibleItems = [
        'name_en',
        'name_ar',
        'brief_desc_en',
        'brief_desc_ar',
    ];

    public function __construct(array $attributes = [])
    {
        foreach (APIHelper::getLangFrom('name,description,brief_desc') as $item)
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
        'amount'    =>  'integer',
        'images'    =>  'json',
        'optionals' =>  'json',
        'sizes'     =>  'json',
        'extras'    =>  'json',
        'drinks'    =>  'json',
        'isFavorite'=>  'boolean'
    ];

    public function getDrinksAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            foreach ($value ?? [] as $key => $item) {
                $value[$key] = Drink::select('id','name_'.Controller::$lang.' as name','price','calories')->find($item);
            }
        }
        return $value;
    }
    public function getExtrasAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            foreach ($value ?? [] as $key => $item) {
                $value[$key] = Extra::select('id','name_'.Controller::$lang.' as name','price','calories')->find($item);
            }
        }
        return $value;
    }
    public function getSizesAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            foreach ($value ?? [] as $key => $item) {
                unset($value[$key]);
                if (request()->user('api'))
                {
                    $package = request()->user('api')->getDiscountPackage();
                    if ($package) {
                        $discount = (int)$package->discount;
                        if ($package->discount_type === '%')
                            $item -= ($discount * $item / 100);
                        else
                            $item -= $discount;
                        if ($item < 0) $item = 0;
                    }
                }
                $value[__('api/order.sizes.'.(strtolower($key)))] = $item;
            }
        }
        return $value;
    }

    public function setSizesAttribute($value) {
        $value = $value ?? [];
        foreach ($value as $key => $price) {
            unset($value[$key]);
            if(strlen($key)>1) {
                $key = $key[0];
            }
            $value[strtolower($key)] = $price;
        }
        $this->attributes['sizes'] = json_encode($value);
    }

    public function getImagesAttribute($value)
    {
        $value = json_decode($value,true);
        foreach ($value ?? [] as $key=>$item)
        {
            $value[$key] = APIHelper::getImageUrl($item,'item');
        }
        return $value;
    }
    public function getImgAttribute($value)
    {
        return APIHelper::getImageUrl($value,'item');
    }
    public function getIsFavoriteAttribute()
    {
        if (request()->user('api'))
            return $this->isFavorite()->exists();
        else
            return false;
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
    public function getDrinks()
    {
        return Drink::whereIn('id',$this->drinks);
    }
    public function category()
    {
        return $this->belongsTo(Category::class);
    }
    public function branches()
    {
        return $this->belongsToMany(Branch::class, 'branch_items', 'item_id', 'branch_id')->withPivot('amount');
    }
    public function orderItem()
    {
        return $this->hasMany(OrderItem::class, 'item_id');
    }

    public function similar()
    {
        $this->similar = self::select('id','img','name_'.Controller::$lang.' as name', 'brief_desc_'.Controller::$lang.' as description','price','amount','amount_type')
            ->where('category_id','=', $this->category_id)
            ->limit(10)
            ->get()->each(function ($item){
                $item->is_favorite = $item->isFavorite;
            });
    }
    public function isFavorite()
    {
        if (request()->user('api'))
            return request()->user('api')->favorites()->where('item_id','=',$this->id);
        else
            return false;
    }
}
