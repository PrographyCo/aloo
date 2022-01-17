@extends('themes.home.ar-dashboard')

@section('main')
    <div class="sections">
        <section>
            <div class="d-none d-lg-block">
                <div class="position-absolute " style="top: 17rem ;right: 8rem">
                    <img src="{{ asset('img/home/bg/Path182.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 9rem ;right: 27rem">
                    <img src="{{ asset('img/home/bg/Path180.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 8rem ;right: 47%">
                    <img src="{{ asset('img/home/bg/Path179.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 9rem ;left: 6rem">
                    <img src="{{ asset('img/home/bg/Path179.png') }}" alt="">
                </div>
            </div>
            <div class="section-1 block" id="home">
                <div class="container ">

                    <div class="row blocks">
                        <div class=" col-12 col-md mb-4">
                            <h1 class="font-weight-bold text-primary ">ألـوو</h1>
                            <p class="font-weight-bold">جـمـعـنا لـك أغـلـب الـتـطـبـيـقـات
                                الـتـي تـحـتـاجـهـا فـي مـكـان واحـد
                            </p>
                            <span class="text-primary ">
                                    حمل التطبيق الأن من
                                </span>
                            <div class="d-flex mt-3">
                                <a href="{{ config('app.app-store-link') }}" class="d-block">
                                    <img src="{{ asset('img/home/app-store-download.png') }}" width="126" height="40" title="app-store" alt="">
                                </a>
                                <div style="width:.6rem">

                                </div>
                                <a href="{{ config('app.google-play-link') }}">
                                    <img src="{{ asset('img/home/download-google-play.png') }}" width="126" height="40" alt="" title="google-play">
                                </a>
                            </div>
                        </div>
                        <div class="offset-md-1"></div>
                        <div class=" col-12 col-md-6   img-group mb-4">
                            <div class="group position-relative">
                                <div class="row">
                                    <div class="circle2  col-6">
                                        <img src="{{ asset('img/home/img1-group.png') }}" alt="">
                                    </div>
                                    <div class="circle1 col-6">
                                        <img src="{{ asset('img/home/img2-group.png') }}" alt="">
                                    </div>
                                </div>
                                <div class="path-img">
                                    <img src="{{ asset('img/home/Path173.png') }}" class="img-fluid" alt="">
                                </div>
                                <div class="row">

                                    <div class="circle4 col-6 ">
                                        <img src="{{ asset('img/home/img4-group.png') }}" alt="">
                                    </div>
                                    <div class="circle3 col-6">
                                        <img src="{{ asset('img/home/img3-group.png') }}" alt="">
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div class="d-none d-lg-block">
                <div class="position-absolute " style="top: 97% ;left: 21%">
                    <img src="{{ asset('img/home/bg/Path180.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 61% ;right: 19%">
                    <img src="{{ asset('img/home/bg/Path183.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 99% ;right: 15%">
                    <img src="{{ asset('img/home/bg/Path179.png') }}" alt="">
                </div>
            </div>
        </section>

        <section>
            <div class="section-2 block" id="about-aloo">
                <div class="container ">
                    <div class="row blocks">
                        <div class=" col-12 col-md  mb-4">
                            <h2 class="font-weight-bold text-primary "> حول تطبيق ألو</h2>
                            <p class="section-text">
                                هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد هذا النص من مولد النص العربى،
                                حيث يمكنك أن تولد مثل هذا النص أو العديد من النصوص الأخرى إضافة إلى زيادة عدد الحروف التى يولدها
                                التطبيق.
                                إذا كنت تحتاج إلى عدد أكبر من الفقرات يتيح لك مولد النص العربى زيادة عدد الفقرات كما تريد، النص
                                لن يبدو مقسما ولا يحوي أخطاء لغوية، مولد النص العربى مفيد لمصممي المواقع على وجه الخصوص، حيث
                                يحتاج العميل فى كثير من الأحيان أن يطلع على صورة حقيقية لتصميم الموقع.
                            </p>


                        </div>
                        <div class="offset-md-1"></div>
                        <div class=" col-12 col-md-4 text-center mb-4">
                            <img src="{{ asset('img/home/your-design.png') }}" alt="img-app" title="app" class="img-fluid">
                        </div>
                    </div>
                </div>
                <div class="section2-img-background   d-none d-lg-block">
                    <img src="{{ asset('img/home/Path184.png') }}" class="img-fluid" alt="">
                </div>
            </div>
        </section>
        <div class="position-relative">

            <section>
                <div class="section-3 block" id="restaurant-owner">
                    <div class="container ">
                        <div class="row blocks">
                            <div class=" col-12 col-md-4 section3-img-first text-center mb-4">
                                <img src="{{ asset('img/home/app-section3.png') }}" alt="img-app" title="app" class="img-fluid">
                            </div>
                            <div class="offset-md-1"></div>
                            <div class=" col-12 col-md mb-4 section-text">
                                <h4 class="font-weight-bold text-primary ">أنت صاحب مطاعم,سوبرماركت,صيدليات !</h4>
                                <p class="">
                                    هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد هذا النص من مولد النص العربى،
                                    حيث يمكنك أن تولد مثل هذا النص أو العديد من النصوص الأخرى إضافة إلى زيادة عدد الحروف التى يولدها
                                    التطبيق.
                                    إذا كنت تحتاج إلى عدد أكبر من الفقرات يتيح لك مولد النص العربى زيادة عدد الفقرات كما تريد، النص
                                    لن يبدو مقسما ولا يحوي أخطاء لغوية، مولد النص العربى مفيد لمصممي المواقع على وجه الخصوص، حيث
                                    يحتاج العميل فى كثير من الأحيان أن يطلع على صورة حقيقية لتصميم الموقع. </p>
                                <a href="{{ route('vendor.register') }}">
                                    <button type="button" class="btn btn-primary">أدخل من هنا</button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

            </section>

            <div class="  d-none d-lg-block" style="position: absolute;

   position: absolute;
    right: -54px;
    z-index: -20;
    width: 73%;
    top: 40%;
">
                <img src="{{ asset('img/home/Path185.png') }}" class="img-fluid" alt="">
            </div>
            <section>
                <div class="section-4 block " id="are-you-delivery">
                    <div class="container ">
                        <div class="row blocks">
                            <div class=" col-12 col-md mb-4 section-text ">
                                <h2 class="font-weight-bold text-primary bg-white  mb-0 pb-2 ">هل أنت ديلفري ! </h2>
                                <p class="bg-white">
                                    هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد هذا النص من مولد النص العربى،
                                    حيث يمكنك أن تولد مثل هذا النص أو العديد من النصوص الأخرى إضافة إلى زيادة عدد الحروف التى يولدها
                                    التطبيق.
                                    إذا كنت تحتاج إلى عدد أكبر من الفقرات يتيح لك مولد النص العربى زيادة عدد الفقرات كما تريد، النص
                                    لن يبدو مقسما ولا يحوي أخطاء لغوية، مولد النص العربى مفيد لمصممي المواقع على وجه الخصوص، حيث
                                    يحتاج العميل فى كثير من الأحيان أن يطلع على صورة حقيقية لتصميم الموقع.
                                </p>
                                <a href="{{ route('driver.register') }}">
                                    <button type="button" class="btn btn-primary">أدخل من هنا</button>
                                </a>
                            </div>
                            <div class="offset-md-1"></div>

                            <div class=" col-12 col-md-4 text-center mb-4">
                                <img src="{{ asset('img/home/app-section4.png') }}" alt="img-app" title="app" class="img-fluid ">
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
@endsection

@push('scripts')
@endpush
