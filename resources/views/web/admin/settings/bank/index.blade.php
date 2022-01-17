@extends('themes.admin.dashboard')

@section('name', 'bank')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.settings.bank.index') }}">bank</a></li>
                    <li class="breadcrumb-item active">banks list</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header" id="create_city_div">
                    <form id="create_bank_form" action="{{ route('admin.settings.bank.store') }}" method="POST">
                        @csrf
                        <input type="hidden" value="" name="name_en" id="name_en">
                        <input type="hidden" value="" name="name_ar" id="name_ar">
                    </form>
                    <button class="rounded btn btn-primary" onclick="createBank()" id="create_city">add new bank</button>
                </div>
                <div class="card-body">
                    {{ $dataTable->table() }}

                    @push('scripts')
                        {{ $dataTable->scripts() }}
                    @endpush
                </div>
            </div>
        </section>

    </main>

@endsection
@push('scripts')
    <script>
        async function createBank() {
            const {value: result} = await new swal({
                title: 'create bank',
                html:
                    '<input id="swal_name_en" class="swal2-input my-2" placeholder="name_en">' +
                    '<input id="swal_name_ar" class="swal2-input my-2" placeholder="name_ar">',
                preConfirm: () => ({
                    name_en: $('#swal_name_en').val(),
                    name_ar: $('#swal_name_ar').val(),
                    bank_id: $('#swal_bank_id').val()
                })
            });

            if (result) {
                $('#name_en').val(result.name_en);
                $('#name_ar').val(result.name_ar);
                $('#create_bank_form').submit();
            }
        }
    </script>

    <script>
        $(document).ready(function () {
            $('.swal2-select').select2();

            $('body').on('focusin', '.swal2-select', function () {
                $(this).select2();
            })
        });
    </script>
@endpush
