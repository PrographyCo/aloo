@extends('themes.theme')


@section('content')

    @include('themes.vendor.header')

    @include('themes.vendor.sidebar')
    @section('main')
    @show
    @include('themes.vendor.footer')

@endsection

@section('script')

@stack('scripts')
@endsection
