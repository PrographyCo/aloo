<?php

namespace App\Http\Requests\Admin\Driver;

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
        $driver = $this->route('driver');
        return [
            'name'         => 'required|string|max:200',
            'email'        => 'required|email|unique:drivers,email,' . $driver->id. ',id',
//            'bankNumber'   => 'required|string|min:3|unique:drivers,bankNumber,' . $driver->id. ',id',
            'img'          => 'image|file|max:10240',
        ];
    }
}
