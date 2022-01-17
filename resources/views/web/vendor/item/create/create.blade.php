@extends('themes.vendor.dashboard')

@section('name', 'item')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.item.index') }}">item</a></li>
                    <li class="breadcrumb-item active">add new item</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">{{ __('web/vendor/item/create.main') }}</h5>
                    <form id="storeForm" method="POST" action="{{ route('vendor.item.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                    <div class="row g-3">
                        <div class="col-12">
                            <h5>{{ __('web/vendor/item/create.ar_data') }}</h5>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_ar" class="form-control w-100" id="name_ar" placeholder="{{ __('web/vendor/item/create.name_ar') }}" required>
                                <label for="name_ar">{{ __('web/vendor/item/create.name_ar') }}</label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-auto" id="brief_desc_ar" name="brief_desc_ar" placeholder="{{ __('web/vendor/item/create.brief_desc_ar') }}" required rows="3"></textarea>
                                <label for="brief_desc_ar">{{  __('web/vendor/item/create.brief_desc_ar') }}</label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-auto" id="description_ar" name="description_ar" placeholder="{{ __('web/vendor/item/create.description_ar') }}" required rows="3"></textarea>
                                <label for="description_ar">{{  __('web/vendor/item/create.description_ar') }}</label>
                            </div>
                        </div>
                    </div>
                    <div class="row g-3 mt-3">
                        <div class="col-12">
                            <h5>{{ __('web/vendor/item/create.en_data') }}</h5>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_en" class="form-control w-100" id="name_en" placeholder="{{ __('web/vendor/item/create.name_en') }}" required>
                                <label for="name_en">{{ __('web/vendor/item/create.name_en') }}</label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-auto" id="brief_desc_en" name="brief_desc_en" placeholder="{{ __('web/vendor/item/create.brief_desc_en') }}" required rows="3"></textarea>
                                <label for="brief_desc_en">{{  __('web/vendor/item/create.brief_desc_en') }}</label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-auto" id="description_en" name="description_en" placeholder="{{ __('web/vendor/item/create.description_en') }}" required rows="3"></textarea>
                                <label for="description_en">{{  __('web/vendor/item/create.description_en') }}</label>
                            </div>
                        </div>
                    </div>
                    <div class="row g-3 mt-3">
                        <div class="col-md-3">
                            <div class="form-floating">
                                <input type="text" name="price" class="form-control w-100" id="price" placeholder="{{ __('web/vendor/item/create.price') }}" required>
                                <label for="price">{{ __('web/vendor/item/create.price') }}</label>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-floating">
                                <input type="number" name="amount" class="form-control w-100" id="amount" placeholder="{{ __('web/vendor/item/create.amount') }}" required>
                                <label for="amount">{{ __('web/vendor/item/create.amount') }}</label>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-floating">
                                <select class="form-select" id="amount_type" name="amount_type" aria-label="{{ __('web/vendor/item/create.amount_type') }}" required>
                                    <option selected="selected">{{ __('web/vendor/item/create.amount_type') }}</option>
                                    <option value="piece">piece</option>
                                    <option value="kgm">kgm</option>
                                    <option value="letter">letter</option>
{{--                                    <option value="calories">calories</option>--}}
                                </select>
                                <label for="amount_type">{{ __('web/vendor/item/create.amount_type') }}</label>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-floating">
                                <select class="form-select" id="category" name="category" aria-label="{{ __('web/vendor/item/create.category') }}" required>
                                    <option selected="selected">{{ __('web/vendor/item/create.category') }}</option>
                                    @foreach($categories as $category)
                                        <option value="{{ $category->id }}">{{ $category->name }}</option>
                                    @endforeach
                                </select>
                                <label for="category">{{ __('web/vendor/item/create.category') }}</label>
                            </div>
                        </div>

                        <div class="col-3">
                            <label for="img">{{ __('web/vendor/item/create.img') }}</label>
                            <input class="form-control w-100" type="file" id="img" name="img" required>
                        </div>
                        <div class="col-3">
                            <label for="images">{{ __('web/vendor/item/create.images') }}</label>
                            <input class="form-control w-100" type="file" id="images" name="images[]" required multiple="">
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
