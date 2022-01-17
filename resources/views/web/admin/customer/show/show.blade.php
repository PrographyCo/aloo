@extends('themes.admin.dashboard')

@section('name', 'customer || ' . $customer->name)
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.customer.index') }}">customer</a></li>
                    <li class="breadcrumb-item active">view customer || {{ $customer->name }}</li>
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
                                    <img src="{{ $customer->img }}" class="rounded" alt="" style="max-height: 128px" />
                                </div>
                                <div class="col-9 row">
                                    <div class="col-12 d-flex">
                                        <div class="pt-3 h4 me-auto">{{ $customer->name }}</div>
                                        <form action='{{ route('admin.customer.update', $customer) }}' id="customer_ban_form" method="post">
                                        @method('PUT')
                                        @csrf
                                        <button type="button" onclick="confirmForm($('#customer_ban_form'))" class="btn btn-{{ $customer->ban ? 'success' : 'danger' }} mx-2"><i class="bi bi-{{ $customer->ban ? 'unlock' : 'lock' }}-fill"></i></button>
                                        </form>
                                        <form id="delete" action='{{ route('admin.customer.destroy', $customer) }}' method="post">
                                            @method('DELETE')
                                            @csrf
                                            <div style="cursor: pointer" onclick="confirmDelete('{{ route('admin.driver.confirm', $customer) }}')" class="rounded mx-1 btn-danger btn"><i class="ri-delete-bin-fill"></i></div>
                                        </form>
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div class="pt-1">phone: {{ $customer->phone }}</div>
                                        <div class="pt-1">email: {{ $customer->email }}</div>
                                        <div class="pt-1">city: {{ $customer->city->name_en }}</div>
                                        <div class="pt-1">gender: {{ $customer->gender }}</div>
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div class="pt-1">count orders: {{ $customer->orders_count }}</div>
                                        <div class="pt-1">orders sum total price: {{ \WEBHelper::priceToText($customer->orders_sum_total_price) }}</div>
                                        <div class="pt-1">count wallet transaction: {{ $customer->wallet->amount }}</div>
                                        <div class="pt-1">wallet amount: {{ \WEBHelper::priceToText($customer->wallet->amount) }}</div>
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div class="pt-1">count places: {{ $customer->places_count }}</div>
                                        <div class="pt-1">count favorites: {{ $customer->places_count }}</div>
                                        <div class="pt-1">count items in cart: {{ $customer->cart_item_count }}</div>
                                        <div class="pt-1">sum items price in cart: {{ $customer->cart_item_sum_total_price }}</div>
                                        <div class="pt-1">customer rate: {{ $customer->getCustomerRate[0]->rate ?? 'Unknown' }}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12">
                    <div class="card m-0">
                        <div class="card-body p-5">
                            <h5 class="card-title pt-0">customer's order</h5>
                            {{ $dataTable->table() }}
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
        function confirmDelete(){
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this! all",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $('#delete').submit();

                }
            })
        }
    </script>
@endpush
