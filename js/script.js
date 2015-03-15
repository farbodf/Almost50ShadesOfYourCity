
$( document ).ready(function() {

       // myLatlng is the coordination center that we like to show on the map (Focus)
       var myLatlng = new google.maps.LatLng(60.17,24.93);
       var image = 'GoogleMapsMarkers/commercial-places.png';
       var geocoder;
       city = "Helsinki"
       var map;
       document.getElementById("app-location").addEventListener("change", search);


       function clusterCall() {
            // send the new bounds back to your server
            bounds = map.getBounds();
            console.log(bounds);
            $.post("http://localhost:8000/test",
            {
              "blx": bounds['Ca']['j'],
              "bly": bounds['Ca']['k'],
              "trx":bounds['va']['j'],
              "try":bounds['va']['k']
            },
            function(data, status){
              alert("Data: " + data + "\nStatus: " + status);
            });
          };

          function initialize() {
            geocoder = new google.maps.Geocoder();
            var mapOptions = {
              center: myLatlng,
              zoom: 12
            };
            map = new google.maps.Map(document.getElementById('map-canvas'),
              mapOptions);
            google.maps.event.addListener(map, "dragend", clusterCall);
            google.maps.event.addListener(map, "zoom_changed", clusterCall);

            var tempPolygon = [];
            var polygon = [{'label':1, 'points':[{'y':60.173151, 'x':24.935794},{'y':60.173279, 'x':24.946093},{'y':60.169810, 'x':24.941780}]}];
            var poly;
            for (i = 0; i < polygon.length; i++) {
              for(j = 0; j < polygon[i].points.length; j++){
                tempPolygon.push(new google.maps.LatLng(polygon[i].points[j].y, polygon[i].points[j].x));
              }
              tempPolygon.push(new google.maps.LatLng(polygon[i].points[0].y, polygon[i].points[0].x));
            }

            var marker = new google.maps.Marker({
              position: myLatlng,
              map: map,
              animation: google.maps.Animation.BOUNCE,
              title:"Hello World!",
              icon:image
            });
            poly = new google.maps.Polygon({
              paths: tempPolygon,
              strokeColor: '#FF0000',
              strokeOpacity: 0.8,
              strokeWeight: 2,
              fillColor: '#FF0000',
              fillOpacity: 0.35
            });
            poly.setMap(map);



          }
          function search(){
            city=document.getElementById("app-location").value;
            codeAddress();
          }


          function codeAddress() {
            console.log(map);
        //var address = document.getElementById("address").value;
        geocoder.geocode( { 'address': city}, function(results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            if(city){
              map.setCenter(results[0].geometry.location);}
            /*var newMarker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location,
                icon:'GoogleMapsMarkers/computers.png'
              });*/
      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
      }

      google.maps.event.addDomListener(window, 'load', initialize);




    });
