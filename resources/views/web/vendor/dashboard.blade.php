@extends('themes.vendor.dashboard')

@section('name', 'dashboard')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Dashboard</a></li>
                </ol>
            </nav>
        </div>


        <section class="section dashboard">
            <div class="row">
                <div class="col-12">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card info-card sales-card">
                                <div class="card-body">
                                    <h5 class="card-title">Orders <span>| Month</span></h5>
                                    <div class="d-flex align-items-center">
                                        <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                            <i class="bi bi-cart"></i>
                                        </div>
                                        <h6 class="ps-3">{{ $countMonthOrders }}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card info-card revenue-card">
                                <div class="card-body">
                                    <h5 class="card-title">total Orders Price <span>| Month</span></h5>
                                    <div class="d-flex align-items-center">
                                        <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                            <i class="bi bi-currency-dollar"></i>
                                        </div>
                                        <h6 class="ps-3">{{ $sumMonthOrdersPrice }} SAR</h6>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">

                            <div class="card info-card customers-card">
                                <div class="card-body">
                                    <h5 class="card-title">Total Item <span>| Today</span></h5>
                                    <div class="d-flex align-items-center">
                                        <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                            <i class="bx bxs-grid"></i>
                                        </div>
                                        <h6 class="ps-3">{{ $countItem }}</h6>
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
                                            <th scope="col">branch</th>
                                            <th scope="col">Total Price</th>
                                            <th scope="col">Status</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        @foreach($last10Orders as $order)
                                            <tr>
                                                <th scope="row"><a href="#">#{{ $order->id }}</a></th>
                                                <td>{{ $order->customer->name }}</td>
                                                <td><a class="ql-color-blue" href="{{ route('vendor.branch.show', $order->branch) }}" class="text-primary">{{ $order->branch->name }}</a></td>
                                                <td>{!! \WEBHelper::priceToText($order->total_price) !!}</td>
                                                <td>{!! \WEBHelper::textToSpan($order->status) !!}</td>
                                            </tr>
                                        @endforeach
                                        </tbody>
                                    </table>

                                </div>

                            </div>
                        </div>

                        <div class="col-12">
                            <div class="card top-selling">
                                <div class="card-body pb-0">
                                    <h5 class="card-title">Top Selling Products</h5>

                                    <table class="table table-borderless">
                                        <thead>
                                        <tr>
                                            <th scope="col">Image</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Total Amount</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        @foreach($topItems as $item)
                                            <tr>
                                                <th scope="row"><a href="{{ route('vendor.item.show', $item) }}"><img src="{{ $item->img }}" alt=""></a></th>
                                                <td>{{ $item->{'name_'.'en'} }}</td>
                                                <td>{{ $item->price }}</td>
                                                <td class="fw-bold">{{ $item->order_item_sum_amount }}</td>
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
