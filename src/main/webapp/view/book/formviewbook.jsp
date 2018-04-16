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
		<h2 class="h2 page-header">View book</h2>
		<div class="row">
			<div class="col-md-3">
				<p>
					<label for="bookName">Name</label>
				</p>
				<p>${book.bookName}</p>
			</div>
			<div class="col-md-3">
				<p>
					<label for="bookRating">Rating</label>
				</p>
				<p>${book.bookRating}</p>
			</div>
			<div class="col-md-3">
				<p>
					<label for="isbn">ISBN</label>
				</p>
				<p>${book.isbn}</p>
			</div>
			<div class="col-md-3">
				<p>
					<label for="year">Year</label>
				</p>
				<p>${book.year}</p>
			</div>
		</div>
		<div class="row">
			<p>
				<label for="description">Description</label>
			</p>
			<p>${book.description}</p>
		</div>
		<div class="row">
			<p>
				<label for="genre">Genre</label>
			</p>
			<c:forEach items="${book.genres}" var="genre">
				<p>${genre.genreName}</p>
			</c:forEach>
		</div>
		<div class="row">
			<p>
				<label for="author">Author</label>
			</p>
			<c:forEach items="${book.authors}" var="author">
                <div class="row">
                    <div class="col-md-3">
                        <p>
                            <label for="firstName">First name</label>
                        </p>
                        <p>${author.firstName}</p>
                    </div>
                    <div class="col-md-3">
                        <p>
                            <label for="lastName">Last name</label>
                        </p>
                        <p>${author.lastName}</p>
                    </div>
                    <div class="col-md-3">
                        <p>
                            <label for="middleName">Middle name</label>
                        </p>
                        <p>${author.middleName}</p>
                    </div>
                </div>
			</c:forEach>
		</div>
		<div class="row">
			<p>
				<label for="publishers">Publisher</label>
			</p>
			<c:forEach items="${book.publishers}" var="publisher">
				<p>${publisher.publisherName}</p>
			</c:forEach>
		</div>
		<c:set var="rev" scope="page" value="No" />
		<div>
			<h2 class="h2 page-header">Book Reviews</h2>
			<c:forEach items="${reviews}" var="review">
				<c:choose>
					<c:when test="${user.username == review.user.username}">
					    <c:if test="${book.bookName == review.book.bookName}">
					        <c:forEach var="entry" items="${review.bookReview}">

                            							<p>
                            								<textarea id="textReview" class="form-control" name="textReview" form="review">${entry.key}</textarea>
                            							</p>
                            							<p>
                            								<input type="number" name="rating" class="form-control" min="1" max="5" value="${entry.value}" form="review" />
                            							</p>
                            						</c:forEach>
                            						<p>
                            							<button type="submit" class="btn btn-success" form="review">Edit review</button>
                            						</p>
                            						<c:set var="rev" scope="page" value="Yes" />
					    </c:if>
					</c:when>
					<c:otherwise>
						<c:forEach var="entry" items="${review.bookReview}">
							<label for="username">Review by user: </label> ${review.user.username}
							<p>${entry.key}</p>
							<label for="rating">Rating:</label> ${entry.value}
							<h2 class="h2 page-header"></h2> </c:forEach>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>



		<form action="${path}/book/${book.bookId}/addreview" id="review" method="POST">
			<div id="bookReview"> </div>
			<c:if test="${not empty username}">
                <c:if test="${rev == 'No'}">
                    <div id="but">
                        <p>
                            <button type="button" class="btn btn-primary" onclick="addBookFormReview()">Add review</button>
                        </p>
                    </div>
                </c:if>
            </c:if>
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