<div class="header bg-white shadow-sm">

    <div class="container p-0">

        <nav class="navbar  navbar-expand-lg navbar-light bg-white">
            <a class="navbar-brand" href="{{ route('home') }}"><img src="{{ asset('img/aloo-logo.png') }}" width="141" height="80" title="aloo"
                                                           alt="aloo"></a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03"
                    aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item  font-weight-bold ">
                        <a class="nav-link " href="{{ route('home') }}" data-scroll="home">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-scroll="about-aloo">About Aloo</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-scroll="restaurant-owner">restaurant owner</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-scroll="are-you-delivery">Are you delivery!</a>
                    </li>
                </ul>
                <form class="form-inline  my-lg-0 ">
                    <a href="{{ route('changeLang') }}?lang=ar" class="">
                        <button class="btn btn my-2 my-sm-0 bg-transparent" type="button">
                            عربي
                        </button>
                    </a>
                </form>
            </div>
        </nav>
    </div>
</div>
