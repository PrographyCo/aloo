<?php

namespace App\Http\Requests\Vendor\Offer;

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
            'name_ar'              => 'required|string|max:200',
            'description_ar'       => 'required|string|max:445',
            'name_en'              => 'required|string|max:200',
            'description_en'       => 'required|string|max:445',
            'price'                => 'required|numeric',
            'amount'               => 'required|numeric',
            'category'             => 'required|exists:categories,id',
            'img'                  => 'required|image|file|max:10240',
            'with'                 => 'required|array|min:1',
            'with.*'               => 'required|string',
            'without'              => 'required|array|min:1',
            'without.*'            => 'required|string',
            'size'                 => 'in:b,m,s,B,M,S',
            'extras'               => 'nullable|array',
            'extras.*'             => 'required|exists:extras,id',
            'drinks'               => 'nullable|array',
            'drinks.*'             => 'required|exists:drinks,id',
        ];

    }

    public function withValidator($validator): void
    {
        $validator->after(function ($validator) {
            if (!in_array($this->input('category'), \Auth::user()->categories()->get('id')->pluck('id')->toArray())) {
                $validator->errors()->add('category', 'category not for you');
            }

            $extras_id = \Auth::user()->extras()->get('id')->pluck('id')->toArray();
            foreach ($this->input('extras') ?? [] as $extra_id){
                if (!in_array($extra_id, $extras_id) && \Auth::user()->isRestaurant()) {
                    $validator->errors()->add('extras', 'extras not for you');
                }
            }

            $drinks_id = \Auth::user()->drinks()->get('id')->pluck('id')->toArray();
            foreach ($this->input('drinks') ?? [] as $drink_id){
                if (!in_array($drink_id, $drinks_id) && \Auth::user()->isRestaurant()) {
                    $validator->errors()->add('drinks', 'drinks not for you');
                }
            }
        });
    }
}
