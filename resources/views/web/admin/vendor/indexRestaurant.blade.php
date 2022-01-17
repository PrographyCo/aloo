@extends('themes.admin.dashboard')

@section('name', 'restaurant vendor')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.vendor.index') }}">vendor</a></li>
                    <li class="breadcrumb-item active">all restaurant vendors</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header">
                    <a class="rounded btn btn-primary" href="{{ route('admin.vendor.create') }}">add new vendor</a>
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
