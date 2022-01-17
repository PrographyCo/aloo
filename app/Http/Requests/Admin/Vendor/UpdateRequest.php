<?php

namespace App\Http\Requests\Admin\Vendor;

use Illuminate\Foundation\Http\FormRequest;

class UpdateRequest extends FormRequest
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
        $vendor = $this->route('vendor');
        return [
            'description'       => 'required|max:400|min:10',
            'city'              => 'required|exists:cities,id',
            'bank'              => 'required|exists:banks,id',
            'bankRecipientName' => 'required|string|min:3',
            'bankIBAN'          => 'required|string|min:3|unique:vendors,bankIBAN,'.$vendor->id.',id',//ignore if for the same vendor
            'image'             => 'image|file|max:10240',
            'logo'              => 'image|file|max:10240',
        ];
    }
}
