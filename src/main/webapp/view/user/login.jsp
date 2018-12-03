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
    <jsp:include page="${path}/view/header.jsp"/>

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