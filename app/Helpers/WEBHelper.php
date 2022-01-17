<?php

namespace App\Helpers;

class WEBHelper
{
    public static function truncate($string,$length=100,$append="&hellip;") {
        $string = trim($string);

        if(strlen($string) > $length) {
            $string = wordwrap($string, $length);
            $string = explode("\n", $string, 2);
            $string = $string[0] . $append;
        }

        return $string;
    }


    public static function textToSpan(string $text)
    {
        return "<span class='badge bg-secondary'>" . $text . "</span>";
    }

    public static function priceToText($price)
    {
        return $price . " SAR";
    }
}
