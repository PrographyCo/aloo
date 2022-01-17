<?php

namespace App\Models;

use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CartItem extends Model
{
    use HasFactory;

    protected $fillable = [
        'customer_cart_id',
        'item_id',
        'offer_id',
        'branch_id',
        'amount',
        'data',
        'unit_price',
        'total_price'
    ];

    protected $hidden = [
        'customer_cart_id',
        'item_id',
        'branch_id',
        'created_at',
        'updated_at',
        'offer_id'
    ];

    protected $casts = [
        'data' => 'object',
        'amount'=>  'integer'
    ];

    public function getDataAttribute($value)
    {
        $value = json_decode($value, false, 512, JSON_THROW_ON_ERROR);
        if (!is_null($value))
        {
            $size = ($value->size)?__('api/order.sizes.'.(strtolower($value->size))):'';
            $drinks = [];
            $extras = [];
            foreach ($value->drinks as $drink)
            {
                $drinks[] = Drink::select('id','name_'.Controller::$lang.' as name','price','calories')->find($drink);
            }
            foreach ($value->extras as $extra)
            {
                $extras[] = Extra::select('id','name_'.Controller::$lang.' as name','price','calories')->find($extra);
            }
            $value->size = $size;
            $value->drinks = $drinks;
            $value->extras = $extras;
            return $value;
        }
        return new \stdClass();
    }

    public function setDataAttribute($value)
    {
        if (!empty($value))
        {
            $value->size = isset($value->size)?strtoupper((strlen($value->size)>1)?$value->size[0]:$value->size):'';
            $drinks = [];
            $extras = [];
            if (!empty($value->drinks))
            {
                foreach ($value->drinks as $drink)
                {
                    $drinks[] = is_object($drink)?$drink->id:(int)$drink;
                }
            }
            if (!empty($value->extras)) {
                foreach ($value->extras as $extra) {
                    $extras[] = (is_object($extra))?$extra->id:(int)$extra;
                }
            }
            $value->drinks = $drinks;
            $value->extras = $extras;

        }
        $this->attributes['data'] = json_encode($value);
    }

    public function customer_cart()
    {
        return $this->belongsTo(CustomerCart::class);
    }
    public function item()
    {
        return $this->belongsTo(Item::class);
    }

    public function offer()
    {
        return $this->belongsTo(Offer::class);
    }
    public function Branch()
    {
        return $this->belongsTo(Branch::class);
    }

    public function is_offer()
    {
        return !is_null($this->offer_id);
    }
}
