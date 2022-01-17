/*
Give the service worker access to Firebase Messaging.
Note that you can only use Firebase Messaging here, other Firebase libraries are not available in the service worker.
*/
importScripts('https://www.gstatic.com/firebasejs/7.23.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.23.0/firebase-messaging.js');

/*
Initialize the Firebase app in the service worker by passing in the messagingSenderId.
* New configuration for app@pulseservice.com
*/
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

/*
Retrieve an instance of Firebase Messaging so that it can handle background messages.
*/
const messaging = firebase.messaging();
messaging.setBackgroundMessageHandler(function(payload) {
    console.log(
        "[firebase-messaging-sw.js] Received background message ",
        payload,
    );
    /* Customize notification here */
    const noteTitle = payload.notification.title;
    const noteOptions = {
        body: payload.notification.body,
        icon: payload.notification.icon,
    };

    return self.registration.showNotification(
        noteTitle,
        noteOptions,
    );
});
