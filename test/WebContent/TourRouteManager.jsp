<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- JSTL 라이브러리 설정 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Travel Planner</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--menu-->
<script src="js/scripts.js"></script>
<link href="css/styles.css" rel="stylesheet">
<!--//menu-->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords"
	content="Real Home Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<!-- slide -->
<script src="js/responsiveslides.min.js"></script>
<script>
	$(function() {
		$("#slider").responsiveSlides({
			auto : true,
			speed : 500,
			namespace : "callbacks",
			pager : true,
		});
	});
</script>
<style type="text/css">
img{border:0;}
#logoUI{width:100px; height:100px; margin:3px auto; border:0px solid;}
</style>


<!-- 지도 부분 -->
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
            var size = new Tmap.Size(24,28);
           	var offset = new Tamp.Pixel(-(size.w/4), -(size.h));
           	var icon = new Tmap.Icon('https://developers.skplanetx.com/upload/tmap/marker/pin_b_m_a.png', size, offset);
           
           	var maker = new Tmpap.Maker(lonlat, icon);
           	markerLaye.addMaker(marker);
           	
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

	<!--header-->
	<div class="header">
		<div class="container">
			<!--logo-->
			<div class="logo">
				<h1 id="logoUI"><img src="images/logoUI.png" width="100"height="100"/> </h1>
			</div>
			<div class="logo">
				<h1> <a href="Index.jsp">Travel Planner</a> </h1>
			</div>
			
				<c:if test="${not empty sessionScope.member}">
					<!-- 로그인 표시 -->
					<ul class="right-icons">
						<li><a href="Login.jsp"><i class="glyphicon glyphicon-user"> </i>${sessionScope.member.usr_id}님 환영합니다! </a></li>
						<li><a href="Member.do?cmd=Logout"> Logout </a></li>
						<li><a href="MyPlan.jsp">MyPage</a></li>
					</ul>
				</c:if>
				
				<!---pop-up-box---->

				<link href="css/popuo-box.css" rel="stylesheet" type="text/css" smedia="all" />
				<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
				<!---//pop-up-box---->
				<div id="small-dialog" class="mfp-hide">
						<script src="js/easyResponsiveTabs.js" type="text/javascript"></script>
						<script type="text/javascript">
							$(document).ready(function() {
								$('#horizontalTab').easyResponsiveTabs({
									type : 'default', //Types: default, vertical, accordion           
									width : 'auto', //auto or any width like 600px
									fit : true
								// 100% fit in a container
								});
							});
						</script>
					</div>
				</div>
				<script>
					$(document).ready(function() {
						$('.popup-with-zoom-anim').magnificPopup({
							type : 'inline',
							fixedContentPos : false,
							fixedBgPos : true,
							overflowY : 'auto',
							closeBtnInside : true,
							preloader : false,
							midClick : true,
							removalDelay : 300,
							mainClass : 'my-mfp-zoom-in'
						});

					});
				</script>
			</div>
			<div class="clearfix"></div>




<!--  header-bottom-->
	<div class = "container-fluid">
		<div class ="row-fluid">
			<div id = "sidebar">
			<ul>
				<li><a href="Index.jsp">홈</a></li>
				<li><a href="MyPlan.jsp">내 여행계획</a></li>
				<li><a href="BillManager.jsp">지출 관리</a></li>
				<li><a href="ReviewManager.jsp">리뷰</a></li>
				<li><a href="#">마이 페이지</a></li>
				<li><a href="#">설정</a></li>
			</ul>
			</div>
			<div id = "map_div">
				<input type="button" id="button1" onclick="searchRoute();" value="길찾기"/>	
			</div>
		</div>
	</div>
	
		
	
	
	<!--footer-->
	<div class="footer">
		<div class="footer-bottom">
			<div class="container">
				<div class="col-md-4 footer-logo">
					<h2>
						<a href="Index.jsp">KOREA TOUR</a>
					</h2>
				</div>
				<div class="col-md-8 footer-class">
					<p>2017 Travel Planner. All Rights Reserved | Design by heekyoung Shin & moonhee Kim</p>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!--//footer-->
</body>
</html>