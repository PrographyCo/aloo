<?php

namespace App\Http\Requests\Vendor\Extra;

use Illuminate\Foundation\Http\FormRequest;

class StoreRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }


    public function rules()
    {
        return [
            'name_ar'              => 'required|string|max:200',
            'name_en'              => 'required|string|max:200',
            'price'                => 'required|numeric',
            'calories'             => 'required|numeric',
        ];

    }
}
