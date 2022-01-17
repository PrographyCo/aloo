<?php

return [
    'types' =>  [
        'pay'   =>  'سحب',
        'back'  =>  'استعادة',
        'reserve'=> 'حجز',
        'get'   =>  'ايداع'
    ],

    'sizes' =>  [
        'b' =>  'Big',
        'm' =>  'Medium',
        's' =>  'Small'
    ],

    'genders'=> [
        'male'   =>  'ذكر',
        'female' =>  'انثى',
        'other'  =>  'غير ذلك',
        'prefer not to say'=>'أفضل عدم الافصاح'
    ],
    'with'  =>  [
        'one'   =>  'مع :last',
        'more'  =>  'مع'.' :data و :last'
    ],
    'without'=>  [
        'one'   =>  'بلا :last',
        'more'  =>  'بلا :data و :last'
    ],
];
