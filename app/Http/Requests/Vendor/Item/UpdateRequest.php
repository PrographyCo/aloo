<?php

namespace App\Http\Requests\Vendor\Item;

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
        $data = [
            'name_ar'              => 'required|string|max:200',
            'brief_desc_ar'        => 'required|string|max:245',
            'description_ar'       => 'required|string|max:445',
            'name_en'              => 'required|string|max:200',
            'brief_desc_en'        => 'required|string|max:245',
            'description_en'       => 'required|string|max:445',
            'price'                => 'required|numeric',
            'amount'               => 'required|numeric',
            'category'             => 'required|exists:categories,id',
//            'category.*'             => 'required|exists:categories,id',
            'img'                  => 'image|file|max:10240',
        ];
        if (\Auth::user()->supported_vendor_id == 3)
        {
            $data = array_merge($data, [
            'optionals'          => 'required|array|min:1',
            'optionals.*'        => 'required|string',
            'size'             => 'required|array',
            'size.*'             => 'numeric',
            'extras'             => 'nullable|array',
            'extras.*'           => 'required|exists:extras,id',
            'drinks'             => 'nullable|array',
            'drinks.*'           => 'required|exists:drinks,id',
            ]);
        } else {
            $data = array_merge($data,[
                'amount_type'          => 'required|in:kgm,piece,letter,calories',
                'images'               => 'array|nullable',
                'images.*'             => 'image|file|max:10240',
            ]);
        }

        return $data;

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
