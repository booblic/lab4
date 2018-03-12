<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <link rel="stylesheet" href="/css/test.css"/>
    </head>
    <body>
        <h1>Welcome to Library!</h1>

        <p>${message}</p>

        <p>${registrationMessage}</p>

        <p>${logoutMessage}</p>

        <p><a href="${path}/user/registrationform">Registration</a></p>

        <p><a href="${path}/user/login">Sign In</a></p>

        <c:if test="${not empty logout}">

            <p><a href="${path}/logout">Logout</a></p>
            <br>
            <p><a href="${path}/user/showuserprofile">Edit Your Profile</a></p>

        </c:if>


        <p><a href="${path}/book/show">Show Books</a></p>

        <p><a href="${path}/book/formadd">Add Book</a></p>

        <p><a href="${path}/searchbookoptions">Searching Books</a></p>

        <br>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>