<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <link rel="stylesheet" href="/css/test.css"/>
    </head>
    <body>
        <h1>Searching Books</h1>

        <p><a href="${path}/book/getsearchingform">Searching from name</a></p>

        <p><a href="${path}/genre/getsearchingform">Searching by genre</a></p>

        <p><a href="${path}/author/getsearchingform">Searching by author</a></p>

        <p><a href="${path}/publisher/getsearchingform">Searching by publisher</a></p>

        <p><a href="${path}/searchingform">Slow Searching</a></p>

        <p><a href="${path}/book/genreandyearsearchingform">Searching by genre and year</a></p>

        <p><a href="${path}/book/authorandgenresearchingform">Searching by author and genre</a></p>

        <p><a href="${path}/book/test">Test Searching</a></p>

        <br><br>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>