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
            <div class="form position-relative">
                <form class=" m-auto form-owner" method="POST" enctype="multipart/form-data" action="{{ route('driver.register.send') }}">
                    @csrf
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="name" placeholder="driver's name">
                    </div>
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="phone" placeholder="Telephone number">
                    </div>
                    <div class="form-group">
                        <input type="email" name="email" class="form-control" placeholder="Official Email">
                    </div>

                    <div class="form-group">
                        <input type="password" name="password" dir="auto" class="form-control" placeholder="password">
                    </div>
                    <div class="form-group">
                        <input type="password" name="password_confirmed" dir="auto" class="form-control" placeholder="confirm password">
                    </div>
                    <div class="form-group">
                        <select class="form-control" name="bank" id="bank" aria-label="Bank name">
                            <option selected="selected">Bank name</option>
                            @foreach($banks as $bank)
                                <option value="{{ $bank->id }}">{{ $bank->name }}</option>
                            @endforeach
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" dir="auto" class="form-control" name="beneficiaryName" placeholder="Beneficiary Name">
                    </div>

                    <div class="form-group">
                        <div class="form-group field-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" Iban" />
                                <span class="files">PDF ,JPG, PNG</span>
                                <input type="file" name="iban" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group field-row m25">
                            <div class="styled-file-select">
                                <input class="no-bg no-border form-control" type="text" disabled placeholder=" Image" />
                                <span class="files">JPG, PNG</span>
                                <input type="file" name="img" class="form-control"/>
                            </div>
                        </div>
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
