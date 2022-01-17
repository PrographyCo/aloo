<?php

namespace App\Models;

use App\Helpers\APIHelper;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Package extends Model
{
    use HasFactory;
    public static $hideName = true;

    public function __construct(array $attributes = [])
    {
        if (self::$hideName)
            $this->makeHidden([...APIHelper::getLangFrom('name'),'discount_type']);

        parent::__construct($attributes);
    }

    protected $hidden = [
        'created_at',
        'updated_at',
    ];

    public function getDiscountAttribute($value)
    {
        if (self::$hideName)
            return $value.$this->discount_type;

        return $value;
    }

    public function users()
    {
        return $this->hasMany(UserPackage::class);
    }
}
