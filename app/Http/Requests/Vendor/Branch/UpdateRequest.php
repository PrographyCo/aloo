<?php

namespace App\Http\Requests\Vendor\Branch;

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
        return [
            'name'              => 'required|string|max:200',
            'city'              => 'required|exists:cities,id',
            'manager'           => 'required|string|max:200',
            'managerPhone'      => 'required|string|max:200',
            'managerEmail'      => 'required|email|max:200',
            'managerPosition'   => 'required|string|max:200',
            'latitude'          => 'required|between:-90,90',
            'longitude'         => 'required|between:-180,180'
        ];
    }
}
