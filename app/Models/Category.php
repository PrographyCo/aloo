<?php

namespace App\Models;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Category extends Model
{
    use HasFactory;

    protected $guarded = [];

    protected $hidden = [
        'vendor_id',
        'parent_id',
        'created_at',
        'updated_at'
    ];

    public function vendor()
    {
        return $this->belongsTo(Vendor::class);
    }

    public function parent()
    {
        return $this->belongsTo(__CLASS__);
    }

    public function items()
    {
        return $this->hasMany(Item::class, 'category_id', 'id');
    }
}
