<?php

namespace App\Helpers;

class CustomClass
{
    public function __construct(array $data)
    {
        foreach ($data as $key => $value)
        {
            $this->$key = $value;
        }
    }
}
