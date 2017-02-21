<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- JSTL 라이브러리 설정 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.wrap {
	position: absolute;
	left: 0;
	bottom: 40px;
	width: 288px;
	height: 132px;
	margin-left: -144px;
	text-align: left;
	overflow: hidden;
	font-size: 12px;
	font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
	line-height: 1.5;
}

.wrap * {
	padding: 0;
	margin: 0;
}

.wrap .info {
	width: 286px;
	height: 120px;
	border-radius: 5px;
	border-bottom: 2px solid #ccc;
	border-right: 1px solid #ccc;
	overflow: hidden;
	background: #fff;
}

.wrap .info:nth-child(1) {
	border: 0;
	box-shadow: 0px 1px 2px #888;
}

.info .title {
	overflow: visible;
	text-overflow: ellipsis;
	padding: 5px 0 0 10px;
	height: 30px;
	background: #eee;
	border-bottom: 1px solid #ddd;
	font-size: 18px;
	font-weight: bold;
}

.info .close {
	position: absolute;
	top: 10px;
	right: 10px;
	color: #888;
	width: 17px;
	height: 17px;
	background:
		url('http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}

.info .close:hover {
	cursor: pointer;
}

.info .body {
	position: relative;
	overflow: hidden;
}

.info .desc {
	position: relative;
	margin: 13px 0 0 90px;
	height: 75px;
}

.desc .ellipsis {
	white-space: nowrap;
	overflow: visible;
	text-overflow: ellipsis;
}

.desc .jibun {
	font-size: 11px;
	color: #888;
	margin-top: -2px;
}

.info .img {
	position: absolute;
	top: 6px;
	left: 5px;
	width: 73px;
	height: 71px;
	border: 1px solid #ddd;
	color: #888;
	overflow: hidden;
}

.info:after {
	content: '';
	position: absolute;
	margin-left: -12px;
	left: 50%;
	bottom: 0;
	width: 22px;
	height: 12px;
	background:
		url('http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')
}

.info .link {
	color: #5085BB;
}
</style>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Real Home Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 

</script>

</head>
<body>

	<!--header-->
	<div class="navigation">
		<div class="container-fluid">
			<nav class="pull">
			<ul>
				<li><a href="Index.jsp">Home</a></li>
				<li><a href="about.html">About Us</a></li>
				<li><a href="blog.html">Blog</a></li>
				<li><a href="terms.html">Terms</a></li>
				<li><a href="privacy.html">Privacy</a></li>
				<li><a href="contact.html">Contact</a></li>
			</ul>
			</nav>
		</div>
	</div>

	<div class="header">
		<div class="container">
			<!--logo-->
			<div class="logo">
				<h1>
					<a href="Index.jsp">Travel Planner</a>
				</h1>
			</div>
			<!--//logo-->
			<div class="top-nav">
				<c:if test="${empty sessionScope.member}">
					<!-- 로그인 표시 -->
					<ul class="right-icons">
						<li><a href="Login.jsp"><i
								class="glyphicon glyphicon-user"> </i>Login</a></li>
					</ul>
				</c:if>

				<c:if test="${not empty sessionScope.member}">
					<!-- 로그인 표시 -->
					<ul class="right-icons">
						<li><a href="Login.jsp"><i
								class="glyphicon glyphicon-user"> </i>${sessionScope.member.email}
						</a></li>
						<li><a href="Member.do?cmd=Logout">Logout</a></li>
					</ul>
				</c:if>
				<div class="clearfix"></div>
				<!---pop-up-box---->

				<link href="css/popuo-box.css" rel="stylesheet" type="text/css"
					media="all" />
				<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
				<!---//pop-up-box---->
				<div id="small-dialog" class="mfp-hide">
					<!----- tabs-box ---->
					<div class="sap_tabs">
						<div id="horizontalTab"
							style="display: block; width: 100%; margin: 0px;">
							<ul class="resp-tabs-list">
								<li class="resp-tab-item " aria-controls="tab_item-0" role="tab"><span>All
										Homes</span></li>
								<li class="resp-tab-item" aria-controls="tab_item-1" role="tab"><span>For
										Sale</span></li>
								<li class="resp-tab-item" aria-controls="tab_item-2" role="tab"><span>For
										Rent</span></li>
								<div class="clearfix"></div>
							</ul>
							<div class="resp-tabs-container">
								<h2 class="resp-accordion resp-tab-active" role="tab"
									aria-controls="tab_item-0">
									<span class="resp-arrow"></span>All Homes
								</h2>
								<div class="tab-1 resp-tab-content resp-tab-content-active"
									aria-labelledby="tab_item-0" style="display: block">
									<div class="facts">
										<div class="login">
											<input type="text"
												value="Search Address, Neighborhood, City or Zip"
												onfocus="this.value = '';"
												onblur="if (this.value == '') {this.value = 'Search Address, Neighborhood, City or Zip';}">
											<input type="submit" value="">
										</div>
									</div>
								</div>
								<h2 class="resp-accordion" role="tab" aria-controls="tab_item-1">
									<span class="resp-arrow"></span>For Sale
								</h2>
								<div class="tab-1 resp-tab-content" aria-labelledby="tab_item-1">
									<div class="facts">
										<div class="login">
											<input type="text"
												value="Search Address, Neighborhood, City or Zip"
												onfocus="this.value = '';"
												onblur="if (this.value == '') {this.value = 'Search Address, Neighborhood, City or Zip';}">
											<input type="submit" value="">
										</div>
									</div>
								</div>
								<h2 class="resp-accordion" role="tab" aria-controls="tab_item-2">
									<span class="resp-arrow"></span>For Rent
								</h2>
								<div class="tab-1 resp-tab-content" aria-labelledby="tab_item-2">
									<div class="facts">
										<div class="login">
											<input type="text"
												value="Search Address, Neighborhood, City or Zip"
												onfocus="this.value = '';"
												onblur="if (this.value == '') {this.value = 'Search Address, Neighborhood, City or Zip';}">
											<input type="submit" value="">
										</div>
									</div>
								</div>
							</div>
						</div>
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
		</div>
	</div>
	<!--//-->
	<div class=" banner-buying">
		<div class=" container">
			<h3>
				<span>tour info</span>
			</h3>
			<!---->
			<div class="menu-right">

			</div>
			<div class="clearfix"></div>
			<!--initiate accordion-->
			<script type="text/javascript">
				$(function() {
					var menu_ul = $('.menu > li > ul'), menu_a = $('.menu > li > a');
					menu_ul.hide();
					menu_a.click(function(e) {
						e.preventDefault();
						if (!$(this).hasClass('active')) {
							menu_a.removeClass('active');
							menu_ul.filter(':visible').slideUp('normal');
							$(this).addClass('active').next().stop(true, true)
									.slideDown('normal');
						} else {
							$(this).removeClass('active');
							$(this).next().stop(true, true).slideUp('normal');
						}
					});

				});
			</script>

		</div>
	</div>
	<!--//header-->
	<!---->
<c:if test="${not empty requestScope.tourinfo }">
	<div class="container">

		<div class="buy-single-single">

			<div class="col-md-9 single-box">

				<div class=" buying-top">
					<!-- 슬라이더 설정 하는 부분 (썸네일) -->

					<c:if test="${not empty requestScope.tourinfo.imageData }">
						<div class="flexslider">
							<ul class="slides">
								<c:forEach var="vo" items="${requestScope.tourinfo.imageData}">
									<li data-thumb="${vo.smallimageurl}"><img
										src="${vo.originimgurl}" /></li>

								</c:forEach>
							</ul>
						</div>
					</c:if>
				
					<!-- FlexSlider -->
					<script defer src="js/jquery.flexslider.js"></script>
					<link rel="stylesheet" href="css/flexslider.css" type="text/css"
						media="screen" />

					<script>
						// Can also be used with $(document).ready()
						$(window).load(function() {
							$('.flexslider').flexslider({
								animation : "slide",
								controlNav : "thumbnails"
							});
						});
					</script>
				</div>
				<div class="buy-sin-single">
					<div class="col-sm-5 middle-side immediate">
						<h4>정보</h4>
						<p>
							<span class="bath">이름 </span><br>
							<span class="two">${requestScope.tourinfo.title}</span>
						</p>
						<p>
							<span class="bath1">주소 </span><br>
							<span class="two">${requestScope.tourinfo.address }</span>
						</p>
						<p>
							<span class="bath2">상세주소</span><br>
							<span class="two">${requestScope.tourinfo.address2 }</span>
						</p>
						<p>
							<span class="bath3">전화명</span><br>
							<span class="two">${requestScope.tourinfo.telname }</span>
						</p>
						<p>
							<span class="bath4">전화번호</span><br>
							<span class="two">${requestScope.tourinfo.tel }</span>
						</p>
						<p>
							<span class="bath5">홈페이지 </span><br>
							<span class="two">${requestScope.tourinfo.homepage }</span>
						</p>

					</div>
					<div class="col-sm-7 buy-sin">
						<h4>Over View</h4>
						<p>${requestScope.tourinfo.overview}</p>
						
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="map-buy-single">
					<h4>Map Information</h4>

					<!-- 지도를 표시할 div 입니다 -->
							<div class="map-buy-single">
							
									<div id="map" style="width: 100%; height: 400px;"></div>
							
							<script type="text/javascript"
								src="//apis.daum.net/maps/maps3.js?apikey=950c9c587d098e4789f5467b057b1da7"></script>
						
							<script>
								var title = "${requestScope.tourinfo.title}";
								var address = "${requestScope.tourinfo.address}";
								var image = "${requestScope.tourinfo.firstimage}";
								var mapx = "${requestScope.tourinfo.mapx}";
								var mapy = "${requestScope.tourinfo.mapy}";
								var address2 = "${requestScope.tourinfo.address2}";

								var mapContainer = document
										.getElementById('map'), // 지도를 표시할 div 
								mapOption = {
									center : new daum.maps.LatLng(mapy, mapx), // 지도의 중심좌표
									level : 3
								// 지도의 확대 레벨
								};

								// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
								var map = new daum.maps.Map(mapContainer,
										mapOption);

								//마커가 표시될 위치입니다 
								var markerPosition = new daum.maps.LatLng(mapy,
										mapx);

								// 마커를 생성합니다
								var marker = new daum.maps.Marker({
									position : markerPosition
								});

								marker.setMap(map);

								// 커스텀 오버레이에 표시할 컨텐츠 입니다
								// 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
								// 별도의 이벤트 메소드를 제공하지 않습니다 
								var content = '<div class="wrap">'
										+ '    <div class="info">'
										+ '        <div class="title">'
										+ title
										+ '            <div class="close" onclick="closeOverlay()" title="닫기"></div>'
										+ '        </div>'
										+ '        <div class="body">'
										+ '            <div class="img">'
										+ '                <img src="'+image+'" width="73" height="70">'
										+ '           </div>'
										+ '            <div class="desc">'
										+ '                <div class="ellipsis">'
										+ address + '</div>'
										+ '                <div>' + address2
										+ '</div>' + '            </div>'
										+ '        </div>' + '    </div>'
										+ '</div>';

								// 마커 위에 커스텀오버레이를 표시합니다
								// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
								var overlay = new daum.maps.CustomOverlay({
									content : content,
									map : map,
									position : marker.getPosition()
								});

								// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
								daum.maps.event.addListener(marker, 'click',
										function() {
											overlay.setMap(map);
										});

								// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
								function closeOverlay() {
									overlay.setMap(null);
								}
							</script>
						</div>
					</c:if>
				</div>
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