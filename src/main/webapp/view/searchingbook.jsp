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
		<h2 class="h2 page-header">Searching Book</h2>
		<h3 class="mb-0">
            <p><a href="${path}/book/getsearchingbybooknameform" class="text-dark">By name</a></p>

            <p><a href="${path}/genre/getsearchingbygenreform" class="text-dark">By genre</a></p>

            <p><a href="${path}/author/getsearchingbyauthorform" class="text-dark">By author</a></p>

            <p><a href="${path}/publisher/getsearchingbypublisherform" class="text-dark">By publisher</a></p>

            <p><a href="${path}/book/genreandyearsearchingform" class="text-dark">By genre and year</a></p>

            <p><a href="${path}/book/authorandgenresearchingform" class="text-dark">By author and genre</a></p>
        </h3> </div>
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