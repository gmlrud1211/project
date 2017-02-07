<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>find Path</title>
        <script src="https://apis.skplanetx.com/tmap/js?version=1&format=javascript&appKey=2cfca2bc-7f91-3031-b69d-3c7eed12970c"></script>
        <script type="text/javascript">

        document.onkeypress= removeMarker;
        var startX;
        var startY;
        var endX;
        var endY;
        var map;
        var routeLayer;
        
        //초기화 함수
        function initTmap(){
            centerLL = new Tmap.LonLat(14145677.4, 4511257.6);
            map = new Tmap.Map({div:'map_div',
                                width:'100%', 
                                height:'400px',
                                transitionEffect:"resize",
                                animation:true
                            });
            markers = new Tmap.Layer.Markers("MarkerLayer");
            map.addLayer(markers);
            
            map.events.register("click", map, addMarker);
            
            //searchRoute();
        };
        
        function addMarker(evt)
        {
        	if(startX!=null && startY!=null && endX!=null && endY!=null)
        		{
        			window.alert("선택 완료! 길찾기 버튼을 눌러주세요.");
        		}
        	var pix = new Tmap.Pixel(evt.clientX, evt.clientY);
        	var lonlat = map.getLonLatFromPixel(pix);
        	
        	if(startX==null || startY==null)
        		{
                    startX = lonlat.lon;
                    startY = lonlat.lat;
        		}
        	else
        		{
                	endX = lonlat.lon;
                	endY = lonlat.lat;
        		}
        }
        
        //경로 정보 로드
        function searchRoute(){
            var routeFormat = new Tmap.Format.KML({extractStyles:true, extractAttributes:true});
            var urlStr = "https://apis.skplanetx.com/tmap/routes?version=1&format=xml";
            urlStr += "&startX="+startX;
            urlStr += "&startY="+startY;
            urlStr += "&endX="+endX;
            urlStr += "&endY="+endY;
            urlStr += "&appKey="+"2cfca2bc-7f91-3031-b69d-3c7eed12970c";
            var prtcl = new Tmap.Protocol.HTTP({
                                                url: urlStr,
                                                format:routeFormat
                                                });
            routeLayer = new Tmap.Layer.Vector("route", {protocol:prtcl, strategies:[new Tmap.Strategy.Fixed()]});
            routeLayer.events.register("featuresadded", routeLayer, onDrawnFeatures);
            map.addLayer(routeLayer);
        }
        //경로 그리기 후 해당영역으로 줌
        function onDrawnFeatures(e){
            map.zoomToExtent(this.getDataExtent());
        }
        
        function removeMarker()
        {
            var key;
            if(window.event)
              key = window.event.keyCode
            else
              key = e.which     //firefox를 위해 
            if(key == 27)
            {
            	map.removeLayer(routeLayer, true);
            	startX = startY = endX = endY = null;
            	windows.alert("새로 선택하세요!");
            }
        } 
        </script>
    </head>
<body onload="initTmap()">
<div id="map_div"></div>
<input type="button" id="button1" onclick="searchRoute();" value="길찾기"/>
</body>
</html>
            