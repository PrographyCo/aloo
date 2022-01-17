@extends('themes.theme')


@section('content')

    @include('themes.driver.header')

    @include('themes.driver.sidebar')
    @section('main')
    @show
    @include('themes.driver.footer')

@endsection

@section('script')
@stack('scripts')
@endsection
