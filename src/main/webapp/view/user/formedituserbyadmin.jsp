<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<script type="text/javascript" src="${path}/js/edituserbyadmin.js"></script>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/edituserbyadmin.css" rel="stylesheet"/> </head>

<body>
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
	<div class="container">
		<h2 class="h2 page-header">Edit profile</h2>
		<form:form action="${path}/user/edituserbyadmin" modelAttribute="user" method="POST">
			<input type="text" name="userId" value="${user.userId}" hidden="true" />
			<div class="row">
				<div class="col-md-3">
					<p>
						<label for="firstName">First name</label>
					</p>
					<p>
						<form:input path="firstName" class="form-control" value="${user.firstName}" />
					</p>
				</div>
				<div class="col-md-3">
					<p>
						<label for="lastName">Last name</label>
					</p>
					<p>
						<form:input path="lastName" class="form-control" value="${user.lastName}" />
					</p>
				</div>
				<div class="col-md-3">
					<p>
						<label for="middleName">Middle name</label>
					</p>
					<p>
						<form:input path="middleName" class="form-control" value="${user.middleName}" />
					</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-5">
					<p>
						<label for="email">Email</label>
					</p>
					<p>
						<form:input path="email" class="form-control" value="${user.email}" />
					</p>
					<p class="error">
						<form:errors path="email" /> </p>
				</div>
				<div class="col-md-5">
					<p>
						<label for="phone">Phone Number</label>
					</p>
					<p>
						<form:input path="phoneNumber" class="form-control" value="${user.phoneNumber}" />
					</p>
					<p class="error">
						<form:errors path="phoneNumber" /> </p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-3">
					<p>
						<label for="username">Username</label>
					</p>
					<p>
						<form:input path="username" class="form-control" value="${user.username}" />
					</p>
					<p class="error">
						<form:errors path="username" /> </p>
				</div>
				<div class="col-md-1">
					<c:choose>
						<c:when test="${not empty superUser}">
							<c:if test="${not empty admin}">
								<p>
									<label for="role">Administrator</label>
								</p>
								<input type="checkbox" class="form-control" name="role" value="ROLE_ADMIN" checked/>
								<br>
								<br> </c:if>
							<c:if test="${empty admin}">
								<p>
									<label for="role">Administrator</label>
								</p>
								<input type="checkbox" class="form-control" name="role" value="ROLE_ADMIN" />
								<br>
								<br> </c:if>
						</c:when>
					</c:choose>
				</div>
			</div>
			<c:if test="${not empty error}">
				<p class="error">${error}</p>
			</c:if>
			<div id="passwordItems"> </div>
			<div id="but">
				<p>
					<button type="button" class="btn btn-primary" onclick="addDropPasswordCheckbox()">Drop password</button>
				</p>
			</div>
			<p>
				<button type="submit" class="btn btn-success btn-lg btn-block">Edit</button>
			</p>
		</form:form>
	</div>
	<footer class="my-5 pt-5 text-muted text-center text-small">
		<br>
		<p class="mb-1">© 2018 Library</p>
		<ul class="list-inline">
			<li class="list-inline-item"><a href="${path}/console">H2 Console</a></li>
		</ul>
	</footer>
	<script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>