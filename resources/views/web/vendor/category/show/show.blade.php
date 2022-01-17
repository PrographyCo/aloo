@extends('themes.vendor.dashboard')

@section('name', 'category')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.category.index') }}">category</a></li>
                    <li class="breadcrumb-item active">show category || {{ $category->name_en }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <div class="col-12 d-flex">
                        <div class="pt-3 h4 me-auto">{{ $category->name_en }}</div>
                        <a href="{{ route('vendor.category.edit', $category) }}" class="btn btn-secondary">edit</a>
{{--                                        <a href="" class="btn btn-warning">un ban</a>--}}
                    </div>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <h5>name Arabic: </h5>
                            <p>{{ $category->name_ar }}</p>
                        </div>
                        <div class="col-md-12">
                            <h5>description Arabic: </h5>
                            {!! nl2br($category->description_AR) !!}
                        </div>
                    </div>
                    <div class="row g-3 mt-3">
                        <div class="col-md-6">
                            <h5>name English: </h5>
                            <p>{{ $category->name_en }}</p>
                        </div>
                        <div class="col-md-12">
                            <h5>description English: </h5>
                            {!! nl2br($category->description_en) !!}
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </main>

@endsection
