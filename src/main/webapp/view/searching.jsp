<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
		<meta charset="utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
        <script type="text/javascript" src="${path}/js/addbook.js"></script>
        <link href="${path}/css/searching.css" rel="stylesheet">
    </head>
    <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
            		<div class="container">
            			<div class="navbar-header">
            				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
            						data-target="#navbar" aria-expanded="false" aria-controls="navbar">
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
            			</div><!--/.nav-collapse -->
            		</div>
        </nav>

        <div class="container">
        <h2 class="h2">Searching Book</h1>

        <form action="${path}/searching" method="POST">
            <div id="bookItems">
                <div>
                    <b>Book:</b><br/>
                    Name <input type="text" name="bookName"/>
                    ISBN <input type="text" name="isbn"/>
                    Year <input type="text" name="year"/>
                </div>
            </div>

            <br>

            <div id="genreItems">
                <div>
                    <b>Genre:</b><br/>
                        Genre Name <input type="text" name="genreName"/>
                        <br><br>
                </div>
            </div>

            <br><br>

            <div id="authorItems">
                <div>
                    <b>Author:</b><br/>
                        First Name <input type="text" name="firstName"/>
                        Last Name <input type="text" name="lastName"/>
                        Middle Name <input type="text" name="middleName"/>
                        <br><br>
                </div>
            </div>

            <br><br>

            <div id="publisherItems">
                <div>
                    <b>Publisher:</b><br/>
                        Publisher Name <input type="text" name="publisherName"/>
                        <br><br>
                </div>
            </div>

            <br><br>

            <div>
                <input type="submit" align="center" value="Searching"/>
            </div>
        </form>

        <br><br>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>