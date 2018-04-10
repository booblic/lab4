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
		<h2 class="h2 page-header">Edit Book</h2>
		<form action="${path}/book/editbook" method="POST">
			<div class="row">
				<input type="text" name="bookId" value="${book.bookId}" size="3" hidden="true" />
				<div class="col-md-3">
					<p>
						<label for="bookName">Name</label>
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
						<label for="year">Year</label>
					</p>
					<p>
						<input type="text" name="year" class="form-control" value="${book.year}" /> </p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-3">
					<p>
						<label for="description">Description</label>
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
							<label for="genre">Genre</label>
						</p>
						<c:forEach items="${book.genres}" var="genre">
							<p>
								<input type="text" class="form-control" name="genresNames" value="${genre.genreName}" /> </p>
						</c:forEach>
					</div>
				</div>
			</div>
			<p>
				<button type="button" class="btn btn-primary" onclick="addGenreFormItem()">Add genre</button>
			</p>
			<div id="authorItems">
				<label for="genre">Author</label>
				<c:forEach items="${book.authors}" var="author">
				    <div class="row">
						<div class="col-md-3">
							<p>First name</p>
							<p>
								<input type="text" class="form-control" name="authorsFirstNames" value="${author.firstName}" /> </p>
						</div>
						<div class="col-md-3">
							<p>Last name</p>
							<p>
								<input type="text" class="form-control" name="authorsLastNames" value="${author.lastName}" /> </p>
						</div>
						<div class="col-md-3">
							<p>Middle name</p>
							<p>
								<input type="text" class="form-control" name="authorsMiddleNames" value="${author.middleName}" /> </p>
						</div>
				    </div>
				</c:forEach>
			</div>
			<p>
				<button type="button" class="btn btn-primary" onclick="addAuthorFormItem()">Add author</button>
			</p>
			<div id="publisherItems">
				<div class="row">
					<div class="col-md-3">
						<p>
							<label for="publisher">Publisher</label>
						</p>
						<c:forEach items="${book.publishers}" var="publisher">
							<p>
								<input type="text" class="form-control" name="publishersNames" value="${publisher.publisherName}" /> </p>
						</c:forEach>
					</div>
				</div>
			</div>
			<p>
				<button type="button" class="btn btn-primary" onclick="addPublisherFormItem()">Add publisher</button>
			</p>
			<p>
				<button type="submit" class="btn btn-success btn-lg btn-block">Edit</button>
			</p>
		</form>
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