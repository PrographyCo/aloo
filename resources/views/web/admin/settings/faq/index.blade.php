@extends('themes.admin.dashboard')

@section('name', 'Frequently Asked Questions')
@section('main')
    <main id="main" class="main">
        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header" id="create_city_div">
                    <a class="rounded btn btn-primary" href="{{ route('admin.settings.faq.create') }}">add new Question</a>
                </div>
                <form id="delete" action="" method="post">
                    @csrf
                    <input type="hidden" name="_method" value="DELETE">
                </form>
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
        $(document).ready(function () {
            $('.swal2-select').select2();

            $('body').on('focusin', '.swal2-select', function () {
                $(this).select2();
            })
        });

        function confirmDelete(id){
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this! all",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete!'
            }).then((result) => {
                if (result.isConfirmed) {
                    let form = $('#delete');
                    form.prop('action', '/admin/settings/faq/'+id);
                    form.submit();
                }
            })
        }

    </script>
@endpush
