<?php

namespace App\Http\Controllers\Api\V1_0_0\Customer\Auth;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Customer as User;
use App\Models\CustomerPlaces;
use App\Models\Item;
use App\Models\Notification;
use App\Models\UserFavorite;
use App\Models\Vendor;
use Illuminate\Http\Request;

class ProfileController extends Controller
{
    /**
     * get user data
     */
    public function user()
    {
        $user = \request()->user();
        $city = $user->city;
        $city->name = $city->{'name_'.self::$lang};

        foreach (APIHelper::getLangFrom('name') as $item) {
            unset($city->$item);
        }

        $user->city = $city;
        return APIHelper::jsonRender('',$user,200,[]);
    }
    /**
     * set user new data
     */
    public function editProfile(Request $request)
    {
        $logout = false;
        $request->validate([
            'name' => 'string|nullable',
            'phone' => 'string|nullable',
            'email' => 'email|nullable',
            'gender'=>  'in:male,female,other,prefer not to say,Male,Female,Other,Prefer Not To Say|nullable',
            'city'  =>  'string|numeric|exists:cities,id|nullable',
            'password' => 'string|confirmed|min:8|nullable',
            'img'   =>  'file|mimes:jpg,png,pdf|nullable'
        ]);

        $user = $request->user();

        if ($request->input('name') && $user->name !== $request->input('name'))
        {
            $user->name = $request->input('name');
        }
        if ($request->input('gender') && $user->gender !== $request->input('gender'))
        {
            $user->gender = strtolower($request->input('gender'));
        }
        if ($request->input('city') && $user->city_id !== $request->input('city'))
        {
            $user->city_id = $request->input('city');
        }
        if ($request->input('password'))
        {
            $user->password = bcrypt($request->input('password'));
        }
        if ($request->file('img'))
        {
            $user->img = \Storage::disk('customers')->put('',$request->file('img'));
        }

        if ($request->input('phone') && $user->phone!==$request->input('phone'))
        {
            if (User::where('phone',$request->input('phone'))->first() !== null) APIHelper::error('phoneTaker');
            $user->phone = $request->input('phone');
            $user->phone_verified_at = null;
            APIHelper::sendVerification('phone', $user);
            $logout = true;
        }
        if ($request->input('email') && $user->email!==$request->input('email'))
        {
            if (User::where('email',$request->input('email'))->first() !== null) APIHelper::error('emailTaker');
            $user->email = $request->input('email');
            $user->email_verified_at = null;
            APIHelper::sendVerification('email', $user);
        }

        if ($user->save())
        {
            if ($logout) $user->token()->revoke();

            return APIHelper::jsonRender('success.dataUpdate',$user->load('city:id,name_'.self::$lang.' as name'),200,[]);
        }
        return APIHelper::error('dataUpdate');
    }

    /**
     * get user places
     */
    public function places()
    {
        return APIHelper::jsonRender('',['places'=>\request()->user()->places()->orderBy('id','desc')->get()],200,[]);
    }
    /**
     * get user default place
     */
    public function defaultPlace()
    {
        return APIHelper::jsonRender('',['place'=>\request()->user()->defaultPlace],200,[]);
    }
    /**
     * set user new places
     */
    public function placesNew(Request $request)
    {
        $request->validate([
            'lon' => 'required|numeric',
            'lat' => 'required|numeric',
            'name'=> 'required|string',
            'location_name' =>  'string|nullable'
        ]);

        $places = $request->user()->places;
        $oldPlace = $places->filter(function ($p){
            return $p['name'] === \request('name');
        });
        if ($oldPlace->count()>0) return APIHelper::error('placeNameErr', $oldPlace);

        $oldPlace = $places->filter(function ($p){
            return ($p['lon'] === (float)\request('lon')) && ($p['lat'] === (float)\request('lat'));
        });
        if ($oldPlace->count()>0) return APIHelper::error('placeCoordErr',$oldPlace);

        $place = $request->user()->places()->create([
            'lon' => $request->input('lon'),
            'lat' => $request->input('lat'),
            'name' => $request->input('name'),
            'location_name'=> $request->input('location_name'),
            'isDefault' => $places->count()===0
        ]);

        return APIHelper::jsonRender('success.newPlace',$place,200,[]);
    }
    /**
     * Delete a place from user places
     */
    public function deletePlace(CustomerPlaces $place)
    {
        if ($place->customer_id !== \request()->user()->id) return APIHelper::error('noPermission',[],['public' => 'Permission Denied']);

        $place->delete();
        return APIHelper::jsonRender('success.deletePlace',[],200,[]);
    }

    /**
     *  GET the wallet data
     */
    public function wallet()
    {
        $user = \request()->user();
        $packages = $user->getPackage();
        return APIHelper::jsonRender('success.success',[
            'wallet' => $user->wallet,
            'wallet_details' => $user->walletTrack()->limit(10)->orderBy('id', 'desc')->get() ,
            'packages' => ($packages)?collect([$packages->package])->each(function ($p){
                $p->name = $p->{'name_'.self::$lang};
            }):[]
        ]);
    }

    /**
     *  POST add money to wallet using Apple Pay
     */
    public function applePay(Request $request)
    {
        $request->validate([
            'amount'    =>  'required|numeric',
            'transaction_id'    =>  'required|string',
            'object'    =>  'nullable|json'
        ]);
        $user = \request()->user();

        $user->wallet->update(
            ['amount'   =>  $user->wallet->amount + $request->input('amount')],
        );
        $user->walletTrack()->create([
            'price'     =>  $request->input('amount'),
            'process'   =>  'in',
            'direction' =>  $request->input('object'),
            'message'   =>  'Deposited',
            'order_type'=>  'get',
            'reason'    =>  'This Amount Deposited In Customer\'s Wallet',
            'isTransaction'=>true,
            'transaction_id'=>$request->input('transaction_id')
        ]);

        return APIHelper::jsonRender('success.success',[]);
    }

    /**
     *  GET the favorite data
     */
    public function getFavoriteVendors($service)
    {
        $data = \request()->user()->favorites()->with(['vendor:id,brandName as name'])->where('supported_vendor_id', $service)->get();
        return APIHelper::jsonRender('',['vendors' => $data->pluck('vendor')]);
    }
    /**
     *  GET the favorite data
     */
    public function getFavoriteByVendorId(Vendor $vendor)
    {
        $data = \request()->user()->favorites()->with(['item:id,name_'.self::$lang.' as name,price,img,amount'.(
            ($vendor->isRestaurant())? ' as calories'
            :',amount_type')
        ])->where('vendor_id', $vendor->id);
        return APIHelper::jsonRender('',\APIHelper::paginate($data,'item',function ($fav){
            $fav->item->is_favorite = $fav->item->isFavorite;
        }));
    }
    /**
     *  Add To Favorite
     */
    public function addToFavorite(Request $request)
    {
        $request->validate([
            'item_id'   => 'required|numeric|exists:items,id',
        ]);
        $item = Item::findOrFail($request->input('item_id'));

        if ($item->is_favorite)
            return APIHelper::error('favAlready');

        $fav = $request->user()->favorites()->create([
            'vendor_id' =>  $item->vendor->id,
            'supported_vendor_id' =>  $item->vendor->supported_vendor->id,
            'item_id'   =>  $request->input('item_id'),
        ]);

        return APIHelper::jsonRender('success.favAdd',$fav->load('item','vendor'),200,[]);
    }
    /**
     *  Add To Favorite
     */
    public function delFromFavorite(Request $request, $item_id)
    {
        $fav = $request->user()->favorites()->where('item_id',$item_id);
        if (!$fav->exists())
            return APIHelper::error('favNot');

        $fav->delete();
        return APIHelper::jsonRender('success.favDelete',[],200,[]);
    }

    public function setFirebaseToken(Request $request)
    {
        $request->validate([
            'token' =>  'required|string'
        ]);

        $request->user()->FToken = $request->input('token');
        $request->user()->save();

        return APIHelper::jsonRender('success.tokenAdded',[]);
    }

    public function notifications($type)
    {
        $user = \request()->user()??\request()->user('api')??\request()->user('api-cars')??\request()->user('api-branches');
        if (!$user)
            return APIHelper::error('unauthorized');

        return APIHelper::jsonRender('',APIHelper::paginate(
            Notification::
                where('user_id','=', $user->id)
                ->where('type','=', $type)
                ->latest()
                ->orderBy('id','desc')
        ));
    }
}
