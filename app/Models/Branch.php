<?php

namespace App\Models;

use App\Models\Main\CustomModel;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;

class Branch extends Authenticatable
{
    use HasFactory, Notifiable, HasApiTokens, CustomModel;

    public static $show = false;
    protected $visibleItems = [
        'login_number',
        'available_status',
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

    protected $casts = [
        'available_status' => 'boolean'
    ];

    protected $fillable = [
        'name',
        'manager',
        'managerEmail',
        'managerPhone',
        'managerPosition',
        'city_id',
        'lon',
        'lat',
        'phone_type',
    ];

    protected $hidden = [
        'vendor_id',
        'available_status',
        'password',
        'city_id',
        'FToken',
        'ban',
        'login_number',
        'created_at',
        'updated_at',
    ];

    // this is a recommended way to declare event handlers
    public static function boot()
    {
        parent::boot();

//        static::created(function ($user) {
//            $user->wallet()->create([
//               'amount' => 0
//            ]);
//        });
    }

    public function isRestaurant()
    {
        return $this->vendor->isRestaurant();
    }

    public function vendor()
    {
        return $this->belongsTo(Vendor::class);
    }

    public function wallet()
    {
        return $this->hasOne(VendorWallet::class);
    }

    public function walletTrack()
    {
        return $this->hasMany(VendorWalletTrack::class)->select('id','price','message','reason','created_at as date','order_type');
    }
    public function transactions()
    {
        return $this->hasMany(VendorWalletTrack::class)->whereNotNull('transaction_id');
    }

    public function city()
    {
        return $this->belongsTo(City::class);
    }

    public function orders()
    {
        return $this->hasMany(Order::class)->withSum('items', 'amount') // get number of items and total amount
            ->with(['customer:id,name']); // get contact data like name and number and more
    }

    public function items()
    {
        return $this->hasMany(BranchItems::class);
    }
    public function offers()
    {
        return $this->hasMany(BranchOffer::class);
    }

    public function item()
    {
        return $this->belongsToMany(Item::class, 'branch_items', 'branch_id', 'item_id')->withPivot('amount');
    }

    public function rates()
    {
        return $this->hasMany(BranchRate::class);
    }

    public function getRateAttribute()
    {
        return $this->rates()->select(\DB::raw('AVG(rate) as rate'))->first()->rate??0;
    }

    public function getRateNumberAttribute()
    {
        return $this->rates()->select(\DB::raw('COUNT(rate) as number'))->first()->number??0;
    }

    public function appPercentage($all=false)
    {
        if ($all)
            return $this->sum_orders_prices() * (int) env('APP_PERCENTAGE',0);

        return $this->price * (int) env('APP_PERCENTAGE',0);
    }

    public function sum_orders_prices()
    {
        return $this->orders()->sum('total_price');
    }
}
