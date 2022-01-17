@extends('themes.vendor.dashboard')

@section('name', 'category')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">dashboard</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.category.index') }}">category</a></li>
                    <li class="breadcrumb-item active">all categories</li>
                </ol>
            </nav>
        </div><!-- End Page Title -->

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header">
                    <a class="btn btn-primary" href="{{ route('vendor.category.create') }}">add new category</a>
                </div>
                <div class="card-body">
                    {{ $dataTable->table() }}

                    @push('scripts')
                        {{ $dataTable->scripts() }}
                    @endpush
                </div>
            </div>
        </section>

    </main>

@endsection
