<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>simpleMap</title>
        <script src="https://apis.skplanetx.com/tmap/js?version=1&format=javascript&appKey=2cfca2bc-7f91-3031-b69d-3c7eed12970c"></script>
        <script type="text/javascript">
             
             
            function init() {
                var map = new Tmap.Map({div:'map_div', width:'500px', height:'500px'}); 
                // div : 지도가 생성될 div의 id값과 같은 값을 옵션으로 정의 합니다.
                // Tmap,Map 클래스에 대한 상세 사항은 "JavaScript" 하위메뉴인 "기본 기능" 페이지를 참조 해주세요. 
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
            