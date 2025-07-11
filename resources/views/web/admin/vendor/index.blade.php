@extends('themes.admin.dashboard')

@section('name', $title)
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.vendor.index') }}">vendor</a></li>
                    <li class="breadcrumb-item active">{{ $title }}</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header">
                    <a class="rounded btn btn-primary" href="{{ route('admin.vendor.create') }}">add new vendor</a>
                    <a class="rounded btn btn-primary" href="{{ route('admin.vendor.datatable.supermarket') }}">supermarket vendor</a>
                    <a class="rounded btn btn-primary" href="{{ route('admin.vendor.datatable.pharmacy') }}">pharmacy vendor</a>
                    <a class="rounded btn btn-primary" href="{{ route('admin.vendor.datatable.restaurant') }}">restaurant vendor</a>

                    <form id="send_money" action="{{ route('admin.settings.city.store') }}" method="POST">
                        @csrf
                        <input type="hidden" value="" name="amount" id="amount">
                    </form>
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
        async function sendMoney(id,max) {
            let form = $('#send_money'), url = "{{ url('/admin/vendor/::vendor::/wallet') }}";
            form.prop('action', url.replace(/::vendor::/g,id));
            const {value: result} = await new swal({
                title: 'Send Money',
                html:
                    `<input id="swal_amount" type="number" max="${max}" class="swal2-input my-2" placeholder="amount" autofocus />`,
                preConfirm: () => ({
                    amount: $('#swal_amount').val(),
                })
            });

            if (result) {
                $('#amount').val(result.amount);
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
