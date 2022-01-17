<?php

return [
    'types' =>  [
        'pay'   =>  'Pay',
        'back'  =>  'Return',
        'reserve'=> 'Reserve',
        'get'   =>  'Deposit'
    ],

    'sizes' =>  [
        'b' =>  'Big',
        'm' =>  'Medium',
        's' =>  'Small'
    ],

    'genders'=> [
        'male'   =>  'Male',
        'female' =>  'Female',
        'other'  =>  'Other',
        'prefer not to say'=>'Prefer Not To Say'
    ],
    'with'  =>  [
        'one'   =>  'With :last',
        'more'  =>  'With :data amd :last'
    ],
    'without'=>  [
        'one'   =>  'Without :last',
        'more'  =>  'Without :data amd :last'
    ],
];
