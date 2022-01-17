@extends('themes.home.en-dashboard')

@section('main')

    <div class="sections">


        <section>

            <div class="d-none d-lg-block">
                <div class="position-absolute " style="top: 17rem ;left: 8rem">
                    <img src="{{ asset('img/home/bg/Path182.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 9rem ;left: 21rem">
                    <img src="{{ asset('img/home/bg/Path180.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 8rem ;left: 47%">
                    <img src="{{ asset('img/home/bg/Path179.png') }}" alt="">
                </div>
                <div class="position-absolute " style="top: 9rem ;right: 6rem">
                    <img src="{{ asset('img/home/bg/Path179.png') }}" alt="">
                </div>
            </div>

            <div class="section-1 block" id="home">
                <div class="container ">
                    <div class="row blocks">
                        <div class=" col-12 col-md mb-4">
                            <h1 class="font-weight-bold text-primary ">Aloo</h1>
                            <p class="font-weight-bold">We have collected for you most of the applications
                                that you need in one place
                            </p>
                            <span class="text-primary ">
                            Download the application now from
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
                        <div class=" col-12 col-md-6 img-group mb-4">

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
                <div class="container">
                    <div class="row blocks">
                        <div class=" col-12 col-md mb-4 section-text">
                            <h2 class="font-weight-bold text-primary ">About the Aloo app</h2>
                            <p class="">
                                This text is an example of a text that can be replaced in the same space. This text was
                                generated from the Arabic text generator, where you can generate such text or many other
                                texts in addition to increasing the number of characters generated by the application.
                                If you need more paragraphs, the Arabic text generator allows you to increase the number of
                                paragraphs as you want, the text will not appear divided and does not contain language
                                errors, the Arabic text generator is useful for web designers in particular, where the
                                customer often needs to see a real picture for site design.
                            </p>
                        </div>
                        <div class="offset-md-1"></div>

                        <div class=" col-12 col-md-4 text-center mb-4">
                            <img src="{{ asset('img/home/your-design.png') }}" alt="img-app" title="app" class="img-fluid">
                        </div>
                    </div>
                </div>
                <div class="section2-img-background d-none d-lg-block">
                    <img src="{{ asset('img/home/Path184.png') }}" class="img-fluid" alt="">
                </div>
            </div>

        </section>
        <div class="position-relative">
            <section>
                <div class="section-3 block" id="restaurant-owner">
                    <div class="container ">
                        <div class="row blocks">
                            <div class=" col-12 col-md-4 text-center mb-4 section3-img-first">
                                <img src="{{ asset('img/home/app-section3.png') }}" alt="img-app" title="app" class="img-fluid">
                            </div>
                            <div class="offset-md-1"></div>

                            <div class=" col-12 col-md mb-4 section-text">
                                <h4 class="font-weight-bold text-primary ">You are the owner of restaurants, supermarkets,
                                    pharmacies!</h4>
                                <p class="">
                                    This text is an example of a text that can be replaced in the same space. This text was
                                    generated from the Arabic text generator, where you can generate such text or many other
                                    texts in addition to increasing the number of characters generated by the application.
                                    If you need more paragraphs, the Arabic text generator allows you to increase the number
                                    of paragraphs as you want
                                </p>
                                <a href="{{ route('vendor.register') }}">
                                    <button type="button" class="btn btn-primary">Enter from here</button>
                                </a>


                            </div>

                        </div>
                    </div>
                </div>
            </section>

            <div class="  d-none d-lg-block" style="position: absolute;            right: 74px;
            z-index: -20;
            width: 79%;
            top: 21%;
            transform: rotate( -90deg);">
                <img src="{{ asset('img/home/Path185.png') }}" class="img-fluid" alt="">
            </div>

            <section>
                <div class="section-4 block " id="are-you-delivery">
                    <div class="container ">
                        <div class="row blocks">
                            <div class=" col-12 col-md mb-4 section-text">
                                <h2 class="font-weight-bold text-primary bg-white mb-0 pb-2 ">Are you Delivery!</h2>
                                <p class="bg-white">
                                    This text is an example of a text that can be replaced in the same space. This text was
                                    generated from the Arabic text generator, where you can generate such text or many other
                                    texts in addition to increasing the number of characters generated by the application.
                                    If you need more paragraphs, the Arabic text generator allows you to increase the number
                                    of paragraphs as you want, the text will not appear divided and does not contain
                                    language errors, the Arabic text generator is useful for web designers in particular,
                                    where the customer often needs to see a real picture for site design.
                                </p>
                                <a href="{{ route('driver.register') }}">
                                    <button type="button" class="btn btn-primary">Enter from here</button>
                                </a>
                            </div>
                            <div class="offset-md-1"></div>

                            <div class=" col-12 col-md-4 text-center  mb-4">
                                <img src="{{ asset('img/home/app-section4.png') }}" alt="img-app" title="app" class="img-fluid ">
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </div>
    </div>
@endsection
