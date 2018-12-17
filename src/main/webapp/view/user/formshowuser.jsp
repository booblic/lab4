<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/commonstyle.css" rel="stylesheet"/> </head>

<body>
    <jsp:include page="${path}/view/header.jsp"/>

	<div class="container">
		<h2 class="h2 page-header">Ваш профиль</h2>
		<p>Имя: ${user.firstName}</p>
		<p>Фамилия: ${user.lastName}</p>
		<c:if test="${not empty user.middleName}">
		    <p>Отчество: ${user.middleName}</p>
        </c:if>
        <p>Почта: ${user.email}</p>
		<p>Телефон: ${user.phoneNumber}</p>
		<p>Логин: ${user.username}</p>
		<p>Подписка оформлена: ${user.subscription} (Осталось дней: ${daysLeft})</p>
		<p><a href="${path}/user/getedituserform" class="btn btn-primary">Edit profile</a></p>
	<div>
	<footer class="my-5 pt-5 text-muted text-center text-small">
		<br>
		<p class="mb-1">© 2018 Почитай-ка</p>
		<ul class="list-inline">
			<li class="list-inline-item"><a href="${path}/console">H2 Console</a></li>
		</ul>
	</footer>
	<script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>