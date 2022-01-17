<?php

    return [
        'errors'    =>  [

            'headers'   =>  [
                'notProvided'   =>  'You Must Provide (:name) on request header',
                'noMatch'       =>  [
                    'array' =>  '(:name) must be one of this (:values)',
                    'str'   =>  '(:name) must be (:value)'
                ],
            ],
        ],

        'success'   =>  [
            'clientService' =>  'Question Sent successfully',
        ]
    ];
