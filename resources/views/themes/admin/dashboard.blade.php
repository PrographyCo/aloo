@extends('themes.theme')


@section('content')

    @include('themes.admin.header')

    @include('themes.admin.sidebar')
    @section('main')
    @show
    @include('themes.admin.footer')

@endsection

@section('script')
@stack('scripts')
<script src="https://www.gstatic.com/firebasejs/7.23.0/firebase.js"></script>
<script>

    const firebaseConfig = {
        apiKey: "AIzaSyCnTzMbDG1J7v0YnfYHAkJLpioXQCCHWBk",
        authDomain: "aloo-5d9ab.firebaseapp.com",
        databaseURL: "https://aloo-5d9ab-default-rtdb.firebaseio.com",
        projectId: "aloo-5d9ab",
        storageBucket: "aloo-5d9ab.appspot.com",
        messagingSenderId: "581669989615",
        appId: "1:581669989615:web:92ec6e6218cc3d7aa27f1a",
        measurementId: "G-N7XLGH8DKE"
    };

    firebase.initializeApp(firebaseConfig);
    const messaging = firebase.messaging();

    // function initFirebaseMessagingRegistration() {
        messaging
            .requestPermission()
            .then(function () {return messaging.getToken()})
            .then(function(token) {
                console.log(token);
                $.ajax({
                    url: '{{ route("admin.save-token") }}',
                    type: 'POST',
                    data: {token: token},
                    dataType: 'JSON',
                    success: function (response) {
                        console.log('Token saved successfully.');
                    },
                    error: function (err) {
                        console.log('User Chat Token Error'+ err);
                    },
                });
            }).catch(function (err) {
            console.log('User Chat Token Error'+ err);
        });
    // }

    messaging.onMessage(function(payload) {
        const noteTitle = payload.notification.title;
        const noteOptions = {
            body: payload.notification.body,
            icon: payload.notification.icon,
        };
        new Notification(noteTitle, noteOptions);
    });
</script>
@endsection
