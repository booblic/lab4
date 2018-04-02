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
            <script type="text/javascript" src="${path}/js/addbook.js"></script>
            <link href="${path}/css/searching.css" rel="stylesheet">
        </head>

        <body>

            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                        </button>
                        <a class="navbar-brand" href="${path}/">
                            <span class="glyphicon glyphicon-book"></span> Library
                        </a>
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
                                    <br>
                                </c:when>
                            </c:choose>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </nav>

            <div class="container">
                <h2 class="h2 page-header">Searching Book</h2>

                <h3 class="mb-0">
            <p><a href="${path}/book/getsearchingform" class="text-dark">By name</a></p>

            <p><a href="${path}/genre/getsearchingbygenreform" class="text-dark">By genre</a></p>

            <p><a href="${path}/author/getsearchingbyauthorform" class="text-dark">By author</a></p>

            <p><a href="${path}/publisher/getsearchingbypublisherform" class="text-dark">By publisher</a></p>

            <p><a href="${path}/book/genreandyearsearchingform" class="text-dark">By genre and year</a></p>

            <p><a href="${path}/book/authorandgenresearchingform" class="text-dark">By author and genre</a></p>
        </h3>

            </div>

            <footer class="my-5 pt-5 text-muted text-center text-small">
                <br>
                <p class="mb-1">© 2018 Library</p>
                <ul class="list-inline">
                    <li class="list-inline-item"><a href="${path}/console">H2 Console</a></li>
                </ul>
            </footer>

        </body>

        </html>