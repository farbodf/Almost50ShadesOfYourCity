$(document).ready(function() {

    // myLatlng is the coordination center that we like to show on the map (Focus)
    var myLatlng = new google.maps.LatLng(60.17, 24.93);
    var geocoder;
    city = "Helsinki"
    var map;
    document.getElementById("app-location").addEventListener("change", search);
	
var markersArray = [];
function createMarker(x, y, label) {
	var marker = new google.maps.Marker({
            position: new google.maps.LatLng(y, x),
            map: map,
            //animation: google.maps.Animation.BOUNCE,
            title: label,
            icon: 'GoogleMapsMarkers/' + label + '.png'
        });
	markersArray.push(marker);
}

function clearMarkers() {
	for (var i = 0; i < markersArray.length; i++ ) {
		markersArray[i].setMap(null);
	}
	markersArray = [];
}

    function clusterCall() {
        // send the new bounds back to your server
        bounds = map.getBounds();
        console.log(bounds);
        $.post("http://localhost:8000/test",
            {
	      "mozSystem": true,
              "bly": bounds['Ca']['k'],
              "try": bounds['Ca']['j'],
              "blx":bounds['va']['j'],
              "trx":bounds['va']['k'],
	      "zoom": map.getZoom()
            },
            function(data, status){
		clearMarkers();
		json = $.parseJSON(data);
		jQuery.each(json, function() {
			var label = this['label'];
  			jQuery.each(this['points'], function() {
				createMarker(this['x'], this['y'], label);
			});
		});
              	console.log(data);
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
google.maps.event.addListener(map, "projection_changed", clusterCall);
//google.maps.event.addListener(map, "center_changed", clusterCall);

        var tempPolygon = [];
        var polygon = [{
            'label': 1,
            'points': [{
                'y': 60.173151,
                'x': 24.935794
            }, {
                'y': 60.173279,
                'x': 24.946093
            }, {
                'y': 60.169810,
                'x': 24.941780
            }]
        }];
        var poly;
        for (i = 0; i < polygon.length; i++) {
            for (j = 0; j < polygon[i].points.length; j++) {
                tempPolygon.push(new google.maps.LatLng(polygon[i].points[j].y, polygon[i].points[j].x));
            }
            tempPolygon.push(new google.maps.LatLng(polygon[i].points[0].y, polygon[i].points[0].x));
        }
	/*
        poly = new google.maps.Polygon({
            paths: tempPolygon,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35
        });
        poly.setMap(map);*/



    }

    function search() {
        city = document.getElementById("app-location").value;
        codeAddress();
	
    }


    function codeAddress() {
        console.log(map);
        //var address = document.getElementById("address").value;
        geocoder.geocode({
            'address': city
        }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (city) {
                    map.setCenter(results[0].geometry.location);
			clusterCall();
                }
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
