function initMap() {
    const myLatlng = { lat: -25.363, lng: 131.044 };
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 4,
        center: myLatlng,
    });
    // Create the initial InfoWindow.
    let infoWindow = new google.maps.InfoWindow({
        position: myLatlng,
    });

    infoWindow.open(map);
    // Configure the click listener.
    map.addListener("click", (mapsMouseEvent) => {
        // Close the current InfoWindow.
        infoWindow.close();
        // Create a new InfoWindow.
        infoWindow = new google.maps.InfoWindow({
            position: mapsMouseEvent.latLng,
        });
        console.log(mapsMouseEvent.latLng);
        $('#latitude').val(mapsMouseEvent.latLng.lat);
        $('#longitude').val(mapsMouseEvent.latLng.lng);
        // infoWindow.setContent(
        //     JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
        // );
        infoWindow.open(map);
    });
}
