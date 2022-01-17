@extends('themes.admin.dashboard')

@section('name', 'driver')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.driver.index') }}">driver</a></li>
                    <li class="breadcrumb-item active">add new driver</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">{{ __('web/admin/driver/create.main') }}</h5>
                    <form id="storeForm" class="" method="POST" action="{{ route('admin.driver.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                    <div class="row g-3">
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" name="name" value="{{ request()->old('name') }}" class="form-control w-100" id="name" placeholder="{{ __('web/admin/driver/create.name') }}" required>
                                <label for="name">{{ __('web/admin/driver/create.name') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" id="phone" name="phone" value="{{ request()->old('phone') }}" placeholder="{{ __('web/admin/driver/create.phone') }}" required>
                                <label for="phone">{{  __('web/admin/driver/create.phone') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="email" class="form-control w-100" name="email" value="{{ request()->old('email') }}" id="email" placeholder="{{ __('web/admin/driver/create.email') }}">
                                <label for="email">{{ __('web/admin/driver/create.email') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" class="form-control w-100" name="password" id="password" placeholder="{{ __('web/admin/driver/create.password') }}" required>
                                <label for="password">{{ __('web/admin/driver/create.password') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" name="password_confirmation" class="form-control w-100" id="password_confirmation" placeholder="{{ __('web/admin/driver/create.password_confirmation') }}" required>
                                <label for="password_confirmation">{{ __('web/admin/driver/create.password_confirmation') }}</label>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-floating">
                                <select class="form-select" id="bank" name="bank" aria-label="{{ __('web/admin/vendor/create.bank') }}" required>
                                    <option selected="selected">{{ __('web/admin/vendor/create.bank') }}</option>
                                    @foreach($banks as $bank)
                                        <option value="{{ $bank->id }}" @if($bank->id===request()->old('bank')) selected @endif>{{ $bank->name }}</option>
                                    @endforeach
                                </select>
                                <label for="bank">{{ __('web/admin/vendor/create.bank') }}</label>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-floating">
                                <input type="text" name="bankRecipientName" value="{{ request()->old('bankRecipientName') }}" class="form-control w-100" id="bankRecipientName" placeholder="{{ __('web/admin/vendor/create.bankRecipientName') }}" required>
                                <label for="bankRecipientName">{{ __('web/admin/vendor/create.bankRecipientName') }}</label>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-4">
                        {{--                        uploads--}}
                        <div class="col-3">
                            <label for="iban">{{ __('web/admin/driver/create.iban') }}</label>
                            <input class="form-control w-100" type="file" id="iban" name="iban" required>
                        </div>
                        <div class="col-3">
                            <label for="img">{{ __('web/admin/driver/create.img') }}</label>
                            <input class="form-control w-100" type="file" id="img" name="img" required>
                        </div>
                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/admin/driver/create.submit') }}</button>
                        </div>
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
