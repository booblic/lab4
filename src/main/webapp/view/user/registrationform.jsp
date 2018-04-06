<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/registration.css" rel="stylesheet"/> </head>

<body>
	<div class="container">
		<h3 class="my_h3">Registration</h3>
		<br>
		<form:form action="${path}/user/registeruser" modelAttribute="user" method="POST">
			<label for="firstName">First name</label>
			<form:input path="firstName" class="form-control" />
			<br>
			<label for="lastName">Last name</label>
			<form:input path="lastName" class="form-control" />
			<br>
			<label for="middleName">Middle name</label>
			<form:input path="middleName" class="form-control" />
			<br>
			<label for="email">Email</label>
			<form:input path="email" class="form-control" placeholder="example@gamil.com" />
			<p class="error">
				<form:errors path="email" />
			</p>
			<br>
			<label for="phone">Phone Number</label>
			<form:input path="phoneNumber" class="form-control" placeholder="9271234567" />
			<p class="error">
				<form:errors path="phoneNumber" />
			</p>
			<br>
			<label for="username">Username</label>
			<form:input path="username" class="form-control" />
			<p class="error">
				<form:errors path="username" />
			</p>
			<c:if test="${not empty InvalidUsername}">
				<p class="error">${InvalidUsername}</p>
			</c:if>
			<br>
			<label for="password">Password</label>
			<form:input type="password" path="password" class="form-control" />
			<p class="error">
				<form:errors path="password" />
			</p>
			<br>
			<label for="confirmedPassword">Confirmed Password</label>
			<form:input type="password" path="confirmedPassword" class="form-control" />
			<c:if test="${not empty invalidPassword}">
				    <p class="error">${invalidPassword}</p>
			</c:if>
			<br>
			<button class="btn btn-primary btn-lg btn-block" type="submit">Registration</button>
		</form:form>
		<footer class="my-5 pt-5 text-muted text-center text-small">
			<br>
			<p class="mb-1">© 2017-2018 Library</p>
			<ul class="list-inline">
				<li class="list-inline-item"><a href="${path}/console">H2 Console</a></li>
			</ul>
		</footer>
	</div>
	<script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>