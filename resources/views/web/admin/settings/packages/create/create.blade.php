@extends('themes.admin.dashboard')

@section('name', 'Package')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.settings.packages.index') }}">Package</a></li>
                    <li class="breadcrumb-item active">create Package</li>
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
                    <h5 class="card-title">Create Package</h5>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('admin.settings.packages.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_en" class="form-control w-100" id="name_en" placeholder="{{ __('web/admin/datatable.bank.name_en') }}" required>
                                <label for="name_en">{{ __('web/admin/datatable.bank.name_en') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_ar" class="form-control w-100" id="name_ar" placeholder="{{ __('web/admin/datatable.bank.name_ar') }}" required>
                                <label for="name_ar">{{ __('web/admin/datatable.bank.name_ar') }}</label>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-floating">
                                <select class="form-select" id="type" name="type" aria-label="{{ __('web/admin/datatable.package.type')  }}" required>
                                    <option>{{ __('web/admin/datatable.package.type') }}</option>
                                    @foreach(__('web/admin/datatable.package.types') as $key=>$value)
                                        <option value="{{ $key }}">{{ $value }}</option>
                                    @endforeach
                                </select>
                                <label for="type">{{ __('web/admin/datatable.package.type') }}</label>
                            </div>
                        </div>

                        <div class="col-3">
                            <div class="form-floating">
                                <input type="number" name="price" class="form-control w-100" id="price" placeholder="{{ __('web/admin/datatable.package.price') }}" required>
                                <label for="price">{{ __('web/admin/datatable.package.price') }}</label>
                            </div>
                        </div>

                        <div class="col-3">
                            <div class="form-floating">
                                <input type="number" name="days" class="form-control w-100" id="days" placeholder="{{ __('web/admin/datatable.package.days') }}" required>
                                <label for="days">{{ __('web/admin/datatable.package.days') }}</label>
                            </div>
                        </div>

                        <div class="col-3 d-none">
                            <div class="form-floating">
                                <input type="number" name="orders" class="form-control w-100 fd" id="orders" placeholder="{{ __('web/admin/datatable.package.orders') }}">
                                <label for="orders">{{ __('web/admin/datatable.package.orders') }}</label>
                            </div>
                        </div>

                        <div class="col-3 d-none">
                            <div class="form-floating">
                                <input type="number" name="discount" class="form-control w-100 di" id="discount" placeholder="{{ __('web/admin/datatable.package.discount') }}">
                                <label for="discount">{{ __('web/admin/datatable.package.discount') }}</label>
                            </div>
                        </div>
                        <div class="col-md d-none">
                            <div class="form-floating">
                                <select class="form-select di2" id="discount_type" name="discount_type" aria-label="{{ __('web/admin/datatable.package.dType') }}">
                                    <option value="" selected="selected" disabled>{{ __('web/admin/datatable.package.dType') }}</option>
                                    @foreach(__('web/admin/datatable.package.dTypes') as $key=>$value)
                                        <option value="{{ $key }}">{{ $value }}</option>
                                    @endforeach
                                </select>
                                <label for="discount_type">{{ __('web/admin/datatable.package.dType') }}</label>
                            </div>
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
        $('#type').on('change', function (){
            let FD = $('.fd'),
                DI = $('.di'),
                DI2 = $('.di2');

            if(this.value == 'freeDelivery'){
                DI.parents()[1].classList.add('d-none');
                DI.prop('required',false);
                DI2.parents()[1].classList.add('d-none');
                DI2.prop('required',false);

                FD.parents()[1].classList.remove('d-none');
                FD.prop('required',true);
            } else {
                DI.parents()[1].classList.remove('d-none');
                DI.prop('required',true);
                DI2.parents()[1].classList.remove('d-none');
                DI2.prop('required',true);

                FD.parents()[1].classList.add('d-none');
                FD.prop('required',false);
            }
        });
    </script>
@endpush
