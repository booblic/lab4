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
	<link href="${path}/css/commonstyle.css" rel="stylesheet"/> </head>

<body>
    <jsp:include page="${path}/view/header.jsp"/>

	<div class="container">
		<h2 class="text-center">Библиотека кратких содержаний!</h2>
		<h5 class="text-center">Количество кратких содержаний: ${bookCount}</h5>
		<p>${message}</p>
		<p>${registrationMessage}</p>

        <div class="row">
            <div class="col-md-6">
                <h3 class="mb-0">
                    <a class="btn btn-primary btn btn-block" href="${path}/book/show">Краткие содержания</a>
                </h3>
                <h3 class="mb-0">
                    <a class="btn btn-primary btn btn-block" href="${path}/book/showFree">Бесплатные краткие содержания</a>
                </h3>
            </div>
            <div class="col-md-6">
                <c:choose>
                    <c:when test="${empty username}">
                        <h3 class="mb-0">
                            <p><a class="btn btn-success  btn btn-block" href="${path}/user/login">Войдите или зарегистрируйтесь</a></p>
                        </h3>
                    </c:when>
                    <c:when test="${not empty username}">
                    </c:when>
                </c:choose>

                <c:choose>
                    <c:when test="${empty subscribed}">
                        <h3 class="mb-0">
                            <p><a class="btn btn-success btn btn-block" href="${path}/user/getsubscribeform">Подписаться</a></p>
                        </h3>
                    </c:when>
                    <c:when test="${not empty subscription}">
                    </c:when>
                </c:choose>
            </div>
        </div>
	</div>
	<!-- /.container -->
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