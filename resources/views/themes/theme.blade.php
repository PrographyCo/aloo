<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta name="csrf-token" content="{{ csrf_token() }}">

    <title>aloo - @section('name')@show</title>
    <link href="{{ asset('favicon.ico') }}" rel="icon">
    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="{{ asset('vendor/bootstrap/css/bootstrap.min.css') }}" rel="stylesheet">
    <link href="{{ asset('vendor/bootstrap-icons/bootstrap-icons.css') }}" rel="stylesheet">
    <link href="{{ asset('vendor/remixicon/remixicon.css') }}" rel="stylesheet">
    <link href="{{ asset('vendor/boxicons/css/boxicons.min.css') }}" rel="stylesheet">

    <link href="{{ asset('vendor/select2/select2.min.css') }}" rel="stylesheet" />
    <link href="{{ asset('css/datatables.min.css') }}" rel="stylesheet" />


    <script src='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.js'></script>
    <link href='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.css' rel='stylesheet' />

    <link href="{{ asset('css/style.css') }}" rel="stylesheet">
{{--    * Template Name: NiceAdmin - v2.1.0--}}
{{--    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/--}}
</head>

<body class="toggle-sidebar">
@section('content')
@show

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<script src="{{ asset('vendor/jquery-3.6.0.min.js') }}"></script>
<script src="{{ asset('vendor/bootstrap/js/bootstrap.bundle.js') }}"></script>
<script src="{{ asset('vendor/php-email-form/validate.js') }}"></script>

<script src="{{ asset('vendor/select2/select2.full.min.js') }}"></script>

<script src="{{ asset('vendor/datatables/jquery.dataTables.min.js') }}"></script>
<script src="{{ asset('vendor/datatables/dataTables.bootstrap5.min.js') }}"></script>
<script src="{{ asset('vendor/datatables/buttons.server-side.js') }}"></script>

<script src="{{ asset('js/main.js') }}"></script>

@include('sweetalert::alert')

<script>
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
</script>

<script>
    $(document).ready(function () {
        $('select[multiple]').select2();
        let Fopt = document.querySelectorAll('select option:first-of-type');
        Fopt.forEach((opt) => {
            opt.selected = (opt.parentElement.multiple || opt.parentElement.selectedOptions.length!==0)?'':'selected';
            opt.disabled = 'disabled';
            opt.value = '';}
        );
    });


    function confirmForm(formToConfirm){
        Swal.fire({
            title: 'Are you sure want to do this?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, confirm!'
        }).then((result) => {
            if (result.isConfirmed) {
                formToConfirm.submit();
            }
        })
    }
</script>

@section('script')
@show
</body>

</html>
