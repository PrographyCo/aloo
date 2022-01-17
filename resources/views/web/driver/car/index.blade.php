@extends('themes.driver.dashboard')

@section('name', 'car')
@section('main')
    <main id="main" class="main">

        <div class="pagetitle">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="{{ route('driver.dashboard') }}">Home</a></li>
                    <li class="breadcrumb-item"><a href="{{ route('driver.car.index') }}">cars</a></li>
                    <li class="breadcrumb-item active">all car</li>
                </ol>
            </nav>
        </div>

        <section class="section dashboard">
            <div class="card p-4">
                <div class="card-header">
                    <a class="btn btn-primary" href="{{ route('driver.car.create') }}">add new car</a>

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
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        });

        function changePassword(url, name) {
            // console.log($(thisElement).parents('tr'));
            (new swal({
                title: 'change password for ' + name,
                html:
                    '<input type="password" id="password" class="swal2-input" placeholder="password">' +
                    '<input type="password" id="password_confirm" class="swal2-input mb-2" placeholder="confirm password">',
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        resolve([
                            $('#password').val(),
                            $('#password_confirm').val()
                        ])
                    })
                },
            })).then(function (result) {
                if (result.isConfirmed && result.value[0] === result.value[1]){
                    $.ajax({
                        headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                        method : "POST",
                        url: url,
                        data: { password: result.value[0], password_confirm: result.value[1] },
                        error : function (data) {
                            console.log(data.responseJSON.errors.password);
                            var html = "";
                            data.responseJSON.errors.password.forEach((value) => {
                                html +=  value + "<br>";
                            })
                            Swal.fire({
                                icon : 'error',
                                title: data.responseJSON.message,
                                html: html,
                            }).then(function () {
                                changePassword(url, name);
                            });
                        }
                    }).done(function() {
                        Toast.fire({
                            icon: 'success',
                            title: 'change password done'
                        })
                    });
                } else if (!result.isDismissed){
                    Swal.fire({
                        icon: 'error',
                        title: 'password not matches'
                    }).then(function (){
                        changePassword(url, name);
                    })
                }
            }).catch()
        }

        async function sendMoney(id,max) {
            let form = $('#send_money'), url = "{{ url('/driver/car/::car::/wallet') }}";
            form.prop('action', url.replace(/::car::/g,id));
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
@endpush
