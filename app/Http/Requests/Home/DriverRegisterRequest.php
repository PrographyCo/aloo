<?php

namespace App\Http\Requests\Home;

use Illuminate\Foundation\Http\FormRequest;

class DriverRegisterRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'name'         => 'required|string|max:200',
            'phone'        => 'required|string|unique:drivers,phone',
            'email'        => 'required|email|unique:drivers,email',
//            'bankNumber'   => 'required|string|min:3|unique:drivers,bankNumber',
            'bank'         => 'required|exists:banks,id',
            'iban'         => 'required|image|file|max:10240',
            'beneficiaryName'         => 'required|string|max:200',
            'img'          => 'required|image|file|max:10240',
        ];
    }
}
