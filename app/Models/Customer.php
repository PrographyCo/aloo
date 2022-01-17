<?php

namespace App\Models;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;

class Customer extends Authenticatable
{
    use HasFactory, Notifiable, HasApiTokens, SoftDeletes;

    public static $show = false;
    protected $visibleItems = [
//        'ban',
    ];

    protected $fillable = [
        'img',
        'name',
        'phone',
        'email',
        'gender',
        'city_id',
        'password',
        'phone_type'
    ];

    protected $casts = [
        'email_verified_at'         => 'datetime',
        'phone_verified_at'         => 'datetime',
    ];

    protected $hidden = [
        'password',
        'FToken',
        'created_at',
        'updated_at',
        'email_verified_at',
        'phone_verified_at',
        'ban',
        'city_id'
    ];

    // this is a recommended way to declare event handlers
    public static function boot() {
        parent::boot();

        static::deleting(function($user) { // before delete() method call this
            $user->places()->delete();
            $user->wallet()->delete();
            $user->walletTrack()->delete();
            $user->packages()->delete();
            $user->favorites()->delete();
        });
        static::created(function ($user) {
            $user->wallet()->create([
                'amount' => 0
            ]);

            foreach (SupportedVendor::all('id')->pluck('id') as $id) {
                $user->carts()->create(['supported_vendor_id' => $id]);
            }
        });
    }

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

    public function hasVerifiedPhone()
    {
        return ! is_null($this->phone_verified_at);
    }

    public function hasVerifiedEmail()
    {
        return ! is_null($this->email_verified_at);
    }

    public function getImgAttribute($value)
    {
        if ($value)
        {
            return APIHelper::getImageUrl($value,'customer');
        }
        return $value;
    }
    public function getGenderAttribute($value)
    {
        return __('api/order.genders.'.$value);
    }

    public function city()
    {
        return $this->belongsTo(City::class);
    }

    public function places()
    {
        return $this->hasMany(CustomerPlaces::class);
    }
    public function defaultPlace()
    {
        return $this->hasOne(CustomerPlaces::class)->where('isDefault','=',true);
    }

    public function wallet()
    {
        return $this->hasOne(UserWallet::class);
    }

    public function walletTrack()
    {
        return $this->hasMany(UserWalletTrack::class)->select('id','price','message','reason','created_at as date','order_type');
    }
    public function transactions()
    {
        return $this->hasMany(UserWalletTrack::class)->whereNotNull('transaction_id');
    }

    public function packages()
    {
        return $this->hasMany(UserPackage::class)->where('status','done');
    }

    public function favorites()
    {
        return $this->hasMany(UserFavorite::class);
    }

    public function carts()
    {
        return $this->hasMany(CustomerCart::class);
    }

    public function rates()
    {
        return $this->hasMany(CustomerRate::class);
    }

    public function getCustomerRate()
    {
        return $this->rates()->select(\DB::raw('AVG(rate) as rate'));
    }
    public function orders()
    {
        return $this->hasMany(Order::class);
    }

    public function cartItem()
    {
        return $this->hasManyThrough(CartItem::class, CustomerCart::class);
    }

    public function getPackage()
    {
        return $this->getDeliveryPackage()?:$this->getDiscountPackage();
    }
    public function getDiscountPackage(){
        return $this
                ->packages()
                ->whereHas('package',function (Builder $q){
                    $q->where('type','=','discount');
                })
                ->with('package')
                ->first()??false;
    }
    public function getDeliveryPackage(){
        return $this
                ->packages()
                ->whereHas('package',function (Builder $q){
                    $q
                        ->where('type','=','freeDelivery');
                })
                ->where('orders','>',0)
                ->with('package')
                ->first()??false;
    }
}
