<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<script type="text/javascript" src="${path}/js/droppassword.js"></script>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/commonstyle.css" rel="stylesheet"/> </head>

<body>
    <jsp:include page="${path}/view/header.jsp"/>

	<div class="container">
		<h2 class="h2 page-header">Редактирование профиля</h2>
		<form:form action="${path}/user/edituserbyadmin" modelAttribute="user" method="POST">
			<input type="text" name="userId" value="${user.userId}" hidden="true" />
			<div class="row">
				<div class="col-md-3">
					<p>
						<label for="firstName">Имя</label>
					</p>
					<p>
						<form:input path="firstName" class="form-control" value="${user.firstName}" />
					</p>
				</div>
				<div class="col-md-3">
					<p>
						<label for="lastName">Фамилия</label>
					</p>
					<p>
						<form:input path="lastName" class="form-control" value="${user.lastName}" />
					</p>
				</div>
				<div class="col-md-3">
					<p>
						<label for="middleName">Отчество</label>
					</p>
					<p>
						<form:input path="middleName" class="form-control" value="${user.middleName}" />
					</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-5">
					<p>
						<label for="email">Почта</label>
					</p>
					<p>
						<form:input path="email" class="form-control" value="${user.email}" />
					</p>
					<p class="error">
						<form:errors path="email" /> </p>
				</div>
				<div class="col-md-5">
					<p>
						<label for="phone">Номер телефона</label>
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
						<label for="username">Логин</label>
					</p>
					<p>
						<form:input path="username" class="form-control" value="${user.username}" />
					</p>
					<p class="error">
						<form:errors path="username"/> </p>
				</div>
				<div class="col-md-1">
					<c:choose>
						<c:when test="${not empty admin}">
							<c:if test="${not empty moder}">
								<p>
									<label for="role">Модератор</label>
								</p>
								<input type="checkbox" class="form-control" name="moder" value="ROLE_MODER" checked/>
								<br>
								<br> </c:if>
							<c:if test="${empty moder}">
								<p>
									<label for="role">Модератор</label>
								</p>
								<input type="checkbox" class="form-control" name="moder" value="ROLE_MODER" />
						    </c:if>
						</c:when>
					</c:choose>
				</div>
				<div class="col-md-1">
                    <c:if test="${not empty sales}">
                        <p>
                            <label for="role">Маркетолог</label>
                        </p>
                        <input type="checkbox" class="form-control" name="sales" value="ROLE_SALES" checked/>
                        <br>
                        <br>
					</c:if>
					<c:if test="${empty sales}">
					    <p>
						    <label for="role">Маркетолог</label>
					    </p>
					    <input type="checkbox" class="form-control" name="sales" value="ROLE_SALES" />
					</c:if>
				</div>
			</div>
			<c:if test="${not empty error}">
				<p class="error">${error}</p>
			</c:if>
			<div id="passwordItems"> </div>
			<div id="but">
				<p>
					<button type="button" class="btn btn-primary" onclick="addDropPasswordCheckbox()">Сбросить пароль</button>
				</p>
			</div>
			<p>
				<button type="submit" class="btn btn-success btn-lg btn-block">Сохранить</button>
			</p>
		</form:form>
	</div>
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