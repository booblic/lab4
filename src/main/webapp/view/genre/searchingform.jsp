<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
    </head>
    <body>
        <h1>Searching Books by Genre Name</h1>

        <form action="${path}/genre/search" method="POST">
            Genre Name <input type="text" name="genreName"/>
            <input type="submit" align="center" value="Search"/>
        </form>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>