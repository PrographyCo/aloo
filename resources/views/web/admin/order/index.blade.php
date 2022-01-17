@extends('themes.admin.dashboard')

@section('name', 'orders')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('admin.order.index') }}">order</a></li>
                    <li class="breadcrumb-item active">orders list</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
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
        async function changeOrder(route, order_id, oldValue, thisElement) {
            // console.log($(thisElement).parents('tr'));
            const {value: deliver_id} = await Swal.fire({
                title: 'Select field validation',
                input: 'select',
                inputOptions: @json($deliver),
                inputPlaceholder: 'Select a deliver for order number #' + order_id,
                showCancelButton: true,
                inputValue : oldValue,
            })

            if(deliver_id){
                $.ajax({
                    headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                    method : "POST",
                    url: route,
                    data: { deliver_id: deliver_id }
                }).done(function() {
                    const swal = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        icon: 'success',
                        title: 'change deliver done',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    swal.fire();
                });
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
