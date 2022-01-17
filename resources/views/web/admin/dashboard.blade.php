@extends('themes.admin.dashboard')

@section('name', 'dashboard')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Dashboard</a></li>
                </ol>
            </nav>
        </div><!-- End Page Title -->

        <section class="section dashboard">
            <div class="row">
                <div class="col-12">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card info-card sales-card">
                                <div class="card-body">
                                    <h5 class="card-title">Orders <span>| Today</span></h5>
                                    <div class="d-flex align-items-center">
                                        <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                            <i class="bi bi-cart"></i>
                                        </div>
                                        <h6 class="ps-3">{{ $countTodayOrders }}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card info-card revenue-card">
                                <div class="card-body">
                                    <h5 class="card-title">total Orders Price <span>| Today</span></h5>
                                    <div class="d-flex align-items-center">
                                        <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                            <i class="bi bi-currency-dollar"></i>
                                        </div>
                                        <h6 class="ps-3">{{ $sumTodayOrdersPrice }} SAR</h6>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">

                            <div class="card info-card customers-card">
                                <div class="card-body">
                                    <h5 class="card-title">Total New Customers <span>| Today</span></h5>
                                    <div class="d-flex align-items-center">
                                        <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                            <i class="bi bi-people"></i>
                                        </div>
                                       <h6 class="ps-3">{{ $countTodayCustomer }}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-12">
                            <div class="card recent-sales">
                                <div class="card-body">
                                    <h5 class="card-title">last 10 Orders</h5>

                                    <table class="table table-borderless datatable">
                                        <thead>
                                        <tr>
                                            <th scope="col"># (order number)</th>
                                            <th scope="col">Customer</th>
                                            <th scope="col">Vendor</th>
                                            <th scope="col">Branch</th>
                                            <th scope="col">Total Price</th>
                                            <th scope="col">Status</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        @foreach($last10Orders as $order)
                                            <tr>
                                                <th scope="row"><a href="#">#{{ $order->id }}</a></th>
                                                <td>{{ $order->customer->name }}</td>
                                                <td><a class="ql-color-blue" href="{{ route('admin.vendor.show', $order->branch->vendor) }}">{{ $order->branch->vendor->brandName }}</a></td>
                                                <td>{{ $order->branch->name }}</td>
                                                <td>{!! \WEBHelper::priceToText($order->total_price) !!}</td>
                                                <td>{!! \WEBHelper::textToSpan($order->status) !!}</td>
                                            </tr>
                                        @endforeach
                                        </tbody>
                                    </table>

                                </div>

                            </div>
                        </div><!-- End Recent Sales -->

                        <!-- Top Selling -->
                        <div class="col-12">
                            <div class="card top-selling">
                                <div class="card-body pb-0">
                                    <h5 class="card-title">Top Selling Products</h5>

                                    <table class="table table-borderless datatable">
                                        <thead>
                                        <tr>
                                            <th scope="col">Image</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Total Amount</th>
                                            <th scope="col">Vendor</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        @foreach($topItems as $item)
                                            <tr>
                                            <th scope="row"><img src="{{ $item->img }}" alt=""></th>
                                            <td>{{ $item->{'name_'.'en'} }}</td>
                                            <td>{{ $item->price }}</td>
                                            <td class="fw-bold">{{ $item->order_item_sum_amount }}</td>
                                            <td><a class="ql-color-blue" href="{{ route('admin.vendor.show', $item->vendor) }}">{{ $item->vendor->brandName }}</a></td>
                                        </tr>
                                        @endforeach
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
@endsection
