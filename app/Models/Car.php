<?php

namespace App\Models;

use App\Helpers\APIHelper;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;

class Car extends Authenticatable
{
    use HasFactory, Notifiable, HasApiTokens;
    public static $show = false;

    protected $visibleItems = [
        'login_number',
    ];
    protected $fillable = [
        'login_number',
        'img',
        'name',
        'idNumber',
        'phone',
        'email',
        'city_id',
        'gender',
        'password',
        'license',
        'phone_type'
    ];

    protected $casts = [
        'ban'               =>  'boolean',
    ];

    protected $hidden = [
        'login_number',
        'bankNumber',
        'city_id',
        'driver_id',
        'license',
        'ban',
        'password',
        'created_at',
        'updated_at',
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

    public function orders()
    {
        return $this->hasMany(Order::class);
    }

    public function getGenderAttribute($value)
    {
        return __('api/order.genders.'.strtolower($value));
    }
    public function getLicenseAttribute($value)
    {
        return APIHelper::getImageUrl($value, 'driver');
    }
    public function getImgAttribute($value)
    {
        return APIHelper::getImageUrl($value, 'driver');
    }

    public function city()
    {
        return $this->belongsTo(City::class);
    }

    public function wallet()
    {
        return $this->hasOne(DriverWallet::class);
    }

    public function walletTrack()
    {
        return $this->hasMany(DriverWalletTrack::class)->select('id','price','message','reason','created_at as date','order_type');
    }

    public function transactions()
    {
        return $this->hasMany(DriverWalletTrack::class)->whereNotNull('transaction_id');
    }

    public function rates()
    {
        return $this->hasMany(CarRate::class);
    }
    public function rate()
    {
        return $this->rates()->select(\DB::raw('AVG(rate) as rate'));
    }

    public function driver()
    {
        return $this->belongsTo(Driver::class);
    }

    public function sum_orders_times()
    {
        return $this->orders()->select(\DB::raw('SUM((deliver_time - start_time) / 100) as delivery_time_sum'));
    }

    public function sum_orders_prices()
    {
        return $this->orders()->sum('total_price');
    }

    // this is a recommended way to declare event handlers
    public static function boot() {
        parent::boot();

        static::deleting(function($user) { // before delete() method call this
            $user->wallet()->delete();
            $user->walletTrack()->delete();
        });

//        static::created(function ($user) {
//            $user->wallet()->create([
//                'amount' => 0
//            ]);
//        });
    }
}
