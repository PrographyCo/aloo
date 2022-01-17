@extends('themes.driver.dashboard')

@section('name', 'car ||' . $car->name)
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('driver.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('driver.car.index') }}">car</a></li>
                    <li class="breadcrumb-item active">view car || {{ $car->name }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="row">
                <div class="col-12">
                    <div class="card p-2">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-12 d-flex">
                                    <div class="pt-3 h4 me-auto">{{ $car->name }}</div>
                                    <a href="{{ route('driver.car.edit', $car) }}" class="btn btn-secondary">edit</a>
{{--                                        <a href="" class="btn btn-warning">un ban</a>--}}
                                </div>
                                <div class="col-4 d-flex flex-column justify-content-center">
                                    <div>name: {{ $car->name }}</div>
                                    <div>username: {{ $car->login_number }}</div>
                                    <div>city: {{ $car->city->name_en }}</div>
                                </div>
                                <div class="col-4 d-flex flex-column justify-content-center">
                                    <div>phone: {{ $car->phone }}</div>
                                    <div>email: {{ $car->email }}</div>
                                    <div>id number: {{ $car->idNumber }}</div>
                                    <div>gender: {{ $car->gender }}</div>
                                </div>
{{--                                <div class="col-2">--}}
{{--                                    <div class="card info-card sales-card p-0">--}}
{{--                                        <div class="card-body p-3">--}}
{{--                                            <div class="d-flex align-items-center">--}}
{{--                                                <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">--}}
{{--                                                    <i class="bx bxs-cabinet"></i>--}}
{{--                                                </div>--}}
{{--                                                <h6 class="ml-3">{{ 1 }}</h6>--}}
{{--                                            </div>--}}
{{--                                        </div>--}}
{{--                                    </div>--}}
{{--                                </div>--}}
{{--                                <div class="col-2">--}}
{{--                                    <div class="card info-card sales-card p-0">--}}
{{--                                        <div class="card-body p-3">--}}
{{--                                            <div class="d-flex align-items-center">--}}
{{--                                                <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">--}}
{{--                                                    <i class="ri-store-2-fill"></i>--}}
{{--                                                </div>--}}
{{--                                                <h6 class="ml-3">{{ 1 }}</h6>--}}
{{--                                            </div>--}}
{{--                                        </div>--}}
{{--                                    </div>--}}
{{--                                </div>--}}
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12">
                    <div class="card m-0">
                        <div class="card-body p-5">
                            <h5 class="card-title pt-0">car orders</h5>
                            {{ $dataTable->table() }}
                        </div>
                    </div>
                </div>
            </div>

        </section>

    </main>
@endsection

@push('scripts')
    {{ $dataTable->scripts() }}
@endpush
