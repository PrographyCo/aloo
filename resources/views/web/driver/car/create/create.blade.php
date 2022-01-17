@extends('themes.driver.dashboard')

@section('name', 'add new car')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('driver.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('driver.car.index') }}">car</a></li>
                    <li class="breadcrumb-item active">add new car</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">{{ __('web/driver/car/create.main') }}</h5>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('driver.car.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                        <div class="col-md-12">
                            <div class="form-floating">
                                <input type="text" name="name" class="form-control w-100" id="name" placeholder="{{ __('web/driver/car/create.name') }}" required>
                                <label for="name">{{ __('web/driver/car/create.name') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" name="password" class="form-control w-100" id="password" placeholder="{{ __('web/driver/car/create.password') }}" required>
                                <label for="password">{{ __('web/driver/car/create.password') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" name="password_confirmation" class="form-control w-100" id="password_confirmation" placeholder="{{ __('web/driver/car/create.password_confirmation') }}" required>
                                <label for="password_confirmation">{{ __('web/driver/car/create.password_confirmation') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <select class="form-select" id="gender" name="gender" aria-label="{{ __('web/driver/car/create.gender') }}" required>
                                    <option selected="selected">{{ __('web/driver/car/create.gender') }}</option>
                                    <option value="male"  >{{ __('web/driver/car/create.male') }}</option>
                                    <option value="female">{{ __('web/driver/car/create.female') }}</option>
                                    <option value="other" >{{ __('web/driver/car/create.other') }}</option>
                                </select>
                                <label for="gender">{{ __('web/driver/car/create.gender') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <select class="form-select" id="city" name="city" aria-label="{{ __('web/driver/car/create.city') }}" required>
                                    <option selected="selected">{{ __('web/driver/car/create.city') }}</option>
                                    @foreach($cities as $city)
                                        <option value="{{ $city->id }}">{{ $city->name }}</option>
                                    @endforeach
                                </select>
                                <label for="city">{{ __('web/driver/car/create.city') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" name="idNumber" id="idNumber" placeholder="{{ __('web/driver/car/create.idNumber') }}" required>
                                <label for="idNumber">{{ __('web/driver/car/create.idNumber') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" name="phone" id="phone" placeholder="{{ __('web/driver/car/create.phone') }}" required>
                                <label for="phone">{{ __('web/driver/car/create.phone') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="email" class="form-control w-100" name="email" id="email" placeholder="{{ __('web/driver/car/create.email') }}">
                                <label for="email">{{ __('web/driver/car/create.email') }}</label>
                            </div>
                        </div>
                        <div class="col-md-3"></div>

                        <div class="col-md-6">
                            <label for="license">{{ __('web/driver/car/create.license') }}</label>
                            <input type="file" class="form-control w-100" name="license" id="license" placeholder="{{ __('web/driver/car/create.license') }}" required>
                        </div>

                        <div class="col-md-6">
                            <label for="id_img">{{ __('web/driver/car/create.id_img') }}</label>
                            <input type="file" class="form-control w-100" name="id_img" id="id_img" placeholder="{{ __('web/driver/car/create.id_img') }}" required>
                        </div>
                        <hr>
                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/driver/car/create.submit') }}</button>
                        </div>
                    </form>

                </div>
                @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
            </div>
        </section>

    </main>

@endsection
