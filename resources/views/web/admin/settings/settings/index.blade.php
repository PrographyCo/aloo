@extends('themes.admin.dashboard')

@section('name', 'customer')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.settings.general') }}">settings</a></li>
                    <li class="breadcrumb-item active">general config for application</li>
                </ol>
            </nav>
        </div><!-- End Page Title -->

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-title">general config for application</div>
                <div class="card-body">
                    <div class="row">
                        <div class="col d-flex flex-column align-items-center">
                            <h3>ios app</h3>
                            <div style="font-size: 55px;"><i class="bx bxl-apple"></i></div>
                            <form id="ios_status_form" action="{{ route('admin.settings.change.iosStatus') }}" method="post">
                                @csrf
                                <button type="button" class="btn btn-{{ !$ios ? "success" : "danger" }}" onclick="confirmForm($('#ios_status_form'))">{{ !$ios ? "activate" : "deactivate" }}</button>
                            </form>
                        </div>
                        <div class="col d-flex flex-column align-items-center">
                            <h3>android app</h3>
                            <div style="font-size: 55px;"><i class="bx bxl-android"></i></div>
                            <div>
                                <form id="android_status_form" action="{{ route('admin.settings.change.AndroidStatus') }}" method="post">
                                    @csrf
                                    <button type="button" class="btn btn-{{ !$android ? "success" : "danger" }}" onclick="confirmForm($('#android_status_form'))">{{ !$android ? "activate" : "deactivate" }}</button>
                                </form>
                            </div>
                        </div>
                        <div class="col d-flex flex-column align-items-center">
                            <h3>distance available</h3>
                            <div style="font-size: 55px;"><i class="bx bxs-map-pin"></i></div>
                            <div>
                                <form id="distance_status_form" action="{{ route('admin.settings.change.distance') }}" class="d-flex flex-column align-items-center" method="post">
                                    @csrf
                                    <input value="{{ $distance }}" class="form-control my-2" type="text" name="distance" id="distance">
                                    <button type="button" class="btn btn-success" onclick="confirmForm($('#distance_status_form'))">Submit</button>
                                </form>
                            </div>
                        </div>
                        <div class="col d-flex flex-column align-items-center">
                            <h3>delivery reservation</h3>
                            <div style="font-size: 55px;"><i class="bx bxs-dollar-circle"></i></div>
                            <div>
                                <form id="delivery_status_form" action="{{ route('admin.settings.change.delivery') }}" class="d-flex flex-column align-items-center" method="post">
                                    @csrf
                                    <input value="{{ $delivery }}" class="form-control my-2" type="text" name="delivery" id="distance"  />
                                    <button type="button" class="btn btn-success" onclick="confirmForm($('#delivery_status_form'))">Submit</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </main>

@endsection
