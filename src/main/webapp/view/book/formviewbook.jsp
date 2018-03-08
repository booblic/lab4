<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>

<head>
	<script type="text/javascript" src="${path}/js/addreview.js"></script>
</head>

<body>
	<h1>View Book</h1>
	<form action="${path}/book/${book.bookId}/addreview" method="POST">
		<div> <b>Book:</b>
			<br/> Name ${book.bookName} ISBN ${book.isbn} Year ${book.year} Rating ${book.bookRating} </div>
		<br>
		<br>
		<div> <b>Genre:</b>
			<br/>
			<c:forEach items="${genres}" var="genre"> ${genre.genreName}
				<br>
				<br> </c:forEach>
		</div>
		<br>
		<br>
		<div> <b>Author:</b>
			<br/>
			<c:forEach items="${authors}" var="author"> First Name ${author.firstName} Last Name ${author.lastName}" Middle Name ${author.middleName}
				<br>
				<br> </c:forEach>
		</div>
		<br>
		<br>
		<div> <b>Publisher:</b>
			<br/>
			<c:forEach items="${publishers}" var="publisher"> ${publisher.publisherName}
				<br>
				<br> </c:forEach>
		</div>
		<br>
		<br>
		<div> <b>Book Reviews:</b>
			<br>
			<c:forEach items="${reviews}" var="review"> Review By User: ${user.username} <br> ${review.text} <br> Rating: ${review.rating}
				<br><br> </c:forEach>
			<br> </div>
		<br>
		<br>
		<div id="bookReview">
			<input type="button" value="Add new Review" onclick="addBookFormReview()" />
			<br>
			<br>
		</div>

		<input type="submit" align="center" value="Save"/>

	</form>

	<p><a href="${path}/">Start Page</a></p>

    <p><a href="${path}/console">H2 Console</a></p>

</body>

</html>