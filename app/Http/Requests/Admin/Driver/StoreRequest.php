<?php

namespace App\Http\Requests\Admin\Driver;

use Illuminate\Foundation\Http\FormRequest;

class StoreRequest extends FormRequest
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
            'password'     => 'required|string|max:200|confirmed|min:8|regex:/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$/',
//            'bankNumber'   => 'required|string|min:3|unique:drivers,bankNumber',
            'bank'         => 'required|exists:banks,id',
            'iban'         => 'required|image|file|max:10240',
            'bankRecipientName'         => 'required|string|max:200',
            'img'          => 'required|image|file|max:10240',
        ];
    }
}
