@extends('themes.driver.dashboard')

@section('name', 'dashboard')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('driver.dashboard') }}">Dashboard</a></li>
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
                                    <img src="{{ $user->img }}" class="rounded" alt="" style="max-height: 128px" />
                                </div>
                                <div class="col-9 row">
                                    <div class="col-12 d-flex">
                                        <div class="pt-3 h4 me-auto">{{ $user->name }}</div>
                                    </div>
                                    <div class="col-4 d-flex flex-column justify-content-center">
                                        <div class="pt-1">phone: {{ $user->phone }}</div>
                                        <div class="pt-1">email: {{ $user->email }}</div>
                                        <a class="btn-sm btn-secondary" href="{{ $user->iban }}"><i class="ri-link-m"></i> i ban</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
@endsection
