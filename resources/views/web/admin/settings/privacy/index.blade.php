@extends('themes.admin.dashboard')

@section('name', 'Privacy')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.settings.privacy.index') }}">Privacy</a></li>
                    <li class="breadcrumb-item active">Privacy list</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header" id="create_Privacy_div">
                    <div class="card-body">
                        {{ $dataTable->table() }}

                        @push('scripts')
                            {{ $dataTable->scripts() }}
                        @endpush
                    </div>
                    <form id="edit_Privacy_form" action="" method="POST">
                        @csrf
                        <input type="hidden" value="" name="en" id="edit_name_en">
                        <input type="hidden" value="" name="ar" id="edit_name_ar">
                    </form>
                </div>
        </section>

    </main>

@endsection
@push('scripts')
    <script>
        async function editPrivacy($Privacy) {
            console.log($Privacy)
            let form = $('#edit_Privacy_form'), url = "{{ url('/admin/settings/privacy/::Privacy::') }}";
            form.prop('action', url.replace(/::Privacy::/g,$Privacy.id));

            const {value: result} = await new swal({
                title: 'edit Privacy',
                html:
                    '<textarea id="swal_edit_name_en" class="swal2-input" placeholder="en" rows=10 cols=25 style="height: auto">'+$Privacy.en+'</textarea>' +
                    '<textarea id="swal_edit_name_ar" class="swal2-input" placeholder="ar" rows=10 cols=25 style="height: auto">'+$Privacy.ar+'</textarea>',
                preConfirm: () => ({
                    en: $('#swal_edit_name_en').val(),
                    ar: $('#swal_edit_name_ar').val()
                })
            });

            if (result) {
                $('#edit_name_en').val(result.en);
                $('#edit_name_ar').val(result.ar);
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
