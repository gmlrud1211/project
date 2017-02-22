<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- JSTL 라이브러리 설정 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>환영합니다!</title>
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

	<%
		// 로그인 실패 메세지 체크
		String msg = request.getParameter("msg");

		if (msg != null) {
			if (msg.equals("fail")) // 메세지가 null값이 아닐때
			{
				response.setContentType("text/html;charset=euc-kr");
				out.println("<script>");
				out.println("alert('아이디 또는 비밀번호가 틀렸습니다.');");
				out.println("</script>");

			}
		}
	%>

	<!--header-->
	<div class="header">
		<div class="container">
			<!--logo-->
			<div class="logo">
				<h1 id="logoUI"><img src="images/logoUI.png" width="100"height="100"/> </h1> </div>
			<div class="logo"> <h1> <a href="Index.jsp">Travel Planner</a> </h1></div>
			
			<!--//logo-->
			<div class="top-nav">

				<c:if test="${not empty sessionScope.member}">
					<!-- 로그인 표시 -->
					<ul class="right-icons">
						<li><a href="Login.jsp"><i
								class="glyphicon glyphicon-user"> </i>${sessionScope.member.usr_id}님 환영합니다!
						</a></li>
						<li><a href="Member.do?cmd=Logout">로그아웃</a></li>
					</ul>
				</c:if>
				<div class="clearfix"></div>
				
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
				<span>Login</span>
			</h3>
			<!---->
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
	<!--contact-->
	<div class="login-right">
		<div class="container">
			<h3>Login</h3>
			<div class="login-top">
				<div class="form-info">
					<!--  로그인 처리 부분 -->

					<form action="Member.do?cmd=login" method="post">
						<input type="text" name="usr_id" class="text"
							placeholder="id" required=""> <input
							type="password" name="usr_pwd" placeholder="Password" required="">
						<label class="hvr-sweep-to-right"> <input type="submit"
							value="Submit">
						</label>
					</form>
				</div>
				<div class="create">
					<h4>새로운 계정을 생성 하시겠습니까?</h4>
					<a class="hvr-sweep-to-right" href="Register.jsp">Create an
						Account</a>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!--//contact-->
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