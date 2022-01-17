// Get the current year for the copyright
$('#year').text(new Date().getFullYear());
$(document).ready(function(){

    $('body').css('paddingTop',$(' .header nav .navbar-nav').innerHeight());

    //smoth scroll
    $('.header nav .navbar-nav li a').click(function (e) {
        // e.preventDefault();
        $('html ,body').animate({
            scrollTop:$( '#' + $(this).data('scroll')).offset().top - 60
        },1000);


    });
    //add active
    $(' .header nav .navbar-nav li a').click(function () {
        $(this).addClass('active').parent().siblings().find('a').removeClass('active');

    });


    $(window).scroll(function () {

        $('.block').each(function() {
            if ($(window).scrollTop() > $(this).offset().top ){
                var blockID =$(this).attr('id');
                $(' .header nav .navbar-nav a').removeClass('active');

                $('.header nav .navbar-nav li a[data-scroll = "' + blockID +  '"]').addClass('active');

            }
        });



    });

});


$(document).ready(function(){
    $('.styled-file-select input[type=file]').change(function(e){
        $(this).parent('.styled-file-select').find('input[type=text]').val($(this).val().replace(/.*(\/|\\)/, ''));
    })
});