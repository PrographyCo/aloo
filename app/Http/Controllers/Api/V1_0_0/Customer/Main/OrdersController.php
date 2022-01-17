<?php

namespace App\Http\Controllers\Api\V1_0_0\Customer\Main;

use App\Helpers\APIHelper;
use App\Http\Controllers\Controller;
use App\Models\Order;
use App\Models\SupportedVendor;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class OrdersController extends Controller
{
    public function listOrders(Request $request,SupportedVendor $supported_vendor)
    {
        return APIHelper::jsonRender('', APIHelper::paginate(
            $request->user()->orders()
                ->select('id', 'updated_at as date', 'total_price as price','branch_id')
                ->whereHas('branch', function (Builder $q) use ($supported_vendor){
                return $q->whereHas('vendor', function (Builder $q) use ($supported_vendor){
                    return $q->where('supported_vendor_id', $supported_vendor->id);
                });
            })->with(['branch:id,vendor_id','branch.vendor:id,brandName as name,logo'])
            ->orderBy('id','desc')
        ,null, function ($order) {
                $order->vendor = $order->branch->vendor;
                unset($order->branch);
        }));
    }

    public function showOrder(Request $request, $order) {
        $order = $request->user()->orders()
            ->with('items:id,amount,item_price as price,data,item_id,order_id')
            ->with('items.item:id,img,name_'.self::$lang.' as name')
            ->with('branch.vendor:id,brandName as name,logo')
            ->with('car.driver:id,img')
            ->with('place')
            ->find($order);
        if ($order===null) return APIHelper::error('noPermission');

        $car_data = [
            'id'    =>  '',
            'name'  =>  '',
            'img'   =>  ''
        ];
        if (!is_null($order->car))
            $car_data = [
                'id'    =>  $order->car->id,
                'name'  =>  $order->car->name,
                'img'   =>  $order->car->driver->img
            ];

        $order->vendor = $order->branch->vendor;
        $order->date = strtotime($order->updated_at);
        $order->total = [
            'price'     => $order->total_price,
            'delivery'  => $order->delivery_price??0,
            'total'     => ((float)$order->total_price)+((float)$order->delivery_price)
        ];

        unset($order->total_price, $order->place->lat,$order->place->lat,$order->branch,$order->car);
        $order->car = $car_data;
        $order->isRated = APIHelper::getFirebaseValue($order)['customer_rated'];

        return APIHelper::jsonRender('',$order);
    }

    public function rate(Request $request, $type, $order)
    {
        $type = strtolower($type);
        $order = $request->user()->orders()->find($order);
        if ($order===null) return APIHelper::error('noPermission');
        if (!in_array($order->status,['done','delivered'])) return APIHelper::error('orderCantRate');
        if (!in_array($type,['branch','car'])) return APIHelper::error('noRateAllowed');
        if ($order->isRated($type)) return APIHelper::error('alreadyRated');

        $order->$type->rates()->create([
            'customer_id'=> $request->user()->id,
            'rate'      =>  $request->input('rate'),
            'message'   =>  $request->input('message')
        ]);
        APIHelper::changeOrderStatusNumber($order,null, null,($type==='car'),($type==='branch'));

        return APIHelper::jsonRender('success.success',[]);
    }
}
