<?php

namespace App\Http\Requests\Admin\Settings\City;

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
            'name_en'    => 'required|string|max:200',
            'name_ar'    => 'required|string|max:200',
            'price'      => 'required|numeric',
        ];
    }
}
