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

         

        var marker = new google.maps.Marker({
          position: myLatlng,
          map: map,
          animation: google.maps.Animation.BOUNCE,
          title:"Hello World!",
          icon:image
        });
        codeAddress();
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
