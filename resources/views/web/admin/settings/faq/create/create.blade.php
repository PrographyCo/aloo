@extends('themes.admin.dashboard')

@section('name', 'Frequently Asked Questions')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.settings.faq.index') }}">Frequently Asked Questions</a></li>
                    <li class="breadcrumb-item active">Create Frequently Asked Questions</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
                <div class="card-body">
                    <h5 class="card-title">Create Package</h5>
                    <form id="storeForm" class="row g-3" method="POST" action="{{ route('admin.settings.faq.store') }}"
                          enctype="multipart/form-data">
                        @csrf
                        <div class="col-md-6">
                            <div class="form-floating">
                                <textarea type="text" name="question_en" class="form-control w-100" id="name_en" placeholder="{{ __('web/admin/datatable.faq.question.en') }}" required style="height: auto" rows="10"></textarea>
                                <label for="name_en">{{ __('web/admin/datatable.faq.question.en') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <textarea type="text" name="answer_en" class="form-control w-100" id="name_ar" placeholder="{{ __('web/admin/datatable.faq.answer.en') }}" required style="height: auto" rows="10"></textarea>
                                <label for="name_ar">{{ __('web/admin/datatable.faq.answer.en') }}</label>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-floating">
                                <textarea type="text" name="question_ar" class="form-control w-100" id="name_en" placeholder="{{ __('web/admin/datatable.faq.question.ar') }}" required style="height: auto" rows="10"></textarea>
                                <label for="name_en">{{ __('web/admin/datatable.faq.question.ar') }}</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <textarea type="text" name="answer_ar" class="form-control w-100" id="name_ar" placeholder="{{ __('web/admin/datatable.faq.answer.ar') }}" required style="height: auto" rows="10"></textarea>
                                <label for="name_ar">{{ __('web/admin/datatable.faq.answer.ar') }}</label>
                            </div>
                        </div>

                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-primary btn-lg w-50">{{ __('web/admin/vendor/create.submit') }}</button>
                        </div>
                    </form>

                </div>
            </div>
        </section>

    </main>

@endsection
