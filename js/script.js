// myLatlng is the coordination center that we like to show on the map (Focus)
var myLatlng = new google.maps.LatLng(60.17,24.93);
var image = 'GoogleMapsMarkers/commercial-places.png';
var geocoder;

var polygon = [{'label':1, 'points':[{'y':60.173151, 'x':24.935794},{'y':60.173279, 'x':24.946093},{'y':60.169810, 'x':24.941780}]}];



function initialize() {
  var tempPolygon = [];
  var poly;

  geocoder = new google.maps.Geocoder();
  var mapOptions = {
    center: myLatlng,
    zoom: 12
  };
  var map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  for (i = 0; i < polygon.length; i++) {
    for(j = 0; j < polygon[i].points.length; j++){
      tempPolygon.push(new google.maps.LatLng(polygon[i].points[j].y, polygon[i].points[j].x));
    }
    tempPolygon.push(new google.maps.LatLng(polygon[i].points[0].y, polygon[i].points[0].x));
  }
console.log(tempPolygon ,"yes",polygon)
  var marker = new google.maps.Marker({
    position: myLatlng,
    map: map,
    animation: google.maps.Animation.BOUNCE,
    title:"Hello World!",
    icon:image
  });
  codeAddress(map);
  poly = new google.maps.Polygon({
    paths: tempPolygon,
    strokeColor: '#FF0000',
    strokeOpacity: 0.8,
    strokeWeight: 2,
    fillColor: '#FF0000',
    fillOpacity: 0.35
  });
  poly.setMap(map);
/*
  var bermudaTriangle;
  var triangleCoords = [
    new google.maps.LatLng(25.774252, -80.190262),
    new google.maps.LatLng(18.466465, -66.118292),
    new google.maps.LatLng(32.321384, -64.75737),
    new google.maps.LatLng(25.774252, -80.190262)
  ];

  // Construct the polygon.
  bermudaTriangle = new google.maps.Polygon({
    paths: triangleCoords,
    strokeColor: '#FF0000',
    strokeOpacity: 0.8,
    strokeWeight: 2,
    fillColor: '#FF0000',
    fillOpacity: 0.35
  });

  bermudaTriangle.setMap(map);
*/
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
