@extends('themes.vendor.dashboard')

@section('name', 'item')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.item.index') }}">item</a></li>
                    <li class="breadcrumb-item active">view item || {{ $item->name_en }}</li>
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
                                    <div class="pt-3 h4 me-auto">{{ $item->name_en }}</div>
                                    <a href="{{ route('vendor.item.edit', $item) }}" class="btn btn-secondary">edit</a>
{{--                                        <a href="" class="btn btn-warning">un ban</a>--}}
                                </div>
                                <div class="col-4 d-flex flex-column justify-content-center">
                                    <div>name: {{ $item->name_en }}</div>
                                    <div>price: {{ $item->price }}</div>
                                    <div>category: {{ $item->category->name_en }}</div>
                                </div>
                                <div class="col-4 d-flex flex-column justify-content-center">
                                    <div>amount: {{ $item->amount }}</div>
                                    <div>amount type: @if(Auth::user()->isRestaurant()) {{ $item->amount_type }} @else calories @endif</div>
                                    @if(Auth::user()->isRestaurant())
                                        <div>optional:
                                            @foreach($item->optionals ?? [] as $option)
                                                {!! \WEBHelper::textToSpan($option) !!}
                                            @endforeach
                                        </div>
                                        <div>size B (big): {!! \WEBHelper::textToSpan($item->sizes['Big'] ?? 'unknown') !!}</div>
                                        <div>size M (Medium): {!! \WEBHelper::textToSpan($item->sizes['Medium'] ?? 'unknown') !!}</div>
                                        <div>size S (Small): {!! \WEBHelper::textToSpan($item->sizes['Small'] ?? 'unknown') !!}</div>
                                    @endif
                                </div>
                                <div class="col-4 d-flex flex-column justify-content-center">
                                    @if(Auth::user()->isRestaurant())
                                        <div class="d-inline">extras:
                                            @foreach($item->extras ?? [] as $extra)
                                                {!! \WEBHelper::textToSpan($extra->name) !!}
                                            @endforeach
                                        </div>

                                        <div class="mt-3 d-inline">drinks:
                                            @foreach($item->drinks ?? [] as $drink)
                                                {!! \WEBHelper::textToSpan($drink->name) !!}
                                            @endforeach
                                        </div>
                                    @endif
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
                            <h5 class="card-title pt-0">branches</h5>
                            {{ $dataTable->table(['id' => 'branch-table'], true) }}
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
        var item_id = {{ $item->id }};
    </script>
    @if(Auth::user()->isRestaurant())
        <script src="{{ asset('js/vendor/branch.itemRestaurant.js') }}"></script>
    @else
        <script src="{{ asset('js/vendor/branch.item.js') }}"></script>
    @endif
@endpush
