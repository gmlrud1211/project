<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- JSTL 라이브러리 설정 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KOREA TOUR</title>
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


</head>
<body>

	<%
		String msg = request.getParameter("msg");
		if (msg != null) {
			if (msg.equals("success")) // 메세지가 null값이 아닐때
			{
				response.setContentType("text/html;charset=euc-kr");
				out.println("<script>");
				out.println("alert('로그인에 성공하였습니다');");
				out.println("</script>");
			}
		}
	%>

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
					<a href="Index.jsp">KOREA TOUR</a>
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
						<li><a href="Member.do?cmd=Logout">로그아웃</a></li>
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
	<div class=" header-right">
		<div class=" banner">
			<div class="slider">
				<div class="callbacks_container">
					<ul class="rslides" id="slider">
						<li>
							<div class="banner1">
								<div class="caption">
									<h3>
										<span>BEST PLACES</span>
									</h3>
									<p>Vsit best places in Korea</p>
								</div>
							</div>
						</li>
						<li>
							<div class="banner2">
								<div class="caption">
									<h3>
										<span>BEST RESTAURANT</span>
									</h3>
									<p>Vsit best restaurant in Korea</p>
								</div>
							</div>
						</li>
						<li>
							<div class="banner3">
								<div class="caption">
									<h3>
										<span>BEST HOTELS</span>
									</h3>
									<p>Vsit best hotels in Korea</p>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<!--header-bottom-->
	<!--//-->
	<div class="banner-bottom-bottom">
		<div class="resp-tabs-container">
			<h2 class="resp-accordion resp-tab-active" role="tab"
				aria-controls="tab_item-0">
				<span class="resp-arrow"></span>All Homes
			</h2>
			<div class="tab-1 resp-tab-content resp-tab-content-active"
				aria-labelledby="tab_item-0" style="display: block">
				<div class="facts">
					<div class="login">
						<form action="Search.do?cmd=searching" method="post">
							<input type="text" name="keyword" value="원하는 관광지를 입력하세요"
								onfocus="this.value = '';"
								onblur="if (this.value == '') {this.value = '주소, 도시, 업종을 입력하세요';}">
							<input type="submit" value="">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!--//header-bottom-->


	<!--//header-->
	<!--content-->
	<div class="content">
		<!--  로그인 되었을때 사는곳 주변 관광지 -->
		<c:if test="${not empty sessionScope.cityList}">
			<!-- 검색 결과가 있을때만 표시 한다. -->
			<div class="content-grid">
				<div class="container">
					<!-- 검색 결과 표시지점 -->

					<h3>City List</h3>

					<c:forEach var="vo" items="${sessionScope.cityList}">
						<div class="col-md-4 box_2">
							<a href=Search.do?cmd=searchinfo&contentid=${vo.contentid}
								class="mask"> <img class="img-responsive zoom-img"
								src="${vo.firstimage }" width=420 height=316 alt="">
							</a>
							<div class="most-1">
								<h5>${vo.title }</h5>
								<p>${vo.address }</p>
							</div>
						</div>


					</c:forEach>

					<div class="clearfix"></div>
				</div>
				<!-- 검색 결과 container end -->
			</div>
		</c:if>

		<!-- 로그인 되었을때 취미를 통한 검색  -->

		<c:if test="${not empty sessionScope.hobbyList}">
			<div class="content-grid">
				<div class="container">
					<!-- 검색 결과 표시지점 -->

					<h3>hobby List</h3>

					<c:forEach var="vo" items="${sessionScope.hobbyList}">
						<div class="col-md-4 box_2">
							<a href=Search.do?cmd=searchinfo&contentid=${vo.contentid}
								class="mask"> <img class="img-responsive zoom-img"
								src="${vo.firstimage }" width=420 height=316 alt="">
							</a>
							<div class="most-1">
								<h5>${vo.title }</h5>
								<p>${vo.address }</p>
							</div>
						</div>


					</c:forEach>

					<div class="clearfix"></div>
				</div>
				<!-- 검색 결과 container end -->
			</div>
		</c:if>

		<c:if test="${not empty sessionScope.resultTour }">
			<!-- 검색 결과가 있을때만 표시 한다. -->
			<div class="content-grid">
				<div class="container">
					<!-- 검색 결과 표시지점 -->

					<h3>Recommend List</h3>

					<c:forEach var="vo" items="${sessionScope.resultTour}">
						<div class="col-md-4 box_2">
							<a href=Search.do?cmd=searchinfo&contentid=${vo.contentid}
								class="mask"> <img class="img-responsive zoom-img"
								src="${vo.firstimage }" width=420 height=316 alt="">
							</a>
							<div class="most-1">
								<h5>${vo.title }</h5>
								<p>${vo.address }</p>
							</div>
						</div>


					</c:forEach>

					<div class="clearfix"></div>
				</div>
				<!-- 검색 결과 container end -->
			</div>
		</c:if>

		<!--phone-->
		<div class="phone">
			<div class="container">
				<div class="col-md-6">
					<img src="images/ph1.png" class="img-responsive" alt="" />
				</div>
				<div class="col-md-6 phone-text">
					<h4>Search Places Around My location</h4>

					<div class="text-1">
						<h5>Map Search</h5>
						<p>There are many variations of places around your location</p>
					</div>
					<div class="text-1">
						<h5>Quick Details</h5>
						<p>There are many information of places around your location</p>
					</div>
					<a href="./NearLocation.jsp" class="hvr-sweep-to-right more">Search!</a>
				</div>
			</div>
		</div>
		<!--//phone-->
		<!--project--->
		<div class="project"></div>
		<!--//project-->
		<!--test-->
		<!--//test-->
		<!--partners-->
		<!--//partners-->
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
					<p>2015 Korea Tour. All Rights Reserved | Design by songyi</p>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!--//footer-->
</body>
</html>