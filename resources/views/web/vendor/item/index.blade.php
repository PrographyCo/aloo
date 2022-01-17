@extends('themes.vendor.dashboard')

@section('name', 'item')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.item.index') }}">item</a></li>
                    <li class="breadcrumb-item active">all items</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header">
                    <a class="btn btn-primary" href="{{ route('vendor.item.create') }}">add new item</a>
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
