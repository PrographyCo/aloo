<?php

namespace App\Models;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;

class Vendor extends Authenticatable
{
    use HasFactory, Notifiable, HasApiTokens;

    public static $show = false;
    public static $imageLinks = true;
    protected $visibleItems = [
        'commercialNo',
    ];

    public function __construct(array $attributes = [])
    {
        if (self::$show)
        {
            foreach ($this->visibleItems as $visibleItem)
            {
                $this->makeVisible($visibleItem);
            }
        }

        parent::__construct($attributes);
    }

    protected $fillable = [
        'legalName',
        'brandName',
        'commercialNo',
        'city_id',
        'description',
        'minPrice',
        'email',
        'phone',
        'password',
        'supported_vendor_id',
        'bank_id',
        'bankIBAN',
        'bankRecipientName',
        'commercialRecord',
        'logo',
        'speech',
        'Ban',
        'custid',
        'confirm'
    ];

    protected $casts = [
        'email_verified_at' =>  'datetime',
        'phone_verified_at' =>  'datetime',
        'ban'               =>  'boolean',
        'confirm'           =>  'boolean',
        'lon'               =>  'float',
        'lat'               =>  'float'
    ];

    protected $hidden = [
        'commercialNo',
        'commercialRecord',
        'bank_id',
        'bankRecipientName',
        'bankIBAN',
        'supported_vendor_id',
        'city_id',
        'speech',
        'ban',
        'password',
        'created_at',
        'updated_at',
        'email_verified_at',
        'phone_verified_at',
        'FToken',
        'remember_token',
        'custid'
    ];

    public function hasVerifiedPhone()
    {
        return ! is_null($this->phone_verified_at);
    }

    public function getLogoAttribute($value)
    {
        if (self::$imageLinks)
            return APIHelper::getImageUrl($value,'vendor');
        return $value;
    }
    public function getImageAttribute($value)
    {
        if (self::$imageLinks)
            return APIHelper::getImageUrl($value,'vendor');
        return $value;
    }

    public function getSpeechAttribute($value)
    {
        return APIHelper::getImageUrl($value,'vendor');
    }

    public function getCommercialRecordAttribute($value)
    {
        return APIHelper::getImageUrl($value,'vendor');
    }

    public function isRestaurant()
    {
        return ($this->supported_vendor->name_en === 'restaurants');
    }

    public function supported_vendor()
    {
        return $this->belongsTo(SupportedVendor::class);
    }
    public function city()
    {
        return $this->belongsTo(City::class);
    }
    public function type($withId=false)
    {
        if (!$withId) return $this->supported_vendor->{'name_'.Controller::$lang};

        return $this->supported_vendor()->select('id', 'name_'.Controller::$lang.' as name')->first();
    }
    public function bank()
    {
        return $this->belongsTo(Bank::class);
    }

    public function data()
    {
        return $this->hasOne(RestaurantData::class);
    }

    public function kitchenType()
    {
        return $this->belongsToMany(KitchenType::class, 'restaurant_data', 'vendor_id', 'kitchen_type_id');
    }

    public function restaurantType()
    {
        return $this->belongsToMany(RestaurantTypes::class, 'restaurant_data', 'vendor_id', 'restaurant_type_id');
    }

    public function favorite()
    {
        return $this->hasMany(UserFavorite::class);
    }

//    created by hamza masoud
//    to get all orders for this vendor from branches
//    read for more here https://laravel.com/docs/8.x/eloquent-relationships#has-many-through
    public function orders()
    {
        return $this->hasManyThrough(Order::class, Branch::class);
    }

    public function items()
    {
        return $this->hasMany(Item::class)
            ->with('category:id,name_'.Controller::$lang.' as name');
    }

    public function min_price()
    {
        return $this->items()->min('price');
    }

    public function drinks()
    {
        return $this->hasMany(Drink::class);
    }

    public function extras()
    {
        return $this->hasMany(Extra::class);
    }

    public function categories()
    {
        return $this->hasMany(Category::class);
    }
    public function branches()
    {
        return $this->hasMany(Branch::class);
    }

    public function offers()
    {
        return $this->hasMany(Offer::class);
    }

    public function getRateAttribute()
    {
        $this->branches->each(function ($branch) use (&$rateTotal, &$rateNumber){

            if ($rateTotal===null) $rateTotal=0;
            if ($rateNumber===null) $rateNumber=0;

            $rateTotal += $branch->rate;
            $rateNumber += $branch->rate_number;
        });

        return [
            'total'     =>  (int)(($rateNumber!==0)?$rateTotal/$rateNumber:0),
            'number'    =>  $rateNumber
        ];
    }

    public function transactions()
    {
        return $this->hasMany(VendorTransaction::class);
    }

    public function wallet()
    {
        return $this->branches()->with('wallet')->get()->sum('wallet.amount');
    }
}
