@extends('themes.admin.dashboard')

@section('name', 'vendor')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.vendor.index') }}">vendor</a></li>
                    <li class="breadcrumb-item active">edit vendor || {{ $vendor->brandName }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <div class="row align-content-center my-3">
                        <div class="col-2"><img src="{{ $vendor->logo }}" class="rounded" alt="" style="max-height: 80px" /></div>
                        <h5 class="card-title m-0 py-3 col-8 d-flex align-items-center">edit vendor data ({{ $vendor->brandName }})</h5>
                    </div>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('admin.vendor.update', $vendor) }}"
                          enctype="multipart/form-data">
                        @csrf
                        @method('put')
                        <div class="col-md-3">
                            <div class="form-floating">
                                <select class="form-select" id="city" name="city" aria-label="{{ __('web/admin/vendor/create.city') }}" required>
                                    <option selected="selected">{{ __('web/admin/vendor/create.city') }}</option>
                                    @foreach($cities as $city)
                                        <option value="{{ $city->id }}" @if($vendor->city_id == $city->id) selected @endif>{{ $city->name }}</option>
                                    @endforeach
                                </select>
                                <label for="floatingCity">{{ __('web/admin/vendor/create.city') }}</label>
                            </div>
                        </div>

                        <div class="col-3">
                            <div class="form-floating">
                                <input type="text" name="bankRecipientName" value="{{ $vendor->bankRecipientName }}" class="form-control w-100" id="bankRecipientName" placeholder="{{ __('web/admin/vendor/create.bankRecipientName') }}" required>
                                <label for="bankRecipientName">{{ __('web/admin/vendor/create.bankRecipientName') }}</label>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-floating">
                                <select class="form-select" id="bank" name="bank" aria-label="{{ __('web/admin/vendor/create.bank') }}" required>
                                    <option selected="selected">{{ __('web/admin/vendor/create.bank') }}</option>
                                    @foreach($banks as $bank)
                                        <option value="{{ $bank->id }}" @if($vendor->bank_id == $bank->id) selected @endif>{{ $bank->name }}</option>
                                    @endforeach
                                </select>
                                <label for="bank">{{ __('web/admin/vendor/create.bank') }}</label>
                            </div>
                        </div>

                        <div class="col-3">
                            <div class="form-floating">
                                <input type="text" name="bankIBAN" value="{{ $vendor->bankIBAN }}" class="form-control w-100" id="bankIBAN" placeholder="{{ __('web/admin/vendor/create.bankIBAN') }}" required>
                                <label for="bankIBAN">{{ __('web/admin/vendor/create.bankIBAN') }}</label>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-100" name="description" id="description" placeholder="{{ __('web/admin/vendor/create.description') }}" rows="5" required>{{ $vendor->description }}</textarea>
                                <label for="description">{{ __('web/admin/vendor/create.description') }}</label>
                            </div>
                        </div>
                        <div class="col-3">
                            <label for="logo">{{ __('web/admin/vendor/create.logo') }}</label>
                            <input class="form-control w-100" type="file" id="logo" name="logo">
                        </div>

                        @if(!$vendor->isRestaurant())
                            <div class="col-3">
                                <label for="logo">{{ __('web/admin/vendor/create.image') }}</label>
                                <input class="form-control w-100" type="file" id="image" name="logo">
                            </div>
                        @endif

                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/admin/vendor/create.submit') }}</button>
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
