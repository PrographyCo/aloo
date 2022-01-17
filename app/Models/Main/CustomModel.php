<?php

namespace App\Models\Main;

use App\Models\Config;

trait CustomModel
{
    public static function ByDistance($place, $distance=null)
    {
        if (is_null($distance)){
            $confDistance = Config::where('key', 'distance')->first();
            $distance = (!is_null($confDistance)) ? $confDistance->value : 7;
        }
        $distance=$distance??7;

        $lon = $place->lon;
        $lat = $place->lat;

        return self::select("*"
            ,\DB::raw("6371 * acos(cos(radians(" . $lat . "))
                * cos(radians(lat))
                * cos(radians(lon) - radians(" . $lon . "))
                + sin(radians(" .$lat. "))
                * sin(radians(lat))) AS distance"))
            ->having('distance', '<=', $distance)
            ->orderBy('distance','asc');
    }
}
