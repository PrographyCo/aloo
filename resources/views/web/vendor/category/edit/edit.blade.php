@extends('themes.vendor.dashboard')

@section('name', 'category')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.category.index') }}">category</a></li>
                    <li class="breadcrumb-item active">add new category</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">edit category || {{ $category->name_en }}</h5>
                    <form id="storeForm" method="POST" action="{{ route('vendor.category.update', $category) }}">
                        @csrf
                        @method('PUT')
                    <div class="row g-3">
                        <div class="col-12">
                            <h5>{{ __('web/vendor/category/create.ar_data') }}</h5>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_ar" class="form-control w-100" value="{{ $category->name_ar }}" id="name_ar" placeholder="{{ __('web/vendor/category/create.name_ar') }}" required>
                                <label for="name_ar">{{ __('web/vendor/category/create.name_ar') }}</label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-auto" id="description_ar" name="description_ar" placeholder="{{ __('web/vendor/category/create.description_ar') }}" required rows="3">{{ $category->description_ar }}</textarea>
                                <label for="description_ar">{{  __('web/vendor/category/create.description_ar') }}</label>
                            </div>
                        </div>
                    </div>
                    <div class="row g-3 mt-3">
                        <div class="col-12">
                            <h5>{{ __('web/vendor/category/create.en_data') }}</h5>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_en" class="form-control w-100" value="{{ $category->name_en }}" id="name_en" placeholder="{{ __('web/vendor/category/create.name_en') }}" required>
                                <label for="name_en">{{ __('web/vendor/category/create.name_en') }}</label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-floating">
                                <textarea class="form-control w-100 h-auto" id="description_en" name="description_en" placeholder="{{ __('web/vendor/category/create.description_en') }}" required rows="3">{{ $category->description_en }}</textarea>
                                <label for="description_en">{{  __('web/vendor/category/create.description_en') }}</label>
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
