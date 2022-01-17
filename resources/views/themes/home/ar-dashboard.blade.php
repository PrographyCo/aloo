@extends('themes.home.ar-theme')


@section('content')

    @include('themes.home.ar-header')

    @section('main')
    @show

    @include('themes.home.ar-footer')

@endsection


@section('script')
    @stack('scripts')
@endsection
