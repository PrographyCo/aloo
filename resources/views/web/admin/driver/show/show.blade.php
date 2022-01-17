@extends('themes.admin.dashboard')

@section('name', 'driver || ' . $driver->name)
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.driver.index') }}">driver</a></li>
                    <li class="breadcrumb-item active">view driver || {{ $driver->name }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="row">
                <div class="col-12">
                    <div class="card p-2">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-3 d-flex flex-column align-items-center justify-content-center">
                                    <img src="{{ $driver->img }}" class="rounded" alt="" style="max-height: 128px" />
                                </div>
                                <div class="col-9 row">
                                    <div class="col-12 d-flex">
                                        <div class="pt-3 h4 me-auto">{{ $driver->name }}</div>
                                        @if($driver->confirm)
                                        <form action='{{ route('admin.driver.ban', $driver) }}' id="driver_ban_form" method="post">
                                            @method('PUT')
                                            @csrf
                                            <button type="button" onclick="confirmForm($('#driver_ban_form'))" class="btn btn-{{ $driver->ban ? 'success' : 'danger' }} mx-2"><i class="bi bi-{{ $driver->ban ? 'unlock' : 'lock' }}-fill"></i></button>
                                        </form>
                                        <a href="{{ route('admin.driver.edit', $driver) }}" class="btn btn-secondary">edit</a>
                                        @else
                                            <div style="cursor: pointer" onclick="confirmDriver('{{ route('admin.driver.confirm', $driver) }}')" class="rounded mx-1 btn-success btn"><i class="bx bx-check"></i></div>
                                        @endif
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div class="pt-1">phone: {{ $driver->phone }}</div>
                                        <div class="pt-1">email: {{ $driver->email }}</div>
{{--                                        <div class="pt-1">bank number: {{ $driver->bankNumber }}</div>--}}
                                        <a class="btn-sm btn-secondary" href="{{ $driver->iban }}"><i class="ri-link-m"></i> i ban</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12">
                    <div class="card m-0">
                        <div class="card-body p-5">
                            <h5 class="card-title pt-0">all cars for driver</h5>
                            {{ $dataTable->table(['id' => 'cars-table'], true) }}
                        </div>
                    </div>
                </div>
            </div>

        </section>

    </main>

@endsection


@push('scripts')
{{ $dataTable->scripts() }}
<script>
    function confirmDriver(url){
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, confirm!'
        }).then((result) => {
            if (result.isConfirmed) {
                location.assign(url);
            }
        })
    }
</script>
@endpush
