<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<script type="text/javascript" src="${path}/js/addreview.js"></script>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/commonstyle.css" rel="stylesheet"/> </head>

<body>
    <jsp:include page="${path}/view/header.jsp"/>

	<div class="container">
		<h2 class="h2 page-header">Об издании</h2>
		<div class="row">
			<div class="col-md-3">
				<p>
					<label for="bookName">Название</label>
				</p>
				<p>${book.bookName}</p>
			</div>
			<div class="col-md-3">
				<p>
					<label for="isbn">ISBN</label>
				</p>
				<p>${book.isbn}</p>
			</div>
			<div class="col-md-3">
				<p>
					<label for="year">Год издания</label>
				</p>
				<p>${book.year}</p>
			</div>
		</div>
		<div class="row">
			<p>
				<label for="description">Описание</label>
			</p>
			<p>${book.description}</p>
		</div>
		<div class="row">
			<p>
				<label for="genre">Жанр</label>
			</p>
			<c:forEach items="${book.genres}" var="genre">
				<p>${genre.genreName}</p>
			</c:forEach>
		</div>
		<div class="row">
			<p>
				<label for="author">Автор</label>
			</p>
			<c:forEach items="${book.authors}" var="author">
                <div class="row">
                    <div class="col-md-3">
                        <p>
                            <label for="firstName">Имя</label>
                        </p>
                        <p>${author.firstName}</p>
                    </div>
                    <div class="col-md-3">
                        <p>
                            <label for="lastName">Фамилия</label>
                        </p>
                        <p>${author.lastName}</p>
                    </div>
                    <div class="col-md-3">
                        <p>
                            <label for="middleName">Отчество</label>
                        </p>
                        <p>${author.middleName}</p>
                    </div>
                </div>
			</c:forEach>
		</div>
		<div class="row">
			<p>
				<label for="publishers">Издатель</label>
			</p>
			<c:forEach items="${book.publishers}" var="publisher">
				<p>${publisher.publisherName}</p>
			</c:forEach>
		</div>
		<div class="row">
            <h3 class="mb-0">
                <a class="btn btn-primary btn-lg" href="${path}/book/getsummary?id=${book.bookId}&name=${book.bookName}">Краткое содержание</a>
            </h3>
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