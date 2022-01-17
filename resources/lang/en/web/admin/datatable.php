<?php

return [
    'branch' =>  [
        'name'  =>  'vendor name',
        'all'   =>  'all orders',
    ],

    'customer' =>  [
        'img'     => 'image',
        'name'    => 'customer name',
        'email'   => 'customer email',
        'phone'   => 'customer phone',
        'gender'  => 'customer gender',
    ],

    'vendor' =>  [
        'legalName'    => 'legal name',
        'brandName'    => 'brand name',
        'commercialNo' => 'commercial no.',
        'kitchenType' => 'kitchen type',
        'restaurantType' => 'restaurant type',
        'phone'        => 'phone',
        'create_at'    => 'create date',
        'type'         => 'type',
    ],

    'driver' =>  [
        'name'         => 'name',
        'idNumber'     => 'identity No.',
        'commercialNo' => 'commercial no.',
        'phone'        => 'phone',
        'email'        => 'email',
        'type'         => 'type',
        'create_at'    => 'create date',
        'cars_count'   => 'cars',
        'phoneVerify'   => 'phone verify',
    ],


    'customer-main' =>  [
        'img'               => 'image',
        'name'              => 'name',
        'email'             => 'email',
        'phone'             => 'phone',
        'gender'            => 'gender',
        'city'              => 'city',
        'wallet_amount'     => 'wallet amount',
        'ban'               => 'is ban',
        'rates_count'       => 'rates count',
        'created_at'        => 'create date',
    ],

    'city' =>  [
        'name_en'           => 'name en.',
        'name_ar'           => 'name ar.',
        'created_at'        => 'create date',
        'price'             => 'Delivery Price (per kilometer)',
    ],
    'bank' =>  [
        'name_en'           => 'name en.',
        'name_ar'           => 'name ar.',
        'created_at'        => 'create date',
    ],
    'package'   =>  [
        'price' =>  'Price',
        'orders'=>  'Orders Number',
        'days'  =>  'Days',
        'type'  =>  'Package Type',
        'types' =>  [
            'freeDelivery'  =>  'Free Delivery',
            'discount'      =>  'Discount'
        ],
        'discount'=>'Discount',
        'dType' =>  'Discount Type',
        'dTypes'=>  [
            '%' =>  '%',
            'SR'=>  'Saudi Riyal'
        ]
    ],
    'faq'   =>  [
        'question'  =>  [
            'en'    =>  'English Question',
            'ar'    =>  'Arabic Question'
        ],
        'answer'    =>  [
            'en'    =>  'English Answer',
            'ar'    =>  'Arabic Answer'
        ]
    ],
    'clientService' =>  [
        'userType'  =>  'User Type',
        'type'      =>  'Service Type',
        'question'  =>  'Message',
        'img'       =>  'Image',
        'email'     =>  'Email',
    ]
];
