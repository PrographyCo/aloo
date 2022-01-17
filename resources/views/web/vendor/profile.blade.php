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
            <div class="card p-4">
                <div class="card-body col-12">
                    <div class="row">
                        <div class="col-3 d-flex flex-column align-items-center justify-content-center">
                            <img src="{{ $user->logo }}" class="rounded" alt="" style="max-height: 128px" />
                        </div>
                        <div class="col-9 row">
                            <div class="col-12 d-flex align-items-center">
                                <div class="pt-3 h4 me-auto">{{ $user->brandName }}</div>
                            </div>
                            <div class="col-4 d-flex flex-column justify-content-center">
                                <div>Legal Name: {{ $user->legalName }}</div>
                                <div class="pt-1">Commercial No. : {{ $user->commercialNo }}</div>
                                <div class="pt-1">Official Phone: {{ $user->phone }}</div>
                                <div class="pt-1">Official Email: {{ $user->email }}</div>
                            </div>
                            <div class="col-4 d-flex flex-column justify-content-center">
                                <div class="h5">{{ $user->supported_vendor->{'name_en'} }}</div>
                                @if($user->isRestaurant())
                                    <div class="pt-2">restaurant type : {{ $user->restaurantType[0]->name_en ?? 'unknown' }}</div>
                                    <div class="pt-2">kitchen type : {{ $user->kitchenType[0]->name_en ?? 'unknown' }}</div>
                                @endif
                            </div>
                            <div class="col-4 d-flex flex-column justify-content-center">
                                <div>city: {{ $user->city->name_en }}</div>
                                <div class="pt-2">bank: {{ $user->bank->name_en }}</div>
                                <div class="pt-2">bank recipient name: {{ $user->bankRecipientName }}</div>
                                <div class="pt-2 d-inline">
                                    <a class="btn-sm btn-secondary" href="{{ $user->speech }}"><i class="ri-link-m"></i> speech</a>
                                    <a class="btn-sm btn-secondary" href="{{ $user->commercialRecord }}"><i class="ri-link-m"></i> commercial record</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <hr class="my-4" />
                            <div class="col-8">
                                <h4>description</h4>
                                <p>{!! nl2br($user->description) !!}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
@endsection
