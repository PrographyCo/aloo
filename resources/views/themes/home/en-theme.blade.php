<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Aloo</title>
    <link rel="stylesheet" href="{{ asset('css/home/bootstrap.min.css') }}">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="{{ asset('vendor/remixicon/remixicon.css') }}" rel="stylesheet">
    <link rel="stylesheet" href="{{ asset('css/home/style.css') }}">

</head>
<body>
@section('content')
@show

<script src="{{ asset('vendor/jquery-3.6.0.min.js') }}"></script>
<script src="{{ asset('js/home/popper.min.js') }}"></script>
<script src="{{ asset('vendor/bootstrap/js/bootstrap.min.js') }}"></script>
<script src="{{ asset('js/home/javascript.js') }}"></script>

@include('sweetalert::alert')
@section('script')
@show
</body>
</html>
