@extends('themes.admin.dashboard')

@section('name', 'edit driver || '. $driver->name)
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.driver.index') }}">driver</a></li>
                    <li class="breadcrumb-item active">edit driver || {{ $driver->name }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <div class="row align-content-center my-3">
                        <div class="col-2"><img src="{{ $driver->img }}" class="rounded" alt="" style="max-height: 80px" /></div>
                        <h5 class="card-title m-0 py-3 col-8 d-flex align-items-center">edit driver data ({{ $driver->name }})</h5>
                    </div>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('admin.driver.update', $driver) }}"
                          enctype="multipart/form-data">
                        @csrf
                        @method('put')
                        <div class="col-md-3">
                            <div class="form-floating">
                                <input type="text" name="name" value="{{ $driver->name }}" class="form-control w-100" id="name" placeholder="{{ __('web/admin/driver/create.name') }}" required>
                                <label for="name">{{ __('web/admin/driver/create.name') }}</label>
                            </div>
                        </div>

                        <div class="col-3">
                            <div class="form-floating">
                                <input type="email" name="email" value="{{ $driver->email }}" class="form-control w-100" id="email" placeholder="{{ __('web/admin/driver/create.email') }}" required>
                                <label for="email">{{ __('web/admin/driver/create.email') }}</label>
                            </div>
                        </div>

{{--                        <div class="col-3">--}}
{{--                            <div class="form-floating">--}}
{{--                                <input type="text" name="bankNumber" value="{{ $driver->bankNumber }}" class="form-control w-100" id="bankNumber" placeholder="{{ __('web/admin/driver/create.bankNumber') }}" required>--}}
{{--                                <label for="bankNumber">{{ __('web/admin/driver/create.bankNumber') }}</label>--}}
{{--                            </div>--}}
{{--                        </div>--}}
                        <div class="col-3">
                            <label for="img">{{ __('web/admin/driver/create.img') }}</label>
                            <input class="form-control w-100" type="file" id="img" name="img">
                        </div>
                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/admin/driver/create.submit') }}</button>
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
