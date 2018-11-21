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
	<script type="text/javascript" src="${path}/js/editbook.js"></script>
<body>
    <jsp:include page="${path}/view/header.jsp"/>

	<div class="container">
    		<h2 class="h2 page-header">Add Book</h2>
    		<form action="${path}/book/addbook" method="POST" enctype="multipart/form-data">
    			<div class="row">
    				<div class="col-md-3">
    					<p>
    						<label for="bookName">Name</label>
    					</p>
    					<p>
    						<input type="text" name="bookName" class="form-control" value="${book.bookName}" required/> </p>
    				</div>
    				<div class="col-md-3">
    					<p>
    						<label for="isbn">ISBN</label>
    					</p>
    					<p><input type="text" name="isbn" class="form-control" value="${book.isbn}" required/> </p>
    					<c:if test="${not empty error}">
                        	<p class="error">${error}</p>
                        </c:if>
    				</div>
    				<div class="col-md-3">
    					<p>
    						<label for="year">Year</label>
    					</p>
    					<p>
    						<input type="text" name="year" class="form-control" value="${book.year}" required/> </p>
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

    						<p><input type="text" class="form-control" name="genresNames" /> </p>

    					</div>
    				</div>
    			</div>
    			<p>
    				<button type="button" class="btn btn-primary" onclick="addGenreFormItem()">Add genre</button>
    			</p>
    			<div id="authorItems">
    				<label for="author">Author</label>
    				<div class="row">

    						<div class="col-md-3">
    							<p>First name</p>
    							<p>
    								<input type="text" class="form-control" name="authorsFirstNames" /> </p>
    						</div>
    						<div class="col-md-3">
    							<p>Last name</p>
    							<p>
    								<input type="text" class="form-control" name="authorsLastNames" /> </p>
    						</div>
    						<div class="col-md-3">
    							<p>Middle name</p>
    							<p>
    								<input type="text" class="form-control" name="authorsMiddleNames" /> </p>
    						</div>

    				</div>
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

    							<p><input type="text" class="form-control" name="publishersNames" /> </p>

    					</div>
    				</div>
    			</div>
    			<p>
    				<button type="button" class="btn btn-primary" onclick="addPublisherFormItem()">Add publisher</button>
    			</p>
    			<p>
    			<div class="col-md-3">
    			    <p><label for="download">Загрузите краткое содержание</label></p>
                    <p><input type="file" name="file"></p>
                </div>
                </p>
    			<p>
    				<button type="submit" class="btn btn-success btn-lg btn-block">Add</button>
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