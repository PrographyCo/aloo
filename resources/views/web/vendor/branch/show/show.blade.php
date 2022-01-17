@extends('themes.vendor.dashboard')

@section('name', 'branch')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.branch.index') }}">branch</a></li>
                    <li class="breadcrumb-item active">view branch || {{ $branch->name }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="row">
                <div class="col-12">
                    <div class="card p-2">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-12 d-flex">
                                    <div class="pt-3 h4 me-auto">{{ $branch->name }}</div>
                                    <a href="{{ route('vendor.branch.edit', $branch) }}" class="btn btn-secondary">edit</a>
{{--                                        <a href="" class="btn btn-warning">un ban</a>--}}
                                </div>
                                <div class="col-4 d-flex flex-column justify-content-center">
                                    <div>name: {{ $branch->name }}</div>
                                    <div>username: {{ $branch->login_number }}</div>
                                    <div>city: {{ $branch->city->name_en }}</div>
                                </div>
                                <div class="col-4 d-flex flex-column justify-content-center">
                                    <div>manager name: {{ $branch->manager }}</div>
                                    <div>manager email: {{ $branch->managerEmail }}</div>
                                    <div>manager phone: {{ $branch->managerPhone }}</div>
                                    <div>manager position: {{ $branch->managerPosition }}</div>
                                </div>
{{--                                <div class="col-2">--}}
{{--                                    <div class="card info-card sales-card p-0">--}}
{{--                                        <div class="card-body p-3">--}}
{{--                                            <div class="d-flex align-items-center">--}}
{{--                                                <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">--}}
{{--                                                    <i class="bx bxs-cabinet"></i>--}}
{{--                                                </div>--}}
{{--                                                <h6 class="ml-3">{{ 1 }}</h6>--}}
{{--                                            </div>--}}
{{--                                        </div>--}}
{{--                                    </div>--}}
{{--                                </div>--}}
{{--                                <div class="col-2">--}}
{{--                                    <div class="card info-card sales-card p-0">--}}
{{--                                        <div class="card-body p-3">--}}
{{--                                            <div class="d-flex align-items-center">--}}
{{--                                                <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">--}}
{{--                                                    <i class="ri-store-2-fill"></i>--}}
{{--                                                </div>--}}
{{--                                                <h6 class="ml-3">{{ 1 }}</h6>--}}
{{--                                            </div>--}}
{{--                                        </div>--}}
{{--                                    </div>--}}
{{--                                </div>--}}
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12">
                    <div class="card m-0">
                        <div class="card-body p-5">
                            <h5 class="card-title pt-0">branch item</h5>
                            {{ $dataTable->table(['id' => 'item-table'], true) }}
                        </div>
                    </div>
                </div>
            </div>

        </section>

    </main>

    <div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="mr-auto">Bootstrap</strong>
            <small class="text-muted">11 mins ago</small>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            Hello, world! This is a toast message.
        </div>
    </div>
@endsection


@push('scripts')

    {{ $dataTable->scripts() }}
    <script>
        var branch_id = {{ $branch->id }};
    </script>
    @if($branch->vendor->isRestaurant())
        <script src="{{ asset('js/vendor/branch.itemRestaurant.js') }}"></script>
    @else
        <script src="{{ asset('js/vendor/branch.item.js') }}"></script>
    @endif
@endpush
