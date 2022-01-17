@extends('themes.home.en-theme')


@section('content')

    @include('themes.home.en-header')

    @section('main')
    @show

    @include('themes.home.en-footer')

@endsection


@section('script')
    @stack('scripts')
@endsection
