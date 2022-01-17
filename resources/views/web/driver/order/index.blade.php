@extends('themes.driver.dashboard')

@section('name', 'orders')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('driver.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('driver.orders.index') }}">order</a></li>
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
                <form id="delete" action="" method="post">
                    @csrf
                    <input type="hidden" name="_method" value="DELETE">
                </form>
            </div>
        </section>

    </main>

@endsection

@push('scripts')
    <script>
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
                    form.prop('action', '/vendor/orders/cancel/'+id);
                    form.submit();
                }
            })
        }
    </script>
@endpush
