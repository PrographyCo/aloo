<?php


namespace App\Helpers;
use App\Http\Controllers\Controller;
use App\Models\Notification;
use App\Models\Order;
use App\Models\Verification;
use GuzzleHttp\Client;
use Illuminate\Support\Facades\App;
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

class APIHelper
{
    const DATA_FIELD_NAME = 'items';

    protected static $orderBy = [
        'min_price_lowest'=>['min_price','asc'],
        'min_price_highest'=>['min_price','desc'],
        'rate'          =>  ['rates.total', 'desc'],

        'price_lowest'  =>  ['price','asc'],
        'price_highest' =>  ['price', 'desc'],
    ];

    // versioning:
    protected static $defaultApiNamespace='App\Http\Controllers\Api';

    // FireBase Database:
    protected static $database;

    public static function getVersion($version, &$prefix=Null, &$namespace=Null, $customNamespace=Null)
    {
        if (isset($version[0]))
        {
            if ($version[0] === 'v')
            {
                $version = ltrim($version,'v');
            }

            $version = explode('.',$version);
            $prefix = 'api/v'.$version[0];

            for ($i=1;$i<3;$i++)
            {
                if (!isset($version[$i]))
                {
                    $version[$i] = 0;
                }else {
                    $prefix.='.'.$version[$i];
                }
            }
            $version = 'v'.implode('.',$version);

            if ($customNamespace === NULL)
            {
                $customNamespace = self::$defaultApiNamespace;
            }

            $namespace = "{$customNamespace}\\".str_replace('.','_',strtoupper($version));
            return strtolower($version);
        }
        return null;
    }
    public static function getAllowedVersions()
    {
        $versions = [];
        foreach (config('app.api_versions') as $ver)
        {
            $versions[] = self::getVersion($ver);
        }

        return $versions;
    }

    public static function mustHaveHeader($header,$val, $next, \Closure $callback=Null)
    {
        if (request()->hasHeader($header) || request()->hasHeader(strtolower($header)))
        {
            $headerVal = request()->header($header)??request()->header(strtolower($header));
            if (is_array($val))
            {
                if (!in_array($headerVal, $val, true))
                {
                    return self::jsonRender('data.errors.headers.noMatch.array',[],400,[],[],['name'=>$header,'values'=>implode(', ', $val)]);
                }
            }else{
                if (strtolower($headerVal)!==strtolower($val))
                {
                    return self::jsonRender('data.errors.headers.noMatch.str',[],400,[],[],['name'=>$header,'value'=>$val]);
                }
            }
        }else{
            return self::jsonRender('data.errors.headers.notProvided',[],400,[],[],['name'=>$header]);
        }
        if ($callback!==Null)
        {
            $callback($headerVal);
        }
        return $next(request());
    }

    public static function error($key, $data=[],$errors=[],$langData=[],$code=400)
    {
        return self::jsonRender('errors.'.$key, $data,$code, [],$errors,$langData);
    }

    public static function jsonRender($key,$data,$code=200, array $more=[],$errors=null,$langData=[])
    {
        if ($data instanceof \Illuminate\Database\Eloquent\Collection) $data = isset($data[0])?[$data[0]->getTable() => $data->all()]:[];
        if(!is_object($data) || is_array($data)) $data = new CustomClass(array_merge($data,$more));
        if ($key!=='' && $key!==null) $key = (str_starts_with($key,'message: '))?trim($key,'message: '):__('api/'.$key,$langData);

        return response()->json([
            'message'   =>  $key,
            'data'      =>  $data,
            'errors'    =>  $errors??new \stdClass(),
            'content-language'  =>  App::getLocale(),
            'status'            =>  $code<400
        ], $code);
    }

    public static function getLangFrom($str)
    {
        $str = explode(',', $str);
        $arr = [];

        foreach ($str as $item)
        {
            foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props)
            {
                $arr[] = $item.'_'.$lang;
            }
        }

        return $arr;
    }

    public static function getImageUrl($image, $for='public')
    {
        return route('images.'.$for, ['hash' => $image]);
    }

    public static function getSimilar($class,$select='*',$num=8)
    {
        return $class::select($select)->inRandomOrder()->paginate($num);
    }
    public static function sendVerification($for, $user)
    {
        $code = (env('APP_ENV')!=='testing'&&env('APP_ENV')!=='local')?\Str::random(4):'1234';
        $verify = new Verification([
            'user_id'   =>  $user->id,
            'user_type' =>  strtolower(class_basename($user)),
            'verify_for'=>  strtolower($for),
            'code'      =>  \Hash::make($code)
        ]);
        $verify->save();

        if (strtolower($for)==='phone')
        {
            self::sendSMS($user->phone,$code);
        }

        return true;
    }

    public static function sendSMS($phone,$message)
    {
        \Http::withBasicAuth(env('SMS_BASIC_AUTH_USERNAME'),env('SMS_BASIC_AUTH_PASSWORD'))
            ->withHeaders([
                'Accept: application/json'
            ])
            ->post('http://basic.unifonic.com/wrapper/sendSMS.php',[
                'appsid' => env('SMS_APP_ID'),
                'msg' => $message,
                'to' => trim($phone,'+'),
                'sender' => env('SMS_SENDER_NAME'),
                'baseEncode' => false,
                'encoding' => 'UCS2'
            ]);
    }

    public static function stcPaymentRequest($phone,$amount)
    {
        return \Http::withOptions([
            'cert'      => \Storage::path('public/certs/aloo_app_com_db1ef_5c4ff_1672436786_99ea3a62c41c104acc6126d9821d216e.crt'),
            'ssl_key'   => \Storage::path('public/certs/db1ef_5c4ff_1e61df10526212552b7397ff39c00215.key')
        ])
            ->asJson()
            ->withHeaders([
                'X-ClientCode'=>' 61286911616'
            ])
            ->post('https://test.b2b.stcpay.com.sa/B2B.DirectPayment.WebApi/DirectPayment/V4/DirectPaymentAuthorize',
                ['DirectPaymentAuthorizeV4RequestMessage' => [
                'BranchID' => random_int(1, 10000),
                'TellerID' => random_int(1, 10000),
                'DeviceID' => random_int(1, 10000),
                'RefNum' => random_int(1, 10000),
                'BillNumber' => random_int(1, 10000),
                'MobileNo' => $phone,
                'Amount' => $amount,
                'MerchantNote' => 'Payment For Aloo-App',
            ]]);
    }
    public static function stcPaymentApprove($OtpReference,$OtpValue,$STCPayPmtReference)
    {
        return \Http::withOptions([
            'cert'      => \Storage::path('public/certs/aloo_app_com_db1ef_5c4ff_1672436786_99ea3a62c41c104acc6126d9821d216e.crt'),
            'ssl_key'   => \Storage::path('public/certs/db1ef_5c4ff_1e61df10526212552b7397ff39c00215.key')
        ])
            ->asJson()
            ->withHeaders([
                'X-ClientCode'=>' 61286911616'
            ])
            ->post('https://test.b2b.stcpay.com.sa/B2B.DirectPayment.WebApi/DirectPayment/V4/DirectPaymentConfirm',
                [
                    'DirectPaymentConfirmV4RequestMessage'  =>  [
                        'OtpReference'  =>  $OtpReference,
                        'OtpValue'      =>  $OtpValue,
                        'STCPayPmtReference'=>  $STCPayPmtReference,
                        'TokenReference'=>  \Str::random(6),
                        'TokenizeYn'    =>  true
                    ]
                ]);
    }

    private static function getDatabase()
    {
        if (is_null(self::$database))
        {
            $serviceAccount = ServiceAccount::fromValue(__DIR__.'/../../config/aloo-5d9ab-c671433ee61e.json');
            self::$database = (new Factory)
                ->withServiceAccount($serviceAccount)
                ->withDatabaseUri('https://aloo-5d9ab-default-rtdb.firebaseio.com/')
                ->createDatabase();
        }
    }
    private static function getReference(Order $order)
    {
        self::getDatabase();
        return self::$database
            ->getReference('orders/'.$order->id);
    }

    public static function getFirebaseValue($order)
    {
        return self::getReference($order)->getValue();
    }

    public static function createOrderRealtime(Order $order)
    {
        self::changeOrderStatusNumber($order,1, false, false,false);
    }
    public static function changeOrderStatusNumber(Order $order, $status, $customer_rated=null,$car_rated=null,$branch_rated=null)
    {

        $record = self::getReference($order);
        $data = $record->getValue();

        $record
            ->update([
                'status'         =>  $status??$data['status'],
                'branch_rated'   =>  $branch_rated??$data['branch_rated'],
                'car_rated'      =>  $car_rated??$data['car_rated'],
                'customer_rated' =>  $customer_rated??$data['customer_rated'],
                'last_update'    =>  now(),
            ]);

        return;
    }
    public static function orderIsRated(Order $order,$for)
    {
        return self::getReference($order)->getValue()[strtolower($for).'_rated'];
    }

    public static function notification($to,$title,$body)
    {
        Notification::create([
            'user_id'   =>  $to->id,
            'type'      =>  strtolower(class_basename($to)),
            'title'     =>  $title,
            'body'      =>  $body
        ]);
        return \Http::withHeaders(['Authorization' => 'key='.env('FIREBASE_SERVER_KEY')])
            ->post('https://fcm.googleapis.com/fcm/send',[
                "registration_ids" => [$to->FToken],
                "notification" => [
                    "title" => $title,
                    "body" => $body,
                ]
            ]);
    }

    public static function paginate($data, $pluck=null, $each=null, $sort=null)
    {
        $pg = $data->paginate(Controller::$paginateMax);
        $items = collect($pg->items());
        if ($each!==null)
        {
            $items->each($each);
        }

        $items = ($pluck===null)?$items:$items->pluck($pluck);
        if ($sort!==null&&$sort!=='default') {
            if (self::$orderBy[$sort][1]==='asc')
                $items = $items->sortBy(self::$orderBy[$sort][0]);
            else
                $items = $items->sortByDesc(self::$orderBy[$sort][0]);
        }

        return [
            'current_page' => $pg->currentPage(),
            self::DATA_FIELD_NAME  =>  array_values($items->toArray()),
            'per_page'  => $pg->perPage(),
            'total' =>  $pg->total()
        ];
    }
}
