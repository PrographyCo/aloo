@extends('themes.vendor.dashboard')

@section('name', 'extra')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.extra.index') }}">extra</a></li>
                    <li class="breadcrumb-item active">edit extra || {{ $extra->name_en }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">edit extra || {{ $extra->name_en }}</h5>
                    <form id="storeForm" method="POST" action="{{ route('vendor.extra.update', $extra) }}"
                          enctype="multipart/form-data">
                        @csrf
                        @method('PUT')
                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_ar" class="form-control w-100" value="{{ $extra->name_ar }}" id="name_ar" placeholder="{{ __('web/vendor/drink/create.name_ar') }}" required>
                                <label for="name_ar">{{ __('web/vendor/extra/create.name_ar') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="name_en" class="form-control w-100" value="{{ $extra->name_en }}" id="name_en" placeholder="{{ __('web/vendor/drink/create.name_en') }}" required>
                                <label for="name_en">{{ __('web/vendor/extra/create.name_en') }}</label>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-floating">
                                <input type="number" name="price" class="form-control w-100" value="{{ $extra->price }}" id="price" placeholder="{{ __('web/vendor/drink/create.price') }}" required>
                                <label for="price">{{ __('web/vendor/extra/create.price') }}</label>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-floating">
                                <input type="number" name="calories" class="form-control w-100" value="{{ $extra->calories }}" id="calories" placeholder="{{ __('web/vendor/drink/create.calories') }}" required>
                                <label for="calories">{{ __('web/vendor/extra/create.calories') }}</label>
                            </div>
                        </div>
                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/vendor/extra/create.submit') }}</button>
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
