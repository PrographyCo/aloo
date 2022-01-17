@extends('themes.admin.dashboard')

@section('name', 'city')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.settings.city.index') }}">city</a></li>
                    <li class="breadcrumb-item active">cities list</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header" id="create_city_div">
                    <form id="create_city_form" action="{{ route('admin.settings.city.store') }}" method="POST">
                        @csrf
                        <input type="hidden" value="" name="name_en" id="name_en">
                        <input type="hidden" value="" name="name_ar" id="name_ar">
                        <input type="hidden" value="" name="price" id="price">
                    </form>
                    <button class="rounded btn btn-primary" onclick="createCity()" id="create_city">add new city</button>
                </div>
                <div class="card-body">
                    {{ $dataTable->table() }}

                    @push('scripts')
                        {{ $dataTable->scripts() }}
                    @endpush
                </div>
                <form id="edit_city_form" action="" method="POST">
                    @csrf
                    <input type="hidden" value="" name="name_en" id="edit_name_en">
                    <input type="hidden" value="" name="name_ar" id="edit_name_ar">
                    <input type="hidden" value="" name="price" id="edit_price">
                </form>
            </div>
        </section>

    </main>

@endsection
@push('scripts')
    <script>
        async function createCity() {
            const {value: result} = await new swal({
                title: 'create city',
                html:
                    '<input id="swal_name_en" class="swal2-input my-2" placeholder="name_en">' +
                    '<input id="swal_name_ar" class="swal2-input my-2" placeholder="name_ar">' +
                    '<input type="number" value="" min="0" id="swal_price" class="swal2-input my-2" placeholder="price">',
                preConfirm: () => ({
                    name_en: $('#swal_name_en').val(),
                    name_ar: $('#swal_name_ar').val(),
                    price: $('#swal_price').val()
                })
            });

            if (result) {
                $('#name_en').val(result.name_en);
                $('#name_ar').val(result.name_ar);
                $('#price').val(result.price);
                $('#create_city_form').submit();
            }
        }
        async function editCity($city) {
            let form = $('#edit_city_form'), url = "{{ url('/admin/settings/city/::city::') }}";
            form.prop('action', url.replace(/::city::/g,$city.id));

            const {value: result} = await new swal({
                title: 'edit city',
                html:
                    '<input id="swal_edit_name_en" value="'+$city.name_en+'" class="swal2-input my-2" placeholder="name_en">' +
                    '<input id="swal_edit_name_ar" value="'+$city.name_ar+'" class="swal2-input my-2" placeholder="name_ar">' +
                    '<input type="number" value="'+$city.price.price+'" min="0" id="swal_edit_price" class="swal2-input my-2" placeholder="price">',
                preConfirm: () => ({
                    name_en: $('#swal_edit_name_en').val(),
                    name_ar: $('#swal_edit_name_ar').val(),
                    price: $('#swal_edit_price').val()
                })
            });

            if (result) {
                $('#edit_name_en').val(result.name_en);
                $('#edit_name_ar').val(result.name_ar);
                $('#edit_price').val(result.price);
                form.submit();
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
