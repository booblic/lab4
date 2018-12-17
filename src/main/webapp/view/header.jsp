 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/tablestyle.css" rel="stylesheet"/> </head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> <span class="sr-only">Toggle navigation</span> </button>
				<a class="navbar-brand" href="${path}/"> <span class="glyphicon glyphicon-book"></span> Почитай-ка </a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<c:choose>
						<c:when test="${not empty username}">
						    <c:choose>
						        <c:when test="${not empty subscribed}">
							        <li><a href="${path}/user/showuserprofile" style="color: #008000">${username}(Подписан)</a></li>
							    </c:when>
							    <c:when test="${empty subscribed}">
							        <li><a href="${path}/user/showuserprofile" style="color: #dc143c">${username}(Подписка отсутствует)</a></li>
							    </c:when>
							</c:choose>
							<c:if test="${role eq 'admin'}">
								<li><a href="${path}/user/getshowalluserform">Управление пользвателями</a></li>
							</c:if>
							<security:authorize access="hasRole('ROLE_SALES')">
								<li><a href="${path}/gettopform">Популярность ресурса</a></li>
						    </security:authorize>
							<li><a href="${path}/logout">Выйти</a></li>
							<br>
					    </c:when>
					</c:choose>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>