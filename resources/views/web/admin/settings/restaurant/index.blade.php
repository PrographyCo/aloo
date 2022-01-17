@extends('themes.admin.dashboard')

@section('name', 'kitchen')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.settings.restaurant.index') }}">restaurant types</a></li>
                    <li class="breadcrumb-item active">restaurant types list</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header" id="create_city_div">
                    <form id="create_kitchen_form" action="{{ route('admin.settings.restaurant.store') }}" method="POST">
                        @csrf
                        <input type="hidden" value="" name="name_en" id="name_en">
                        <input type="hidden" value="" name="name_ar" id="name_ar">
                    </form>
                    <button class="rounded btn btn-primary" onclick="createKitchen()" id="create_city">add new restaurant type</button>
                </div>
                <div class="card-body">
                    {{ $dataTable->table() }}

                    @push('scripts')
                        {{ $dataTable->scripts() }}
                    @endpush
                </div>
                <form id="edit_kitchen_form" action="" method="POST">
                    @csrf
                    <input type="hidden" value="" name="name_en" id="edit_name_en">
                    <input type="hidden" value="" name="name_ar" id="edit_name_ar">
                </form>
            </div>
        </section>

    </main>

@endsection
@push('scripts')
    <script>
        async function createKitchen() {
            const {value: result} = await new swal({
                title: 'create restaurant',
                html:
                    '<input id="swal_name_en" class="swal2-input my-2" placeholder="name_en">' +
                    '<input id="swal_name_ar" class="swal2-input my-2" placeholder="name_ar">',
                preConfirm: () => ({
                    name_en: $('#swal_name_en').val(),
                    name_ar: $('#swal_name_ar').val()
                })
            });

            if (result) {
                $('#name_en').val(result.name_en);
                $('#name_ar').val(result.name_ar);
                $('#create_kitchen_form').submit();
            }
        }

        async function editKitchen($type) {
            let form = $('#edit_kitchen_form'), url = "{{ url('/admin/settings/restaurant/::type::') }}";
            form.prop('action', url.replace(/::type::/g,$type.id));

            const {value: result} = await new swal({
                title: 'edit restaurant',
                html:
                    '<input id="swal_edit_name_en" value="'+$type.name_en+'" class="swal2-input my-2" placeholder="name_en">' +
                    '<input id="swal_edit_name_ar" value="'+$type.name_ar+'" class="swal2-input my-2" placeholder="name_ar">',
                preConfirm: () => ({
                    name_en: $('#swal_edit_name_en').val(),
                    name_ar: $('#swal_edit_name_ar').val()
                })
            });

            if (result) {
                $('#edit_name_en').val(result.name_en);
                $('#edit_name_ar').val(result.name_ar);
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
