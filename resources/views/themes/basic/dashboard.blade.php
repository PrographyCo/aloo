@extends('themes.theme')


@section('content')

    @include('themes.admin.header')

    @include('themes.admin.sidebar')
    @section('main')
    @show
    @include('themes.admin.footer')

@endsection

@section('script')
@stack('scripts')
@endsection
