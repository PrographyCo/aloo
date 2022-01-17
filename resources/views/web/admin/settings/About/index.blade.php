@extends('themes.admin.dashboard')

@section('name', 'Client Service')
@section('main')
    <main id="main" class="main">
        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-body">
                    {{ $dataTable->table() }}

                    @push('scripts')
                        {{ $dataTable->scripts() }}
                    @endpush
                </div>
            </div>

            <form id="edit_city_form" action="" method="POST">
                @csrf
                <input type="hidden" value="" name="link" id="edit_name_en">
            </form>
        </section>

    </main>

@endsection

@push('scripts')
    <script>
        async function editAbout(about) {
            let form = $('#edit_city_form'), url = "{{ url('/admin/settings/about/::about::') }}";
            form.prop('action', url.replace(/::about::/g,about.id));

            const {value: result} = await new swal({
                title: 'edit city',
                html:
                    '<input id="swal_edit_name_en" value="'+about.link+'" class="swal2-input my-2" placeholder="link">',
                preConfirm: () => ({
                    link: $('#swal_edit_name_en').val()
                })
            });

            if (result) {
                $('#edit_name_en').val(result.link);
                form.submit();
            }
        }

        $(document).ready(function () {
            $('.swal2-select').select2();

            $('body').on('focusin', '.swal2-select', function () {
                $(this).select2();
            })
        });
    </script>
@endpush
