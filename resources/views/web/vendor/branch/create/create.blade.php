@extends('themes.vendor.dashboard')

@section('name', 'vendor')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('vendor.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('vendor.branch.index') }}">branch</a></li>
                    <li class="breadcrumb-item active">create branch</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    <h5 class="card-title">{{ __('web/vendor/branch/create.main') }}</h5>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('vendor.branch.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                        <div class="col-md-12">
                            <div class="form-floating">
                                <input type="text" name="name" class="form-control w-100" id="name" placeholder="{{ __('web/vendor/branch/create.name') }}" required>
                                <label for="name">{{ __('web/vendor/branch/create.name') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" name="password" class="form-control w-100" id="password" placeholder="{{ __('web/vendor/branch/create.password') }}" required>
                                <label for="password">{{ __('web/vendor/branch/create.password') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="password" name="password_confirmation" class="form-control w-100" id="password_confirmation" placeholder="{{ __('web/vendor/branch/create.password_confirmation') }}" required>
                                <label for="password_confirmation">{{ __('web/vendor/branch/create.password_confirmation') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <select class="form-select" id="city" name="city" aria-label="{{ __('web/vendor/branch/create.city') }}" required>
                                    <option selected="selected">{{ __('web/vendor/branch/create.city') }}</option>
                                    @foreach($cities as $city)
                                        <option value="{{ $city->id }}">{{ $city->name }}</option>
                                    @endforeach
                                </select>
                                <label for="city">{{ __('web/vendor/branch/create.city') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" name="manager" id="manager" placeholder="{{ __('web/vendor/branch/create.manager') }}" required>
                                <label for="manager">{{ __('web/vendor/branch/create.manager') }}</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" name="managerPhone" id="managerPhone" placeholder="{{ __('web/vendor/branch/create.managerPhone') }}" required>
                                <label for="managerPhone">{{ __('web/vendor/branch/create.managerPhone') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="email" class="form-control w-100" name="managerEmail" id="managerEmail" placeholder="{{ __('web/vendor/branch/create.managerEmail') }}" required>
                                <label for="managerEmail">{{ __('web/vendor/branch/create.managerEmail') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" name="managerPosition" class="form-control w-100" id="managerPosition" placeholder="{{ __('web/vendor/branch/create.managerPosition') }}" required>
                                <label for="managerPosition">{{ __('web/vendor/branch/create.managerPosition') }}</label>
                            </div>
                        </div>
                        <div class="col-12">
                            <input type="hidden" name="latitude" id="latitude" required>
                            <input type="hidden" name="longitude" id="longitude" required>
                            <div id='map' style="width: auto; height: 500px;"></div>
                        </div>
                        <hr>
                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/vendor/branch/create.submit') }}</button>
                        </div>
                    </form>

                </div>
                @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
            </div>
        </section>

    </main>

@endsection

@push('scripts')
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script>
        let map;
        let markers = [];
        function initMap() {
            const haightAshbury = { lat: 24.296, lng: 40.95};
            map = new google.maps.Map(document.getElementById("map"), {
                zoom: 5,
                center: haightAshbury,
                mapId: "ac5a49dfdab492ed",
            });
            map.addListener("click", (event) => {
                addMarker(event.latLng);
            });
        }

        // Adds a marker to the map and push to the array.
        function addMarker(position) {
            $('#latitude').val(position.lat());
            $('#longitude').val(position.lng());
            setMapOnAll(null);
            const marker = new google.maps.Marker({
                position,
                map,
            });

            markers.push(marker);
        }

        // Sets the map on all markers in the array.
        function setMapOnAll(map) {
            for (let i = 0; i < markers.length; i++) {
                markers[i].setMap(map);
            }
        }
    </script>
    <script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCnTzMbDG1J7v0YnfYHAkJLpioXQCCHWBk&callback=initMap&v=weekly"
        async
    ></script>
@endpush
