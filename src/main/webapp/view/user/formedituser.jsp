<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <script type="text/javascript" src="${path}/js/edituser.js"></script>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
        <link href="${path}/css/showbooks.css" rel="stylesheet">
    </head>
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
            <h2 class="h2 page-header">Edit Your Profile</h2>

        <form:form action="${path}/user/edituserprofile" modelAttribute="user" method="POST">
            <label for="firstName">First name</label>
            <form:input path="firstName" class="form-control" value="${user.firstName}"/><br>

            <label for="lastName">Last name</label>
            <form:input path="lastName" class="form-control" value="${user.lastName}"/><br>

            <label for="middleName">Middle name</label>
            <form:input path="middleName" class="form-control" value="${user.middleName}"/><br>

            <label for="email">Email</label>
            <form:input path="email" class="form-control" value="${user.email}"/>
            <form:errors path="email"/><br>

            <label for="phone">Phone Number</label>
            <form:input path="phoneNumber" class="form-control" value="${user.phoneNumber}"/>
            <form:errors path="phoneNumber"/><br>

            <label for="username">Username</label>
            <form:input path="username" class="form-control" value="${user.username}"/>
            <form:errors path="username"/><br>


            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <div id="passwordItems">

            </div>

            <div id="but">

                <button type="button" class="btn btn-primary" onclick="addUserChangePasswordForm()">Change Password</button>

            </div>

            <br>

            <button type="submit" class="btn btn-success btn-lg btn-block">Edit</button>

        </div>

        </form:form>

            <footer class="my-5 pt-5 text-muted text-center text-small">
                <br>
                <p class="mb-1">© 2018 Library</p>
                <ul class="list-inline">
                    <li class="list-inline-item"><a href="${path}/console">H2 Console</a></li>
                </ul>
            </footer>

    </body>
</html>