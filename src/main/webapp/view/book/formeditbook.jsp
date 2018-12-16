<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<script type="text/javascript" src="${path}/js/editbook.js"></script>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link href="${path}/css/commonstyle.css" rel="stylesheet"/> </head>

<body>
    <jsp:include page="${path}/view/header.jsp"/>

	<div class="container">
		<h2 class="h2 page-header">Редактирование</h2>
		<form action="${path}/book/editbook" method="POST" enctype="multipart/form-data">
			<div class="row">
				<input type="text" name="bookId" value="${book.bookId}" size="3" hidden="true"/>
				<input type="text" name="viewsNumber" value="${book.viewsNumber}" hidden="true"/>
				<input type="text" name="summaryPath" value="${book.summaryPath}" hidden="true"/>
				<input type="text" name="summaryNumberPage" value="${book.summaryNumberPage}" hidden="true" />
				<div class="col-md-3">
					<p>
						<label for="bookName">Название</label>
					</p>
					<p>
						<input type="text" name="bookName" class="form-control" value="${book.bookName}" /> </p>
				</div>
				<div class="col-md-3">
					<p>
						<label for="isbn">ISBN</label>
					</p>
					<p>
						<input type="text" name="isbn" class="form-control" value="${book.isbn}" /> </p>
				</div>
				<div class="col-md-3">
					<p>
						<label for="year">Год изадния</label>
					</p>
					<p>
						<input type="text" name="year" class="form-control" value="${book.year}" /> </p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-3">
					<p>
						<label for="description">Описание</label>
					</p>
					<p>
						<textarea rows="5" cols="200" style="width: 400px" class="form-control" name="description">${book.description}</textarea>
					</p>
				</div>
			</div>
			<div id="genreItems">
				<div class="row">
					<div class="col-md-3">
						<p>
							<label for="genre">Жанр</label>
						</p>
						<c:forEach items="${book.genres}" var="genre">
							<p>
								<input type="text" class="form-control" name="genresNames" value="${genre.genreName}" /> </p>
						</c:forEach>
					</div>
				</div>
			</div>
			<p>
				<button type="button" class="btn btn-primary" onclick="addGenreFormItem()">Добавить</button>
			</p>
			<div id="authorItems">
				<label for="genre">Автор</label>
				<c:forEach items="${book.authors}" var="author">
				    <div class="row">
						<div class="col-md-3">
							<p>Имя</p>
							<p>
								<input type="text" class="form-control" name="authorsFirstNames" value="${author.firstName}" /> </p>
						</div>
						<div class="col-md-3">
							<p>Фамилия</p>
							<p>
								<input type="text" class="form-control" name="authorsLastNames" value="${author.lastName}" /> </p>
						</div>
						<div class="col-md-3">
							<p>Отчество</p>
							<p>
								<input type="text" class="form-control" name="authorsMiddleNames" value="${author.middleName}" /> </p>
						</div>
				    </div>
				</c:forEach>
			</div>
			<p>
				<button type="button" class="btn btn-primary" onclick="addAuthorFormItem()">Добавить автора</button>
			</p>
			<div id="publisherItems">
				<div class="row">
					<div class="col-md-3">
						<p>
							<label for="publisher">Издатель</label>
						</p>
						<c:forEach items="${book.publishers}" var="publisher">
							<p>
								<input type="text" class="form-control" name="publishersNames" value="${publisher.publisherName}" /> </p>
						</c:forEach>
					</div>
				</div>
			</div>
			<p>
				<button type="button" class="btn btn-primary" onclick="addPublisherFormItem()">Добавить изадтеля</button>
			</p>
    		<p>
    		<div class="row">
                <div class="col-md-4">
                    <p><label for="download">Загрузите краткое содержание</label></p>
                    <p><input class="btn btn-primary" type="file" name="file"></p>
                </div>
                <div class="col-md-1">
                	<c:if test="${book.free == true}">
                		<p><label for="role">Бесплатное</label></p>
                		<input type="checkbox" class="form-control" name="free" checked/>
                	</c:if>
                	<c:if test="${book.free == false}">
                		<p><label for="role">Бесплатное</label></p>
                		<input type="checkbox" class="form-control" name="free"/>
                	</c:if>
                </div>
            </div>
            </p>
            <div class="row">
                <div class="col-md-10">
                    <p><button type="submit" class="btn btn-success btn-lg">Сохранить</button></p>
                </div>
			</div>
		</form>
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