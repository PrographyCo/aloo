<?php

namespace App\Http\Requests\Driver\Car;

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
            'name'       => 'required|string|max:200',
            'password'   => 'required|string|max:200|confirmed|min:8|regex:/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$/',
            'city'       => 'required|exists:cities,id',
            'phone'      => 'required|string|max:200|unique:cars,phone',
            'email'      => 'email|max:200|unique:cars,email',
            'gender'     => 'required|in:male,female,other',
            'idNumber'   => 'required|numeric',
            'license'    => 'required|image|file|max:10240',
            'id_img'     => 'required|image|file|max:10240'
        ];
    }
}
