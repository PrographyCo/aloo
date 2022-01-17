<?php

namespace App\Http\Requests\Driver\Car;

use Illuminate\Foundation\Http\FormRequest;

class ChangePasswordRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'password'   => 'required|string|max:200|confirmed|min:8|regex:/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$/',
        ];
    }
}
