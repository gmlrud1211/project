<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Marker</title>
        <script src="https://apis.skplanetx.com/tmap/js?version=1&format=javascript&appKey=2cfca2bc-7f91-3031-b69d-3c7eed12970c"></script>
        <script type="text/javascript">
        
            function init() {
               
                var map = new Tmap.Map({div:'map_div', width:'500px', height:'500px'});
                map.setCenter(new Tmap.LonLat(14135893.887852,4518348.1852606), 16);
                var markerLayer = new Tmap.Layer.Markers();
                map.addLayer(markerLayer);
                var lonlat = new Tmap.LonLat(14135893.887852, 4518348.1852606);
                var size = new Tmap.Size(24,38);
                var offset = new Tmap.Pixel(-(size.w/4), -(size.h));
                var icon = new Tmap.Icon('https://developers.skplanetx.com/upload/tmap/marker/pin_b_m_a.png', size, offset);
                
                var marker = new Tmap.Marker(lonlat, icon);
                markerLayer.addMarker(marker);
            } 
            
            window.onload = function() {
               init();
            }
        </script>
    </head>
    <body>
        <div id="map_div">
        </div>        
    </body>
</html>
            