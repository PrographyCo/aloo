<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Aloo - Payment</title>
    <link rel="stylesheet" href="{{ asset('css/home/bootstrap.min.css') }}">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="{{ asset('vendor/remixicon/remixicon.css') }}" rel="stylesheet">
    <link rel="stylesheet" href="{{ asset('css/home/style.css') }}">

</head>
<body>
<div class="sections mb-4">
    <div class="d-none d-lg-block">
        <div class="position-absolute " style="top: 9rem ;right: 6rem">
            <img src="{{ asset('img/home/bg/Path179.png') }}" alt="">
        </div>
        <div class="position-absolute " style="top: 17rem ;left: 8rem">
            <img src="{{ asset('img/home/bg/Path182.png') }}" alt="">
        </div>
        <div class="position-absolute " style="top: 9rem ;left: 21rem">
            <img src="{{ asset('img/home/bg/Path180.png') }}" alt="">
        </div>

        <div class="position-absolute " style="top: 44rem ;right: 21rem">
            <img src="{{ asset('img/home/bg/Path183.png') }}" alt="">
        </div>
    </div>




    <div class="container">
        <div class="text-center mb-5">
            <h1><img src="{{ asset('img/aloo-logo.png') }}" class="w-100 h-100" style="max-width: 150px;" alt=""></h1>
        </div>
        <div class="form  position-relative">
            <form class="m-auto form-owner" method="post" action="{{ route('payment.stc.post',['lang'=>$lang,'token'=>$token]) }}">
                @csrf
                <div class="form-group">
                    <input type="tel" class="form-control" name="phone" placeholder="Phone Number" readonly value="{{ $phone }}" />
                </div>
                <div class="input-group mb-3">
                    <input type="number" min="{{ $price }}" value="{{ $price }}" name="price" class="form-control" pattern="[0-9]{4,6}" placeholder="amount" readonly />
                    <div class="input-group-append">
                        <span class="input-group-text">SAR</span>
                    </div>
                </div>

                <div class="form-group">
                    <input type="text" class="form-control" name="verify_code" placeholder="Verify Code" required />
                </div>

                <button type="submit" class="btn btn-primary w-100 mb-4">Send</button>
            </form>
            <div class="d-none d-lg-block">
                <div class="position-absolute " style="top: 84% ;right: 21rem">
                    <img src="{{ asset('img/home/bg/Path180.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 91% ;left: 21rem">
                    <img src="{{ asset('img/home/bg/Path182.png') }}" alt="">
                </div>


            </div>

        </div>


    </div>

</div>

<script src="{{ asset('vendor/jquery-3.6.0.min.js') }}"></script>
<script src="{{ asset('js/home/popper.min.js') }}"></script>
<script src="{{ asset('vendor/bootstrap/js/bootstrap.min.js') }}"></script>
<script src="{{ asset('js/home/javascript.js') }}"></script>

</body>
</html>
