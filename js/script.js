// myLatlng is the coordination center that we like to show on the map (Focus)
var myLatlng = new google.maps.LatLng(60.17,24.93);
var image = 'GoogleMapsMarkers/commercial-places.png';
var geocoder;



function initialize() {
  geocoder = new google.maps.Geocoder();
  var mapOptions = {
    center: myLatlng,
    zoom: 12
  };
  var map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
  var marker = new google.maps.Marker({
    position: myLatlng,
    map: map,
    animation: google.maps.Animation.BOUNCE,
    title:"Hello World!",
    icon:image
  });
  codeAddress(map);
}

function codeAddress(map) {
  //var address = document.getElementById("address").value;
  geocoder.geocode( { 'address': "Helsinki"}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
      //map.setCenter(results[0].geometry.location);
      var newMarker = new google.maps.Marker({
          map: map,
          position: results[0].geometry.location,
          icon:'GoogleMapsMarkers/computers.png'
      });
    } else {
      alert("Geocode was not successful for the following reason: " + status);
    }
  });
}

google.maps.event.addDomListener(window, 'load', initialize);
