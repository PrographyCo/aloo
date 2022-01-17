<?php

namespace App\Http\Controllers\Api\V1_0_0\Data\Help;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\About;
use App\Models\Bank;
use App\Models\City;
use App\Models\ClientService;
use App\Models\faq;
use App\Models\KitchenType;
use App\Models\Package;
use App\Models\Privacy;
use App\Models\RestaurantTypes;
use App\Models\SupportedVendor;
use App\Models\Vendor;
use Illuminate\Http\Request;

class DataController extends Controller
{
    public function faq($service)
    {
        $data = faq::select('question_'.self::$lang.' as question','answer_'.self::$lang.' as answer','id')
            ->where('type','=',$service);

        return APIHelper::jsonRender('',APIHelper::paginate($data));
    }

    public function about()
    {
        return APIHelper::jsonRender('',About::all()->pluck('link','data'));
    }

    public function privacy($service)
    {
        $data = Privacy::select(self::$lang.' as data')->findOrFail($service);

        return APIHelper::jsonRender('',$data);
    }

    public function clientService(Request $request ,$service)
    {
        $request->validate([
            'type'  =>  'required|in:support,complaint,question',
            'question'=>'required|string|max:255',
            'img'   =>  'file|mimes:png,jpg,pdf|nullable',
            'email' =>  'email|nullable'
        ]);

        $img = null;
        if ($request->file('img'))
            $img = \Storage::disk('clientServices')->put('',$request->file('img'));

        $email = $request->input('email')??$request->user('api')->email;
        $clientService = ClientService::create([
            'userType'  =>  $service,
            'type'      =>  $request->input('type'),
            'question'  =>  $request->input('question'),
            'img'       =>  $img,
            'email'     =>  $email
        ]);

        return APIHelper::jsonRender('success.clientService',$clientService);
    }

    public function cities()
    {
        return APIHelper::jsonRender('',City::select('id','name_'.self::$lang.' as name')->get());
    }

    public function banks()
    {
        return APIHelper::jsonRender('',Bank::select('id','name_'.self::$lang.' as name')->get());
    }

    public function services()
    {
        return APIHelper::jsonRender('',SupportedVendor::select('id','name_'.self::$lang.' as name','description_'.self::$lang.' as description', 'img')->get());
    }

    public function restaurantType()
    {
        return APIHelper::jsonRender('',RestaurantTypes::all('id','name_'.self::$lang.' as name'));
    }
    public function kitchenType()
    {
        return APIHelper::jsonRender('',KitchenType::all('id','name_'.self::$lang.' as name'));
    }

    public function categories(Vendor $vendor)
    {
        return APIHelper::jsonRender('',$vendor->categories()->select('id','name_'.self::$lang.' as name','description_'.self::$lang.' as description')->get());
    }

    public function packages()
    {
        return APIHelper::jsonRender('', Package::all()->each(function ($p){
            $p->name = $p->{'name_'.self::$lang};
        }));
    }
}
