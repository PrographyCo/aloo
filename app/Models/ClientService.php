<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class ClientService extends Model
{
    use HasFactory;

    protected $fillable= ['userType','type','question','email','img'];
    protected $hidden= ['created_at','updated_at'];
}
