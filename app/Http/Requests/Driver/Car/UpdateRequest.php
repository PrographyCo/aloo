<?php

namespace App\Http\Requests\Driver\Car;

use Illuminate\Foundation\Http\FormRequest;

class UpdateRequest extends FormRequest
{

    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        $car = $this->route('car');
        return [
            'name'       => 'required|string|max:200',
            'city'       => 'required|exists:cities,id',
            'phone'      => 'required|string|max:200|unique:cars,phone,'.$car->id.',id',
            'email'      => 'email|max:200|unique:cars,email,'.$car->id.',id',
            'gender'     => 'required|in:male,female,other',
            'idNumber'   => 'required|numeric',
            'license'    => 'image|file|max:10240',
            'id_img'     => 'image|file|max:10240'
        ];
    }
}
