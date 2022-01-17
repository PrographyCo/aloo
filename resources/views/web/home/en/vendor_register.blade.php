@extends('themes.home.en-dashboard')

@section('main')
    <div class="sections mb-4">
        <div class="d-none d-lg-block">
            <div class="position-absolute " style="top: 9rem ;right: 6rem">
                <img src="{{ asset('img/home/bg/Path179.png') }}" alt="">
            </div>
            <div class="position-absolute " style="top: 17rem ;left: 8rem">
                <img src="{{ asset('img/home/bg/Path182.png') }}" alt="">
            </div>
            <div class="position-absolute " style="top: 9rem ;left: 21rem">
                <img src="{{ asset('img/home/bg/Path180.png') }}" alt="">
            </div>

            <div class="position-absolute " style="top: 44rem ;right: 21rem">
                <img src="{{ asset('img/home/bg/Path183.png') }}" alt="">
            </div>
        </div>




        <div class="container">
            <div class="text-center mb-4">
                <h1>Aloo</h1>
                <h5>You can submit a request from here</h5>
            </div>
            <div class="form  position-relative">
                <form class="m-auto form-owner" enctype="multipart/form-data" method="POST" action="{{ route('vendor.register.send') }}">
                    @csrf
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="legalName" placeholder="Legal name">
                    </div>
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="brandName" placeholder="Trade Name">
                    </div>
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="commercialNo" placeholder="Commercial Registration No">
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <select class="form-control" name="supported_vendor" id="supported_vendor" aria-label="Store Type" required>
                                <option selected="selected">Store Type</option>
                                @foreach($supportedVendors as $supportedVendor)
                                    <option value="{{ $supportedVendor->id }}">{{ $supportedVendor->name }}</option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group col-md-6">

                            <select class="form-control" id="city" name="city" aria-label="City" required>
                                <option selected="selected">City</option>
                                @foreach($cities as $city)
                                    <option value="{{ $city->id }}">{{ $city->name }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div id="restaurant_type_div" class="form-group col-md-6 d-none">
                            <select class="form-control" name="restaurant_type" id="restaurant_type" aria-label="Restaurant Type">
                                <option value="" selected="selected">Restaurant Type</option>
                                @foreach($restaurantType as $type)
                                    <option value="{{ $type->id }}">{{ $type->name }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div id="kitchen_type_div" class="form-group col-md-6 d-none">
                            <select class="form-control" name="kitchen_type" id="kitchen_type" aria-label="kitchen Type">
                                <option value="" selected="selected">kitchen Type</option>
                                @foreach($kitchenType as $type)
                                    <option value="{{ $type->id }}">{{ $type->name }}</option>
                                @endforeach
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <textarea type="text" dir="auto" name="description" class="form-control w-100" placeholder="Store Description" rows="4"></textarea>
                    </div>


                    <div class="input-group mb-3">
                        <input type="text" dir="auto" name="minPrice" class="form-control"  placeholder="Minimum order">
                        <div class="input-group-append">
                            <span class="input-group-text">SAR</span>
                        </div>
                    </div>


                    <div class="form-group">
                        <input type="email" class="form-control" name="email" placeholder="Official Email">
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" name="phone" placeholder="Official Phone">
                    </div>
                    <div class="form-group">
                        <label  class="text-primary">Bank information</label>
                        <div class="form-group">
                            <select class="form-control" name="bank" id="bank" aria-label="Bank name">
                                <option selected="selected">Bank name</option>
                                @foreach($banks as $bank)
                                    <option value="{{ $bank->id }}">{{ $bank->name }}</option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" dir="auto" class="form-control" name="iban" placeholder="Bank IBAN number">
                        </div>
                        <div class="form-group">
                            <input type="text" dir="auto" class="form-control" name="beneficiaryName" placeholder="Beneficiary Name">
                        </div>

                    </div>


                    <div class="form-group">
                        <label  class="text-primary" >Attachments</label>
                        <div class="form-group field-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" Vendor logo" />
                                <span class="files">JPG, PNG</span>
                                <input type="file" name="logo" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group field-row m25" id="image_type_div">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" Vendor image" />
                                <span class="files">JPG, PNG</span>
                                <input type="file" name="image" class="form-control"/>
                            </div>
                        </div>


                        <div class="form-group field-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" Photo of the commercial register" />
                                <span class="files">PDF, JPG, PNG</span>
                                <input type="file" name="commercialRecord" class="form-control"/>
                            </div>
                        </div>
                        <div class="f form-group ield-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" Speech" />
                                <span class="files">PDF, JPG, PNG</span>
                                <input type="file" name="speech"  class="form-control"/>
                            </div>
                        </div>

                    </div>


                    <div class="nots-form mb-2 d-flex flex-column align-items-end">
                        <div>A letter with the company's bank details/</div>
                        <div>Signed and sealed by the institution</div>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 mb-4">Send</button>
                </form>
                @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
                <div class="d-none d-lg-block">
                    <div class="position-absolute " style="top: 84% ;right: 21rem">
                        <img src="{{ asset('img/home/bg/Path180.png') }}" alt="">
                    </div>
                    <div class="position-absolute " style="top: 91% ;left: 21rem">
                        <img src="{{ asset('img/home/bg/Path182.png') }}" alt="">
                    </div>


                </div>

            </div>


        </div>

    </div>
@endsection

@push('scripts')
    <script>
        $('#supported_vendor').on('change', function (){
            if(this.value == 3){
                $('#restaurant_type_div').removeClass('d-none');
                $('#kitchen_type_div').removeClass('d-none');
                $('#image_type_div').addClass('d-none');
            } else {
                $('#restaurant_type_div').addClass('d-none');
                $('#kitchen_type_div').addClass('d-none');
            }
        });
    </script>
@endpush
