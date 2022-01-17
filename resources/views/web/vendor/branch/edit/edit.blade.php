@extends('themes.vendor.dashboard')

@section('name', 'vendor')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.branch.index') }}">branch</a></li>
                    <li class="breadcrumb-item active">edit branch || {{ $branch->name }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">{{ __('web/vendor/branch/create.main') }}</h5>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('vendor.branch.update', $branch) }}"
                          enctype="multipart/form-data">
                        @csrf
                        @method('PUT')
                        <div class="col-md-12">
                            <div class="form-floating">
                                <input type="text" name="name" class="form-control w-100" value="{{ $branch->name }}" id="name" placeholder="{{ __('web/vendor/branch/create.name') }}" required>
                                <label for="name">{{ __('web/vendor/branch/create.name') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <select class="form-select" id="city" name="city" aria-label="{{ __('web/vendor/branch/create.city') }}" required>
                                    <option selected="selected">{{ __('web/vendor/branch/create.city') }}</option>
                                    @foreach($cities as $city)
                                        <option value="{{ $city->id }}" @if($branch->city_id == $city->id) selected @endif>{{ $city->name }}</option>
                                    @endforeach
                                </select>
                                <label for="city">{{ __('web/vendor/branch/create.city') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" name="manager" value="{{ $branch->manager }}" id="manager" placeholder="{{ __('web/vendor/branch/create.manager') }}" required>
                                <label for="manager">{{ __('web/vendor/branch/create.manager') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" name="managerPhone" value="{{ $branch->managerPhone }}" id="managerPhone" placeholder="{{ __('web/vendor/branch/create.managerPhone') }}" required>
                                <label for="managerPhone">{{ __('web/vendor/branch/create.managerPhone') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="email" class="form-control w-100" name="managerEmail" value="{{ $branch->managerEmail }}" id="managerEmail" placeholder="{{ __('web/vendor/branch/create.managerEmail') }}" required>
                                <label for="managerEmail">{{ __('web/vendor/branch/create.managerEmail') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="managerPosition" class="form-control w-100" value="{{ $branch->managerPosition }}" id="managerPosition" placeholder="{{ __('web/vendor/branch/create.managerPosition') }}" required>
                                <label for="managerPosition">{{ __('web/vendor/branch/create.managerPosition') }}</label>
                            </div>
                        </div>
{{--                        <div class="col-3">--}}
{{--                            <div class="form-floating">--}}

{{--                            </div>--}}
{{--                        </div>--}}
                        <hr>
                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/vendor/branch/create.submit') }}</button>
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
