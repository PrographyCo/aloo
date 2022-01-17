<?php

namespace App\Http\Requests\Admin\Vendor;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

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
            'legalName'         => 'required|string|max:200',
            'brandName'         => 'required|string|max:200',
            'password'          => 'required|string|max:200|confirmed|min:8|regex:/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$/',
            'commercialNo'      => 'required|string|max:50|unique:vendors,commercialNo',
            'city'              => 'required|exists:cities,id',
            'supported_vendor'  => 'required|exists:supported_vendors,id',
            'restaurant_type'   => 'nullable|required_if:supported_vendor,3|exists:restaurant_types,id',
            'kitchen_type'      => 'nullable|required_if:supported_vendor,3|exists:kitchen_types,id',
            'description'       => 'required|max:400|min:10',
            'email'             => 'required|email|unique:vendors,email',
            'phone'             => 'required|string|unique:vendors,phone',
            'bank'              => 'required|exists:banks,id',
            'bankRecipientName' => 'required|string|min:3',
            'bankIBAN'          => 'required|string|min:3|unique:vendors,bankIBAN',
            'commercialRecord'  => 'required|file|max:10240',
            'logo'              => 'required|image|file|max:10240',
            'image'             => [
                Rule::requiredIf(request('supported_vendor')!=='3'),
                'image',
                'file',
                'max:10240'
            ],
            'speech'            => 'required|file|max:10240',
        ];
    }
}
