<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <link rel="stylesheet" href="/css/test.css"/>
    </head>
    <body>
        <h1>Your Profile</h1>

        <form action="${path}/user/getedituserform" method="POST">

            First Name:  ${user.firstName}<br><br>

            Last Name:  ${user.lastName}<br><br>

            <c:if test="${not empty user.middleName}">

                Middle Name:  ${user.middleName}<br><br>

            </c:if>

            Email: ${user.email}<br><br>

            Phone Number: ${user.phoneNumber}<br><br>

            User Name: ${user.username}<br><br>

            <input type="submit" value="Edit"/>

        </form>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>