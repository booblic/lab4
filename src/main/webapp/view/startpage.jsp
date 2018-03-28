<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>

	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<%--<link rel="stylesheet" href="/css/test.css" />--%>
</head>

<body style="padding-top: 70px;">

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
						data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${path}/">
                    <span class="glyphicon glyphicon-book"></span> Library
                </a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="${path}/user/getregistrationform">Registration</a></li>
					<li><a href="${path}/console">H2 Console</a></li>
				</ul>
			</div><!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">
		<div>
			<h1>Welcome to Library!</h1>

			<p>${message}</p>
			<p>${registrationMessage}</p>
			<p>${logoutMessage}</p>

			<c:choose>
				<c:when test="${empty logout}">
					<p><a class="btn btn-success" href="${path}/user/login">Sign In</a></p>
					<br>
				</c:when>
				<c:when test="${not empty logout}">
					<p><a href="${path}/user/showuserprofile">Show Your Profile</a></p>
					<c:if test="${not empty admin}">
						<p><a href="${path}/user/getshowalluserform">Show All User</a></p>
					</c:if>
					<p><a href="${path}/logout">Logout</a></p>
					<br>
					<br>
					<p><a href="${path}/book/show">Show Books</a></p>
					<p><a href="${path}/searchbookoptions">Searching Books</a></p>
					<br>
				</c:when>
			</c:choose>

		</div>
	</div><!-- /.container -->

    <script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>