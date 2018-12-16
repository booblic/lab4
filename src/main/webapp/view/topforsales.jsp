 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
	    <h2 class="h2 page-header">Статистика</h2>
	    <div class="row">
	        <div class="col-md-4">
                <div class="table-responsive">
                    <h4>Топ кратких содержаний</h4>
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>Название</th>
                                <th>Просмотры</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${topSummary}" var="book">
                                <tr>
                                    <td>${book.bookName}</td>
                                    <td>${book.viewsNumber}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
	        <div class="col-md-4">
                <div class="table-responsive">
                    <h4>Топ бесплатных кратких содержаний</h4>
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>Название</th>
                                <th>Просмотры</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${topFreeSummary}" var="book">
                                <tr>
                                    <td>${book.bookName}</td>
                                    <td>${book.viewsNumber}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
 	        <div class="col-md-4">
                 <div class="table-responsive">
                     <h4>Топ жанров</h4>
                     <table class="table table-striped table-sm">
                         <thead>
                             <tr>
                                 <th>Название</th>
                                 <th>Просмотры</th>
                             </tr>
                         </thead>
                         <tbody>
                             <c:forEach items="${topGenres}" var="genre">
                                 <tr>
                                     <td>${genre.key}</td>
                                     <td>${genre.value}</td>
                                 </tr>
                             </c:forEach>
                         </tbody>
                     </table>
                 </div>
            </div>
	    </div>
	    <p>Количество активных подписчиков: ${currentSubscribersNumber}</p>
	    <p>Общее количество просмотров кратких содержаний: ${commonViewNumber}</p>
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