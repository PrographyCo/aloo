<?php

namespace App\Http\Controllers\Api\V1_0_0\Branch\Main;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class MainController extends Controller
{

    public function changeStatusAvailable(Request $request)
    {
        $request->user()->available_status = !$request->user()->available_status;
        $request->user()->save();

        return \APIHelper::jsonRender('success.changeStatus', [
            'status' => !$request->user()->available_status
        ]);
    }

    public function getStatus(Request $request)
    {
        return \APIHelper::jsonRender('', ['status' => $request->user()->available_status]);
    }

    public function items(Request $request)
    {
        $request->validate([
            'category_id' => 'exists:'.(new \App\Models\Category())->getTable().',id',
        ]);

        $user = $request->user();

        $items = $user->items()
            ->with('item:id,img,name_'.self::$lang.' as name,brief_desc_'.self::$lang.' as description,price,'
                .(!$user->isRestaurant()?
                    'amount,amount_type,images'
                    :'amount as calories,optionals,sizes,extras,drinks'
                )
            );

        if ($category_id = $request->input('category_id') ?? "")
            $items = $items->where('category_id', $category_id);

        return \APIHelper::jsonRender('', \APIHelper::paginate($items));
    }

    public function category(Request $request)
    {
        $category = $request->user()->vendor->categories()->select('id', 'name_' . self::$lang . ' as name')->get();
        return \APIHelper::jsonRender('', $category);
    }

    public function profile(Request $request)
    {
        $user = $request->user();

        $user->legal_name = $user->vendor->legalName;
        $user->brand_name = $user->vendor->brandName;
        $user->commercial_number = $user->vendor->commercialNo;
        $user->city_name = $user->city->{'name_'.self::$lang};
        $user->type = $user->vendor->type();
        $user->description = $user->vendor->description;

        if ($user->isRestaurant())
        {
            if (!is_null($user->vendor->data))
            {
                $user->restaurant_type = $user->vendor->data->restaurantType->{'name_'.self::$lang};
                $user->kitchen_type = $user->vendor->data->kitchenType->{'name_'.self::$lang};
            }else
            {
                $user->restaurant_type = '';
                $user->kitchen_type = '';
            }
        }

        $user->min_price = $user->vendor->minPrice;
        $user->email = $user->vendor->email;

        unset($user->vendor, $user->city);
        return \APIHelper::jsonRender('', $user);
    }

    /**
     *  GET the wallet data
     */
    public function wallet()
    {
        $user = \request()->user();
        return \APIHelper::jsonRender('',[
            'wallet' => $user->wallet,
            'wallet_details' => $user->walletTrack()->limit(10)->orderBy('id', 'desc')->get(),
        ]);
    }

    public function setFirebaseToken(Request $request)
    {
        $request->validate([
            'token' =>  'required|string'
        ]);

        $request->user()->FToken = $request->input('token');
        $request->user()->save();

        return \APIHelper::jsonRender('success.tokenAdded',[]);
    }

    public function data()
    {
        // TODO: Do The Timing Shit
        $time = [
            'today' =>  1,
            'week'  =>  7,
            'month' =>  30,
            'year'  =>  365
        ];

        \request()->validate([
            'time'  =>  'in:'.implode(',',array_keys($time))
        ]);

        $user = request()->user();
        $return = new \stdClass();

        $return->name = $user->name;
        $return->img = $user->vendor->logo;
        $return->rate = [
            'total' =>  $user->rate,
            'by'    =>  $user->rate,
        ];
        $return->orders = $user->orders->count();
        $return->app_percentage = $user->appPercentage(true);
        $return->income = number_format($user->sum_orders_prices(), 2);
        $return->exchanged = $user->transactions->sum('price');

        return \APIHelper::jsonRender('',$return,200,[]);
    }
}
