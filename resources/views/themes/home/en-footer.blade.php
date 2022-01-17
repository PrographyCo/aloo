<footer class="bg-primary pt-5">
    <div class="container">
        <div class="main-footer pt-md-4">

            <div class="row pt-4 mb-4 justify-content-between">
                <div class=" col-12 col-lg d-flex justify-content-between justify-content-md-start">
                    <div class="logo-footer"><img src="{{ asset('img/home/logo-footer.png') }}" class="img-fluid" alt="">
                    </div>
                    <div class="offset-1"></div>
                    <div>
                        <h5>follow me</h5>
                        <div class="d-flex flex-column socials pt-1">
                            <ul class="list-unstyled socials d-flex ">
                                <li><a href="{{ config('app.whatsapp-link') }}" class="social-links"><i class="ri-whatsapp-line"></i></a></li>
                                <li><a href="{{ config('app.facebook-link') }}" class="social-links"><i class="ri-facebook-fill"></i></a></li>
                                <li><a href="{{ config('app.twitter-link') }}" class="social-links"><i class="ri-twitter-fill"></i></a></li>
                                <li><a href="{{ config('app.youtube-link') }}" class="social-links"><i class="ri-youtube-fill"></i></a></li>
                                <li><a href="{{ config('app.mail-link') }}" class="social-links"><i class="ri-mail-line"></i></a></li>
                                <li><a href="{{ config('app.linkedin-link') }}" class="social-links"><i class="ri-linkedin-fill"></i></a></li>
                            </ul>
                        </div>
                    </div>

                </div>

                <div class="col-12 col-lg aloo-footer">
                    <h5>Aloo</h5>
                    <ul class="list-unstyled d-flex">
                        <li class="mb-md-2"><a href="#">Hot Product</a></li>
                        <li class="mb-md-2"><a href="#">Lower Prices</a></li>
                        <li class="mb-md-2"><a href="#">Huge discounts</a></li>
                    </ul>
                </div>

                <div class="col-12 col-lg-3 mb-3">

                    <div>
                        <h5>Download the Aloo app</h5>
                        <div class="d-flex mt-3 footer-download-img">
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
                </div>

            </div>
            <div class="footer-line">
                <div class="line"></div>
            </div>
        </div>

        <div class="copy p-2 text-center text-white">
            © <span id="year"></span> - All rights reserved for <a href="#" class="text-white">Aloo</a>
        </div>
    </div>

</footer>
