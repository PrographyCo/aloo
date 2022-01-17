@extends('themes.vendor.dashboard')

@section('name', 'offers')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.offer.index') }}">offer</a></li>
                    <li class="breadcrumb-item active">add new offer</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">{{ __('web/vendor/offers/create.main') }}</h5>
                    <form id="storeForm" method="POST" action="{{ route('vendor.offer.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                        <div class="row g-3">
                            <div class="col-12">
                                <h5>{{ __('web/vendor/offers/create.ar_data') }}</h5>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" name="name_ar" class="form-control w-100" id="name_ar" placeholder="{{ __('web/vendor/offers/create.name_ar') }}" required>
                                    <label for="name_ar">{{ __('web/vendor/offers/create.name_ar') }}</label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-floating">
                                    <textarea class="form-control w-100 h-auto" id="description_ar" name="description_ar" placeholder="{{ __('web/vendor/offers/create.description_ar') }}" required rows="3"></textarea>
                                    <label for="description_ar">{{  __('web/vendor/offers/create.description_ar') }}</label>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3 mt-3">
                            <div class="col-12">
                                <h5>{{ __('web/vendor/offers/create.en_data') }}</h5>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" name="name_en" class="form-control w-100" id="name_en" placeholder="{{ __('web/vendor/offers/create.name_en') }}" required>
                                    <label for="name_en">{{ __('web/vendor/offers/create.name_en') }}</label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-floating">
                                    <textarea class="form-control w-100 h-auto" id="description_en" name="description_en" placeholder="{{ __('web/vendor/offers/create.description_en') }}" required rows="3"></textarea>
                                    <label for="description_en">{{  __('web/vendor/offers/create.description_en') }}</label>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3 mt-3">
                            <div class="col-md-3">
                                <div class="form-floating">
                                    <input type="text" name="price" class="form-control w-100" id="price" placeholder="{{ __('web/vendor/offers/create.price') }}" required>
                                    <label for="price">{{ __('web/vendor/offers/create.price') }}</label>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-floating">
                                    <input type="number" name="amount" class="form-control w-100" id="amount" placeholder="{{ __('web/vendor/offers/create.amount') }}" required>
                                    <label for="amount">{{ __('web/vendor/offers/create.amount') }}</label>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-floating">
                                    <select class="form-select" id="category" name="category" aria-label="{{ __('web/vendor/offers/create.category') }}" required>
                                        <option selected="selected">{{ __('web/vendor/offers/create.category') }}</option>
                                        @foreach($categories as $category)
                                            <option value="{{ $category->id }}">{{ $category->name }}</option>
                                        @endforeach
                                    </select>
                                    <label for="category">{{ __('web/vendor/offers/create.category') }}</label>
                                </div>
                            </div>

                            <div class="col-3">
                                <label for="img">{{ __('web/vendor/offers/create.img') }}</label>
                                <input class="form-control w-100" type="file" id="img" name="img" required>
                            </div>


                            <div class="col-md-3">
                                <div class="form-floating">
                                    <select class="form-select" id="category" name="size" aria-label="Size" required>
                                        <option selected="selected">Size</option>
                                        @foreach(__('web/vendor/offers/create.sizes') as $key=>$value)
                                            <option value="{{ $key }}">{{ $value }}</option>
                                        @endforeach
                                    </select>
                                    <label for="category">Size</label>
                                </div>
                            </div>

                            <div class="col-md">
                                <div class="form-group">
                                    <label for="optionals">{{ __('web/vendor/offers/create.with') }}</label>
                                    <select class="form-select" id="optionals" name="with[]" aria-label="{{ __('web/vendor/offers/create.with') }}" multiple>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md">
                                <div class="form-group">
                                    <label for="optionals">{{ __('web/vendor/offers/create.without') }}</label>
                                    <select class="form-select" id="optionals2" name="without[]" aria-label="{{ __('web/vendor/offers/create.without') }}" multiple>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="extras">{{ __('web/vendor/offers/create.drinks') }}</label>
                                    <select class="form-select" id="extras" name="drinks[]" aria-label="{{ __('web/vendor/offers/create.drinks') }}" multiple>
                                        <option value="">{{ __('web/vendor/offers/create.drinks') }}</option>
                                        @foreach($drinks as $drink)
                                            <option value="{{ $drink->id }}">{{ $drink->name }}</option>
                                        @endforeach
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="extras">{{ __('web/vendor/offers/create.extras') }}</label>
                                    <select class="form-select" id="extras" name="extras[]" aria-label="{{ __('web/vendor/offers/create.extras') }}" multiple>
                                        <option value="">{{ __('web/vendor/offers/create.extras') }}</option>
                                        @foreach($extras as $extra)
                                            <option value="{{ $extra->id }}">{{ $extra->name }}</option>
                                        @endforeach
                                    </select>
                                </div>
                            </div>
                            <div class="text-center mt-5">
                                <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/vendor/category/create.submit') }}</button>
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
@push('scripts')
    <script>
        $(document).ready(function () {
            $("#optionals").select2("destroy");
            $("#optionals").select2({
                tags: true,
                dropdownCssClass: "d-none"
            });
            $("#optionals2").select2("destroy");
            $("#optionals2").select2({
                tags: true,
                dropdownCssClass: "d-none"
            });
        })
    </script>
@endpush
