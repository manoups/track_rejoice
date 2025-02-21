var mymap = null;

window.onload = function() {
    mymap = L.map('mapid', {
    minZoom: 3,
    maxZoom: 18
})
var data = [[${pets}]];
var zoom = [[${zoom}]]

if (data && 0 < data.length && data[0].latitude !== null && data[0].longitude !== "") {
    mymap.setView([data[0].latitude, data[0].longitude], 10);
    L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
        attribution: 'Pets'
    }).addTo(mymap);

    for (var i = 0; i < data.length; i++) {
        if (data[i].latitude !== null && data[i].longitude !== "") {
            L.marker([data[i].latitude, data[i].longitude]).addTo(mymap)
                .bindPopup(data[i].name)
                .openPopup();
        }
    }
} else {
    var centre_lng = [[${centre_lng}]];
    var centre_lat = [[${centre_lat}]];
    mymap.setView([centre_lat, centre_lng], zoom);
    L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
        attribution: 'Pets'
    }).addTo(mymap);
}
document.getElementById('zoom').value = mymap.getZoom();

mymap.on('zoom', function () {
    document.getElementById('zoom').value = mymap.getZoom()

});
mymap.on('click', function (e) {
    // Get the click coordinates relative to the map
    var clickPoint = mymap.mouseEventToLayerPoint(e.originalEvent);

    // Access the X and Y coordinates of the click
    var x = clickPoint.x;
    var y = clickPoint.y;

    console.log("Clicked at X:", x, "Y:", y);
    var lat = e.latlng.lat
    var lng = e.latlng.lng
    // location = '/search?lng=' + x + '&lat=' + y + '&distance=1'
    // (param1 = 'value1', param2 = 'value2');
    document.getElementById('lat').value = lat;
    document.getElementById('lng').value = lng;
    // You can use these coordinates for further actions, like displaying a popup
});