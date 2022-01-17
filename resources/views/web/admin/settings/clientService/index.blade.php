@extends('themes.admin.dashboard')

@section('name', 'Client Service')
@section('main')
    <main id="main" class="main">
        <section class="section dashboard">
            <div class="card p-4">
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
