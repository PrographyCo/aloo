$(document).on('change', '.checkbox-input input[type="checkbox"]', function () {
    console.log($(this)[0].checked);
    $.ajax({
        headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
        method : "POST",
        url: $(this).data('ajax'),
        data: { amount: $(this)[0].checked }
    }).done(function() {
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
        })
        Toast.fire({
            icon: 'success',
            title: 'change amount done'
        });
    });
});
