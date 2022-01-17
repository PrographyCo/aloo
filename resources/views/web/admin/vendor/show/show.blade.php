@extends('themes.admin.dashboard')

@section('name', 'vendor')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.vendor.index') }}">vendor</a></li>
                    <li class="breadcrumb-item active">view vendor || {{ $vendor->brandName }}</li>
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
                                    <img src="{{ $vendor->logo }}" class="rounded" alt="" style="max-height: 128px" />
                                </div>
                                <div class="col-9 row">
                                    <div class="col-12 d-flex align-items-center">
                                        <div class="pt-3 h4 me-auto">{{ $vendor->brandName }}</div>
                                        @if($vendor->confirm)
                                        <form action='{{ route('admin.vendor.ban', $vendor) }}' id="vendor_ban_form" method="post">
                                            @csrf
                                            <button type="button" onclick="confirmForm($('#vendor_ban_form'))" class="btn btn-{{ $vendor->ban ? 'success' : 'danger' }} mx-2"><i class="bi bi-{{ $vendor->ban ? 'unlock' : 'lock' }}-fill"></i></button>
                                        </form>
                                        <a href="{{ route('admin.vendor.edit', $vendor) }}" class="btn btn-secondary">edit</a>
                                        @else
                                            <div style="cursor: pointer;" onclick="confirmVendor('{{ route('admin.vendor.confirm', $vendor) }}')" class="rounded mx-1"><i class="bx bx-check"></i></div>
                                        @endif
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div>Legal Name: {{ $vendor->legalName }}</div>
                                        <div class="pt-1">Commercial No. : {{ $vendor->commercialNo }}</div>
                                        <div class="pt-1">Official Phone: {{ $vendor->phone }}</div>
                                        <div class="pt-1">Official Email: {{ $vendor->email }}</div>
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div class="h5">{{ $vendor->supported_vendor->{'name_en'} }}</div>
                                        @if($vendor->isRestaurant())
                                            <div class="pt-2">restaurant type : {{ $vendor->restaurantType[0]->name_en ?? 'unknown' }}</div>
                                            <div class="pt-2">kitchen type : {{ $vendor->kitchenType[0]->name_en ?? 'unknown' }}</div>
                                        @endif
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div>city: {{ $vendor->city->name_en }}</div>
                                        <div class="pt-2">bank: {{ $vendor->bank->name_en }}</div>
                                        <div class="pt-2">bank recipient name: {{ $vendor->bankRecipientName }}</div>
                                        <div class="pt-2 d-inline">
                                            <a class="btn-sm btn-secondary" href="{{ $vendor->speech }}"><i class="ri-link-m"></i> speech</a>
                                            <a class="btn-sm btn-secondary" href="{{ $vendor->commercialRecord }}"><i class="ri-link-m"></i> commercial record</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <hr class="my-4" />
                                    <div class="col-8">
                                        <h4>description</h4>
                                        <p>{!! nl2br($vendor->description) !!}</p>
                                    </div>
                                    <div class="col-2">
                                        <div class="card info-card sales-card p-0">
                                            <div class="card-body p-3">
                                                <div class="d-flex align-items-center">
                                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                                        <i class="bx bxs-cabinet"></i>
                                                    </div>
                                                    <h6 class="ml-3">{{ $vendor->items_count }}</h6>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-2">
                                        <div class="card info-card sales-card p-0">
                                            <div class="card-body p-3">
                                                <div class="d-flex align-items-center">
                                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                                        <i class="ri-store-2-fill"></i>
                                                    </div>
                                                    <h6 class="ml-3">{{ $vendor->branches_count }}</h6>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12">
                    <div class="card m-0">
                        <div class="card-body p-5">
                            <h5 class="card-title pt-0">other data about the vendor</h5>
                            <ul class="nav nav-tabs" id="vendorTable" role="tablist">
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link active" id="branches-tab" data-bs-toggle="tab" data-bs-target="#branches" type="button" role="tab" aria-controls="branches" aria-selected="true">Branches</button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="items-tab" data-bs-toggle="tab" data-bs-target="#items" type="button" role="tab" aria-controls="items" aria-selected="false">Items</button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="categories-tab" data-bs-toggle="tab" data-bs-target="#categories" type="button" role="tab" aria-controls="categories" aria-selected="false">Categories</button>
                                </li>
                                @if($vendor->isRestaurant())
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link" id="drinks-tab" data-bs-toggle="tab" data-bs-target="#drinks" type="button" role="tab" aria-controls="drinks" aria-selected="false">Drinks</button>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link" id="extras-tab" data-bs-toggle="tab" data-bs-target="#extras" type="button" role="tab" aria-controls="extras" aria-selected="false">Extras</button>
                                    </li>
                                @endif
                            </ul>
                            <div class="tab-content pt-2" id="myTabContent">
                                <div class="tab-pane fade show active" id="branches" role="tabpanel" aria-labelledby="branches-tab">
                                    {{ $branchesTable->table(['id' => 'branch-table'], true) }}
                                </div>
                                <div class="tab-pane fade" id="items" role="tabpanel" aria-labelledby="items-tab">
                                    {{ $itemsTable->table(['id' => 'item-table'], true) }}
                                </div>
                                <div class="tab-pane fade" id="categories" role="tabpanel" aria-labelledby="categories-tab">
                                    {{ $categoryTable->table(['id' => 'category-table'], true) }}
                                </div>
                                @if($vendor->isRestaurant())
                                    <div class="tab-pane fade" id="drinks" role="tabpanel" aria-labelledby="drinks-tab">
                                        {{ $drinkTable->table(['id' => 'drink-table'], true) }}
                                    </div>
                                    <div class="tab-pane fade" id="extras" role="tabpanel" aria-labelledby="extras-tab">
                                        {{ $extraTable->table(['id' => 'extra-table'], true) }}
                                    </div>
                                @endif
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </section>

    </main>

@endsection


@push('scripts')
    {{ $branchesTable->scripts() }}
    {{ $categoryTable->scripts() }}
    {{ $itemsTable->scripts() }}
    @if($vendor->isRestaurant())
        {{ $extraTable->scripts() }}
        {{ $drinkTable->scripts() }}
    @endif
    <script>
        function confirmVendor(url){
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
