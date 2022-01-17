@extends('themes.home.ar-dashboard')

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
                <h1>ألـوو</h1>
                <h5>يمكنك ارسال الطلب من هنا</h5>
            </div>
            <div class="form  position-relative">
                <form class="m-auto form-owner" enctype="multipart/form-data" method="POST" action="{{ route('vendor.register.send') }}">
                    @csrf
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="legalName" placeholder="الاسم القانوني ">
                    </div>
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="brandName" placeholder="الاسم التجاري">
                    </div>
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="commercialNo" placeholder="رقم السجل التجاري">
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <select class="form-control" name="supported_vendor" id="supported_vendor" aria-label="نوع المتجر" required>
                                <option selected="selected">نوع المتجر</option>
                                @foreach($supportedVendors as $supportedVendor)
                                    <option value="{{ $supportedVendor->id }}">{{ $supportedVendor->name }}</option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group col-md-6">

                            <select class="form-control" id="city" name="city" aria-label="المدينة" required>
                                <option selected="selected">المدينة</option>
                                @foreach($cities as $city)
                                    <option value="{{ $city->id }}">{{ $city->name }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div id="restaurant_type_div" class="form-group col-md-6 d-none">
                            <select class="form-control" name="restaurant_type" id="restaurant_type" aria-label="نوع المطعم">
                                <option value="" selected="selected">نوع المطعم</option>
                                @foreach($restaurantType as $type)
                                    <option value="{{ $type->id }}">{{ $type->name }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div id="kitchen_type_div" class="form-group col-md-6 d-none">
                            <select class="form-control" name="kitchen_type" id="kitchen_type" aria-label="نوع المطبخ">
                                <option value="" selected="selected">نوع المطبخ</option>
                                @foreach($kitchenType as $type)
                                    <option value="{{ $type->id }}">{{ $type->name }}</option>
                                @endforeach
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <textarea type="text" dir="auto" name="description" class="form-control w-100" placeholder="وصف المتجر" rows="4"></textarea>
                    </div>


                    <div class="input-group mb-3">
                        <input type="text" dir="auto" name="minPrice" class="form-control" placeholder="الحد الأدنى للطلب">
                        <div class="input-group-append">
                            <span class="input-group-text">SAR</span>
                        </div>
                    </div>


                    <div class="form-group">
                        <input type="email" class="form-control" name="email" placeholder="البريد الالكتروني الرسمي ">
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" name="phone" placeholder="رقم الهاتف الرسمي ">
                    </div>
                    <div class="form-group">
                        <label  class="text-primary">معلومات البنك</label>
                        <div class="form-group">
                            <select class="form-control" name="bank" id="bank" aria-label="اسم البنك">
                                <option selected="selected">اسم البنك</option>
                                @foreach($banks as $bank)
                                    <option value="{{ $bank->id }}">{{ $bank->name }}</option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" dir="auto" class="form-control" name="iban" placeholder="رقم الايبان البنكي">
                        </div>
                        <div class="form-group">
                            <input type="text" dir="auto" class="form-control" name="beneficiaryName" placeholder="اسم المستفيد">
                        </div>

                    </div>


                    <div class="form-group">
                        <label  class="text-primary" >ارفاق المستندات والصور</label>
                        <div class="form-group field-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" صورة الشعار بجوده عالية" />
                                <span class="files">JPG, PNG</span>
                                <input type="file" name="logo" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group field-row m25" id="image_type_div">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" صورة خلفية بجوده عالية" />
                                <span class="files">JPG, PNG</span>
                                <input type="file" name="image" class="form-control"/>
                            </div>
                        </div>


                        <div class="form-group field-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" صورة من السجل التجاري" />
                                <span class="files">PDF, JPG, PNG</span>
                                <input type="file" name="commercialRecord" class="form-control"/>
                            </div>
                        </div>
                        <div class="f form-group ield-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" خطاب" />
                                <span class="files">PDF, JPG, PNG</span>
                                <input type="file" name="speech"  class="form-control"/>
                            </div>
                        </div>

                    </div>


                    <div class="nots-form mb-2 d-flex flex-column align-items-end">
                        <div> ﺧﻄﺎﺏ ﺑﻪ ﺍﻟﺘﻔﺎﺻﻴﻞ ﺍﻟﺒﻨﻜﻴﺔ ﺍﻟﺨﺎﺻﺔ ﺑﺎﻟﺸﺮﻛﺔ/</div>
                        <div>ﺑﺎﻟﻤﺆﺳﺴﺔ ﻣﻮﻗﻊ ﻭ ﻣﺨﺘﻮﻡ</div>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 mb-4">إرسال</button>
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
