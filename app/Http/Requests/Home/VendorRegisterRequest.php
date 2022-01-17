<?php

namespace App\Http\Requests\Home;

use Illuminate\Foundation\Http\FormRequest;

class VendorRegisterRequest extends FormRequest
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
            'legalName'         => 'required|string|max:200',
            'brandName'         => 'required|string|max:200',
            'commercialNo'      => 'required|string|max:50|unique:vendors,commercialNo',
            'supported_vendor'  => 'required|exists:supported_vendors,id',
            'city'              => 'required|exists:cities,id',
            'restaurant_type'   => 'nullable|required_if:supported_vendor,3|exists:restaurant_types,id',
            'kitchen_type'      => 'nullable|required_if:supported_vendor,3|exists:kitchen_types,id',
            'description'       => 'required|max:400|min:10',
            'email'             => 'required|email|unique:vendors,email',
            'phone'             => 'required|string|unique:vendors,phone',
            'bank'              => 'required|exists:banks,id',
            'iban'              => 'required|string|min:3|unique:vendors,bankIBAN',
            'beneficiaryName'   => 'required|string|min:3',
            'logo'              => 'required|image|file|max:10240',
            'image'             => 'required_if:supported_vendor,1,2|image|file|max:10240',
            'commercialRecord'  => 'required|file|max:10240',
            'speech'            => 'required|file|max:10240',
        ];
    }
}
