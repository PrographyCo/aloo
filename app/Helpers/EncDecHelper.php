<?php

namespace App\Helpers;

class EncDecHelper
{
    public static function enc($data,$key=null) {
        $enc_key = base64_decode($key??env('ENC_KEY'));
        $openssl = openssl_random_pseudo_bytes(openssl_cipher_iv_length('aes-256-cbc'));
        $iv = ($openssl !== false)? $openssl:'';
        $enc_data = openssl_encrypt($data, 'aes-256-cbc', $enc_key, 0, $iv);
        return base64_encode($enc_data . '::' . base64_encode($iv));
    }
    public static function dec($data,$key=null){
        $enc_key = base64_decode($key??env('ENC_KEY'));
        list($enc_data, $iv) = array_pad(explode('::', base64_decode($data), 2),2,null);
        $iv = base64_decode($iv);
        return openssl_decrypt($enc_data, 'aes-256-cbc', $enc_key, 0, $iv);
    }
}
