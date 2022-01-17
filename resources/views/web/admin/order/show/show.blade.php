@extends('themes.admin.dashboard')

@section('name', 'order')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.order.index') }}">order</a></li>
                    <li class="breadcrumb-item active">view order || #{{ $order->id }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="row">
                <div class="col-12">
                    <div class="card p-2">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-4 d-flex flex-column align-items-center">
                                    <h6 class="card-title">vendor data</h6>
                                    <img src="{{ $order->branch->vendor->logo }}" class="rounded" alt="" style="max-height: 64px" />
                                    <div class="pt-3">brand Name :{{ $order->branch->vendor->brandName }}</div>
                                    <div class="pt-3">Legal Name: {{ $order->branch->vendor->legalName }}</div>
                                    <div class="pt-3">phone: {{ $order->branch->vendor->phone }}</div>
                                    <div class="pt-3">branch name: {{ $order->branch->name }}</div>
                                    <div class="pt-3">branch city: {{ $order->branch->city->{'name_en'} }}</div>
                                    <div class="pt-3 h5">{{ $order->branch->vendor->supported_vendor->{'name_en'} }}</div>
                                </div>
                                <div class="col-4 d-flex flex-column align-items-center">
                                    <h6 class="card-title">customer data</h6>
                                    <img src="{{ $order->customer->img }}" class="rounded" alt="" style="max-height: 64px" />
                                    <div class="pt-3">phone :{{ $order->customer->phone }}</div>
                                    <div class="pt-3">email :{{ $order->customer->email }}</div>
                                    <div class="pt-3">gender: {{ $order->customer->gender }}</div>
                                    <div class="pt-3">city: {{ $order->customer->city->{'name_en'} }}</div>
                                </div>
                                <div class="col-4 d-flex flex-column align-items-center">
                                    <h6 class="card-title">order data</h6>
                                    <div class="pt-3">total amount :{{ $order->items_sum_amount }}</div>
                                    <div class="pt-3">items count: {{ $order->items_count }}</div>
                                    <div class="pt-3">position lon/lat: {{ $order->place->lon . $order->place->lat }}</div>
                                    <div class="pt-3">order time: {{ $order->created_at }}</div>
                                    <div class="pt-3">delivery time: {{ $order->deliver_time ?? 'Non' }}</div>
                                    <div id="order_status" class="pt-3">status: {!! \WEBHelper::textToSpan($order->status) !!}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
{{--                <div class="col-12">--}}
{{--                    <div class="card">--}}
{{--                        <div class="card-body p-5">--}}
{{--                            <h5 class="card-title pt-0">order tracking</h5>--}}
{{--                            <div>--}}
{{--                    --}}
{{--                            </div>--}}
{{--                        </div>--}}
{{--                    </div>--}}
{{--                </div>--}}
                <div class="col-12">
                    <div class="card m-0">
                        <div class="card-body p-5">
                            <h5 class="card-title pt-0">order items</h5>
                                <table class="table table-borderless">
                                    <thead>
                                    <tr>
                                        <th scope="col">Image</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Price</th>
                                        <th scope="col">order Amount</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    @foreach($order->items as $item)
                                        <tr>
                                            <th scope="row"><img src="{{ $item->item->img }}" alt="" class="img-thumbnail" style="max-height: 128px"></th>
                                            <td>{{ $item->item->{'name_'.'en'} }}</td>
                                            <td>{{ \WEBHelper::priceToText($item->item->price) }}</td>
                                            <td class="fw-bold">{{ $item->amount }}</td>
                                        </tr>
                                    @endforeach
                                    </tbody>
                                </table>
                        </div>
                    </div>
                </div>
            </div>

        </section>

    </main>

@endsection

@push('scripts')
    <script type="module">
        import { initializeApp } from "https://www.gstatic.com/firebasejs/9.5.0/firebase-app.js";
        import {  getDatabase, ref, onValue } from "https://www.gstatic.com/firebasejs/9.5.0/firebase-database.js";

        const firebaseConfig = {
            apiKey: "AIzaSyCnTzMbDG1J7v0YnfYHAkJLpioXQCCHWBk",
            authDomain: "aloo-5d9ab.firebaseapp.com",
            databaseURL: "https://aloo-5d9ab-default-rtdb.firebaseio.com",
            projectId: "aloo-5d9ab",
            storageBucket: "aloo-5d9ab.appspot.com",
            messagingSenderId: "581669989615",
            appId: "1:581669989615:web:92ec6e6218cc3d7aa27f1a",
            measurementId: "G-N7XLGH8DKE"
        };

        // Initialize Firebase
        const app = initializeApp(firebaseConfig);
        const db = getDatabase(app);
        const status = @json(\App\Models\Order::STATUS);
        const starCountRef = ref(db, 'orders/{{ $order->id }}/');
        onValue(starCountRef, (snapshot) => {
            const data = snapshot.val();
            var span = "<span class='badge bg-secondary'>" + (status[data.status]).replaceAll('-', ' ') + "</span>";
            $('#order_status').html('status: ' + span);
            const swal = Swal.mixin({
                toast: true,
                position: 'top-end',
                icon: 'success',
                title: 'order changed to ' + status[data.status],
                showConfirmButton: false,
                timer: 1500
            });
            swal.fire();
        });
    </script>

@endpush
