<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Marker</title>
        <script src="https://apis.skplanetx.com/tmap/js?version=1&format=javascript&appKey=2cfca2bc-7f91-3031-b69d-3c7eed12970c"></script>
        <script type="text/javascript">
        
        
        document.onkeypress= removeMarker;
        var map;
        var markers;
            
            function init() 
            {	
                map = new Tmap.Map({div:'map_div', width:'500px', height:'500px', transitionEffect:"resize"});
                map.setCenter(new Tmap.LonLat(14135893.887852,4518348.1852606), 14);

                markers = new Tmap.Layer.Markers("MarkerLayer");
                map.addLayer(markers);
                
                map.events.register("click", map, addMarker);
            } 
            
            function addMarker(evt)
            {
                var size = new Tmap.Size(12,19);
                var offset = new Tmap.Pixel(-(size.w/2), -(size.h));
                var icon = new Tmap.Icon("https://developers.skplanetx.com/upload/tmap/marker/pin_b_s_simple.png", size, offset);
                var pix = new Tmap.Pixel(evt.clientX, evt.clientY);
                var lonlat = map.getLonLatFromPixel(pix);
            	markers.addMarker(new Tmap.Marker(new Tmap.LonLat(lonlat.lon, lonlat.lat), icon));
            	document.getElementById("demo1").innerHTML=lonlat;
                console.log(evt.clientX);
                console.log(evt.clientY);
            	console.log(lonlat);            	
                console.log(lonlat.lon);
                console.log(lonlat.lat);
            }
            
            function removeMarker(evt)
            {
                var key;
                if(window.event)
                  key = window.event.keyCode
                else
                  key = e.which     //firefox를 위해 
                if(key == 27)
                {
                	markers.clearMarkers();
                	windows.alert("ESC!");
                }
            } 
            
            window.onload = function() {
            	init();
            }
        </script>
    </head>
    <body>
        <div id="map_div">
        </div>    
        경위도 : <p id="demo1"></p><br><br>
    </body>
</html>
            