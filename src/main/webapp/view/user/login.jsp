<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/signin.css" rel="stylesheet"/> </head>

<body class="text-center">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        	<div class="container">
        		<div class="navbar-header">
        			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> <span class="sr-only">Toggle navigation</span> </button>
        			<a class="navbar-brand" href="${path}/"> <span class="glyphicon glyphicon-book"></span> Library </a>
        		</div>
        		<div id="navbar" class="collapse navbar-collapse">
        			<ul class="nav navbar-nav">
        				<c:choose>
        					<c:when test="${not empty username}">
       						<li><a href="${path}/user/showuserprofile">${username}</a></li>
       						<c:if test="${role eq 'admin'}">
       							<li><a href="${path}/user/getshowalluserform">Show all user</a></li>
       						</c:if>
        					<li><a href="${path}/logout">Logout</a></li>
        					<br> </c:when>
        				</c:choose>
        			</ul>
        	</div>
        	<!--/.nav-collapse -->
        </div>
    </nav>
	<form class="form-signin" action="${path}/user/login" method="POST">
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
		<label for="inputEmail" class="sr-only">Login</label>
		<input type="text" id="inputEmail" class="form-control" placeholder="Login" required="" autofocus="" name="username">
		<label for="inputPassword" class="sr-only">Password</label>
		<input type="password" id="inputPassword" class="form-control" placeholder="Password" required="" name="password">
		<c:if test="${not empty error}">
			<p class="error">${error}</p>
		</c:if>
		<button class="btn btn-success btn-lg btn-block" type="submit">Sign in</button> <a class="btn btn-primary btn-lg btn-block" href="${path}/user/getregistrationform">Registration</a> </form>
		<script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>