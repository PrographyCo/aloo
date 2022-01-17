@extends('themes.admin.dashboard')

@section('name', 'vendor')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.vendor.index') }}">vendor</a></li>
                    <li class="breadcrumb-item active">create vendor</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
                <div class="card-body">
                    <h5 class="card-title">{{ __('web/admin/vendor/create.main') }}</h5>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('admin.vendor.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="legalName" class="form-control w-100" id="legalName" value="{{ request()->old('legalName') }}" placeholder="{{ __('web/admin/vendor/create.legalName') }}" required>
                                <label for="legalName">{{ __('web/admin/vendor/create.legalName') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" id="brandName" name="brandName" value="{{ request()->old('brandName') }}" placeholder="{{ __('web/admin/vendor/create.brandName') }}" required>
                                <label for="brandName">{{  __('web/admin/vendor/create.brandName') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" class="form-control w-100" name="password" id="password" placeholder="{{ __('web/admin/vendor/create.password') }}" required>
                                <label for="password">{{ __('web/admin/vendor/create.password') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" name="password_confirmation" class="form-control w-100" id="password_confirmation" placeholder="{{ __('web/admin/vendor/create.password_confirmation') }}" required>
                                <label for="password_confirmation">{{ __('web/admin/vendor/create.password_confirmation') }}</label>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-floating">
                                <input type="number" name="commercialNo" class="form-control w-100" id="commercialNo" value="{{ request()->old('commercialNo') }}" placeholder="{{ __('web/admin/vendor/create.commercialNo') }}" required>
                                <label for="commercialNo">{{ __('web/admin/vendor/create.commercialNo') }}</label>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-floating">
                                <select class="form-select" id="city" name="city" aria-label="{{ __('web/admin/vendor/create.city') }}" required>
                                    <option value="" selected="selected" disabled>{{ __('web/admin/vendor/create.city') }}</option>
                                    @foreach($cities as $city)
                                        <option value="{{ $city->id }}" @if($city->id===request()->old('city')) selected @endif>{{ $city->name }}</option>
                                    @endforeach
                                </select>
                                <label for="floatingCity">{{ __('web/admin/vendor/create.city') }}</label>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-floating">
                                <select class="form-select" name="supported_vendor" id="supported_vendor" aria-label="{{ __('web/admin/vendor/create.supported_vendor') }}" required>
                                    <option value="" selected="selected" disabled>{{ __('web/admin/vendor/create.supported_vendor') }}</option>
                                    @foreach($supportedVendors as $supportedVendor)
                                        <option value="{{ $supportedVendor->id }}" @if($supportedVendor->id===request()->old('supported_vendor')) selected @endif>{{ $supportedVendor->name }}</option>
                                    @endforeach
                                </select>
                                <label for="supported_vendor">{{ __('web/admin/vendor/create.supported_vendor') }}</label>
                            </div>
                        </div>
                        <div id="restaurant_type_div" class="col-3 d-none">
                            <div class="form-floating">
                                <select class="form-select" name="restaurant_type" id="restaurant_type" aria-label="{{ __('web/admin/vendor/create.restaurant_type') }}">
                                    <option value="" selected="selected" disabled>{{ __('web/admin/vendor/create.restaurant_type') }}</option>
                                    @foreach($restaurant_type as $type)
                                        <option value="{{ $type->id }}" @if($type->id===request()->old('restaurant_type')) selected @endif>{{ $type->name }}</option>
                                    @endforeach
                                </select>
                                <label for="supported_vendor">{{ __('web/admin/vendor/create.restaurant_type') }}</label>
                            </div>
                        </div>
                        <div id="kitchen_type_div" class="col-3 d-none">
                            <div class="form-floating">
                                <select class="form-select" name="kitchen_type" id="kitchen_type" aria-label="{{ __('web/admin/vendor/create.kitchen_type') }}">
                                    <option value="" selected="selected" disabled>{{ __('web/admin/vendor/create.kitchen_type') }}</option>
                                    @foreach($kitchen_type as $type)
                                        <option value="{{ $type->id }}" @if($type->id===request()->old('kitchen_type')) selected @endif>{{ $type->name }}</option>
                                    @endforeach
                                </select>
                                <label for="supported_vendor">{{ __('web/admin/vendor/create.kitchen_type') }}</label>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-100" name="description" id="description" placeholder="{{ __('web/admin/vendor/create.description') }}" rows="5" required>{{ request()->old('description') }}</textarea>
                                <label for="description">{{ __('web/admin/vendor/create.description') }}</label>
                            </div>
                        </div>

                        <div class="col-3">
                            <div class="form-floating">
                                <input type="email" name="email" class="form-control w-100" id="email" value="{{ request()->old('email') }}" placeholder="{{ __('web/admin/vendor/create.email') }}">
                                <label for="email">{{ __('web/admin/vendor/create.email') }}</label>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-floating">
                                <input type="text" name="phone" class="form-control w-100" id="phone" value="{{ request()->old('phone') }}" placeholder="{{ __('web/admin/vendor/create.phone') }}" required>
                                <label for="phone">{{ __('web/admin/vendor/create.phone') }}</label>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-floating">
                                <select class="form-select" id="bank" name="bank" aria-label="{{ __('web/admin/vendor/create.bank') }}" required>
                                    <option value="" selected="selected" disabled>{{ __('web/admin/vendor/create.bank') }}</option>
                                    @foreach($banks as $bank)
                                        <option value="{{ $bank->id }}">{{ $bank->name }}</option>
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
                        <div class="col-3">
                            <div class="form-floating">
                                <input type="text" name="bankIBAN" value="{{ request()->old('bankIBAN') }}" class="form-control w-100" id="bankIBAN" placeholder="{{ __('web/admin/vendor/create.bankIBAN') }}" required>
                                <label for="bankIBAN">{{ __('web/admin/vendor/create.bankIBAN') }}</label>
                            </div>
                        </div>
                        <div class="col-9"></div>
                        <hr>

{{--                        uploads--}}
                        <div class="col-3">
                            <label for="commercialRecord">{{ __('web/admin/vendor/create.commercialRecord') }}</label>
                            <input class="form-control w-100" type="file" id="commercialRecord" name="commercialRecord" required>
                        </div>
                        <div class="col-3">
                            <label for="logo">{{ __('web/admin/vendor/create.logo') }}</label>
                            <input class="form-control w-100" type="file" id="logo" name="logo" required>
                        </div>
                        <div class="col-3">
                            <label for="logo">{{ __('web/admin/vendor/create.image') }}</label>
                            <input class="form-control w-100" type="file" id="image" name="image" required>
                        </div>
                        <div class="col-3">
                            <label for="speech">{{ __('web/admin/vendor/create.speech') }}</label>
                            <input class="form-control w-100" type="file" id="speech" name="speech" required>
                        </div>


                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/admin/vendor/create.submit') }}</button>
                        </div>
                    </form>

                </div>
            </div>
        </section>

    </main>

@endsection


@push('scripts')
    <script>
        $('#supported_vendor').on('change', function (){
            let RT = $('#restaurant_type_div'),
                KT = $('#kitchen_type_div');

            if(this.value == 3){
                RT.removeClass('d-none');
                RT.prop('required',true);
                KT.removeClass('d-none');
                KT.prop('required',true);
            } else {
                RT.addClass('d-none');
                RT.prop('required',false);
                KT.addClass('d-none');
                KT.prop('required',false);
            }
        });
    </script>
@endpush
