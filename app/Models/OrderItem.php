<?php

namespace App\Models;

use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class OrderItem extends Model
{
    use HasFactory;

    public static $withDetails = true;

    protected $fillable = [
        'item_id',
        'offer_id',
        'amount',
        'item_price',
        'data'
    ];

    protected $casts = [
        'amount' => 'integer',
        'item_price' => 'float',
        'data' => 'array',
    ];

    protected $hidden = [
        'order_id',
        'item_id',
        'offer_id',
        'updated_at',
        'created_at',
    ];


    public function getDataAttribute($value) {
        $value = json_decode($value, true);
        if (self::$withDetails)
        {
            if (!empty($value))
            {
                foreach ($value['drinks'] as $key => $item) {
                    $value['drinks'][$key] = Drink::select('id','name_'.Controller::$lang.' as name','price','calories')->find($item);
                }
                foreach ($value['extras'] as $key => $item) {
                    $value['extras'][$key] = Extra::select('id','name_'.Controller::$lang.' as name','price','calories')->find($item);
                }

                if (count($value['with'])>0){
                    $last = $value['with'][count($value['with']) - 1];
                    unset($value['with'][count($value['with']) - 1]);
                    $value['with'] = __('api/order.with.' . (count($value['with']) < 1 ? 'one' : 'more'), ['data' => implode(',', $value['with']), 'last' => $last]);
                }else{
                    $value['with'] = '';
                }

                if (count($value['without'])>0)
                {
                    $last = $value['without'][count($value['without'])-1];
                    unset($value['without'][count($value['without'])-1]);
                    $value['without'] = __('api/order.without.'.(count($value['without'])<1?'one':'more'), ['data' => implode(',', $value['without']), 'last' => $last]);
                }else{
                    $value['without'] = '';
                }

                if ($value['size']!=='')
                {
                    $value['size'] = __('api/order.sizes.'.strtolower($value['size']));
                }
            }
        }
        return $value;
    }
    public function setDataAttribute($value)
    {
        $value = (array)$value;
        if (!empty($value))
        {
            $value['size'] = strtoupper((strlen($value['size'])>1)?$value['size'][0]:$value['size']);
            $drinks = [];
            $extras = [];
            if (!empty($value['drinks']))
            {
                foreach ($value['drinks'] as $drink)
                {
                    $drinks[] = is_object($drink)?$drink->id:(int)$drink;
                }
            }
            if (!empty($value['extras'])) {
                foreach ($value['extras'] as $extra) {
                    $extras[] = (is_object($extra))?$extra->id:(int)$extra;
                }
            }
            $value['with']   = $value['with']??[];
            $value['without']= $value['without']??[];
            $value['drinks'] = $drinks;
            $value['extras'] = $extras;

        }
        $this->attributes['data'] = json_encode($value);
    }

    public function order()
    {
        return $this->belongsTo(Order::class);
    }

    function item()
    {
        return $this->belongsTo(Item::class);
    }

    function offer()
    {
        return $this->belongsTo(Offer::class);
    }
}
