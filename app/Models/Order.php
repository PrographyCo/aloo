<?php

namespace App\Models;

use App\Helpers\APIHelper;
use Carbon\Carbon;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Order extends Model
{
    use HasFactory;
    public const STATUS = [
        1  => 'unconfirmed',
        2  => 'confirmed By Vendor',
        3  => 'confirmed By driver',
        4  => 'driver waiting',
        5  => 'ready',
        6  => 'in-delivery',
        7  => 'driver arrived',
        8  => 'delivered',
        9  => 'delivery confirmed',
        10 => 'done',
        11 => 'cancelled',
    ];
    public static $show = false;

    public function __construct(array $attributes = [])
    {
        if (!self::$show) $this->makeHidden(['delivery_price']);
        parent::__construct($attributes);
    }

    protected $fillable = [
        'branch_id',
        'place_id',
        'total_price',
        'isReady'
    ];

    protected $casts = [
        'total_price'       => 'float',
        'delivery_price'       => 'float',
        'items_sum_amount'  => 'integer',
        'start_time'  => 'timestamp',
        'deliver_time'  => 'timestamp',
        'car_rated' =>  'boolean',
        'branch_rated' =>  'boolean',
        'customer_rated'=>  'boolean',
        'created_at'    =>  'timestamp',
        'isReady'   =>  'boolean'
    ];

    protected $hidden = [
        'isReady',
        'start_time',
        'deliver_time',
        'distance',
        'customer_id',
        'branch_id',
        'place_id',
        'car_id',
        'car_rated',
        'branch_rated',
        'customer_rated',
        'updated_at',
        'created_at',
        'FID'
    ];

    function items()
    {
        return $this->hasMany(OrderItem::class);
    }

    function customer()
    {
        return $this->belongsTo(Customer::class);
    }

    public function place()
    {
        return $this->belongsTo(CustomerPlaces::class, 'place_id', 'id');
    }

    function branch()
    {
        return $this->belongsTo(Branch::class);
    }

    function cancel()
    {
        return $this->hasMany(OrderCancel::class);
    }

    public function car()
    {
        return $this->belongsTo(Car::class);
    }

    public function makeRealtime(): void
    {
        APIHelper::createOrderRealtime($this);
    }
    public function isRated($for)
    {
        return APIHelper::orderIsRated($this,$for);
    }
}
