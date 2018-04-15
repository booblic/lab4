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
	<div class="container-fluid">
	    <div class="row">
            <div class="table-responsive">
                <c:if test="${not empty books}">
                    <h2 class="h2 page-header">Searching results</h2>
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th>Name</th>
                                <th>Genres</th>
                                <th>Authors</th>
                                <th>Publishers</th>
                                <th>Year</th>
                                <th>Add</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${books}" var="book">
                                <tr>
                                    <form action="${path}/book/addsearchingbook" method="POST">
                                        <td>${book.count}</td>
                                        <td>
                                            <input type="hidden" class="form-control" name="bookName" value="${book.bookName}" />
                                            ${book.bookName}
                                        </td>
                                        <td>
                                            <input type="hidden" class="form-control" name="genresNames" value="${book.genresNames}" />
                                            ${book.genresNames}
                                        </td>
                                        <td>
                                            <input type="hidden" class="form-control" name="authorsNames" value="${book.authorsNames}" />
                                            ${book.authorsNames}
                                        </td>
                                        <td>
                                            <input type="hidden" class="form-control" name="publishersNames" value="${book.publishersNames}" />
                                            ${book.publishersNames}
                                        </td>
                                        <td>
                                            <input type="hidden" class="form-control" name="year" value="${book.year}" />
                                            ${book.year}
                                        </td>
                                            <input type="hidden" name="href" value="${book.href}" />
                                        <td>
                                            <button type="submit" class="btn btn-primary">Add</button>
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty books}">
                    <c:if test="${not empty error}">
                        <p class="error">${error}</p>
                    </c:if>
                    <br><a class="btn btn-primary btn-lg btn-block" href="${path}/book/getaddform">Add book</a> </c:if>
            </div>
        </div>
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