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
	<link href="${path}/css/tablestyle.css" rel="stylesheet"/> </head>

<body>
    <jsp:include page="${path}/view/header.jsp"/>

	<div class="container">
        <h2 class="h2 page-header">Поиск пользователей</h2>
        <p><label for="Name">Введите логин пользователя:</label></p>
        <form action="${path}/user/searchingUserByLogin" method="GET">
            <p><input type="text" class="form-control" name="login" /></p>
            <button type="submit" class="btn btn-primary btn-lg">Поиск</button>
        </form>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <div class="row">
            <div class="table-responsive">
                <c:if test="${not empty users}">
                    <h2 class="h2 page-header">Все пользователи</h2>
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th>Логин</th>
                                <th>Имя</th>
                                <th>Фамилия</th>
                                <th>Отчество</th>
                                <th>Почта</th>
                                <th>Номер телефона</th>
                                <th>Роль</th>
                                <th>Редактировать</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.username}</td>
                                    <td>${user.firstName}</td>
                                    <td>${user.lastName}</td>
                                    <td>${user.middleName}</td>
                                    <td>${user.email}</td>
                                    <td>${user.phoneNumber}</td>
                                    <td>
                                        <c:forEach items="${user.roles}" var="role"> ${role.roleName} </c:forEach>
                                    </td>
                                    <td><a href="${path}/user/getformedituserbyadmin?id=${user.userId}">Edit</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
	    </div>
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