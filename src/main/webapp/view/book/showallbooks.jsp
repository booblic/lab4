 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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
	    <div class="row">
            <div class="table-responsive">
                <c:if test="${not empty books}">
                    <h2 class="h2 page-header">Бесплатные краткие содержания</h2>
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <!--<security:authorize access="hasRole('ROLE_MODER')">
                                    <th>Export</th>
                                </security:authorize>-->
                                <security:authorize access="hasRole('ROLE_MODER')">
                                    <th>Ид</th>
                                </security:authorize>
                                <th>Краткое Содержание</th>
                                <th>Страницы</th>
                                <th></th>
                                <security:authorize access="hasRole('ROLE_MODER')">
                                    <th></th>
                                </security:authorize>
                                <security:authorize access="hasRole('ROLE_MODER')">
                                    <th></th>
                                </security:authorize>
                                <c:if test="${subscribed}">
                                    <th></th>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${books}" var="book">
                                <tr>
                                    <!--<security:authorize access="hasRole('ROLE_MODER')">
                                        <td><input type="checkbox" class="form-check-input" form="export" name="id" value="${book.bookId}" /></td>
                                    </security:authorize>-->
                                    <security:authorize access="hasRole('ROLE_MODER')">
                                        <td>${book.bookId}</td>
                                    </security:authorize>
                                    <td><a href="${path}/book/getsummary?id=${book.bookId}&name=${book.bookName}">${book.bookName}</a></td>
                                    <td>${book.isbn}</td>
                                    <td><a href="${path}/book/getformviewbook?id=${book.bookId}">Об Издании</a></td>
                                    <security:authorize access="hasRole('ROLE_MODER')">
                                        <td><a href="${path}/book/getformedit?id=${book.bookId}">Редактировать</a></td>
                                    </security:authorize>
                                    <security:authorize access="hasRole('ROLE_MODER')">
                                        <td><a href="${path}/book/deletebook?id=${book.bookId}">Удалить</a></td>
                                    </security:authorize>
                                    <c:if test="${subscribed}">
                                        <td><a href="${path}/book/downloadbook?id=${book.bookId}">Скачать</td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <!--<security:authorize access="hasRole('ROLE_MODER')">
                        <form id="export" action="${path}/book/exportbooks" method="POST">
                            <input type="hidden" name="id" value="0" />
                            <button class="btn btn-info" type="submit">Export</button> Use checkbox
                        </form>
                    </security:authorize>-->
                </c:if>
                <c:if test="${not empty error}">
                    <p class="error">${error}</p>
                </c:if>
                <br><a class="btn btn-primary btn-lg btn-block" href="${path}/book/getaddform">Add book</a>
                <br><a class="btn btn-primary btn-lg btn-block" href="${path}/book/getsearchbookinternetform">Searching book on mybook.ru</a>
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