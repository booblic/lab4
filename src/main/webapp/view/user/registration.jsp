<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
    </head>
    <body>
        <h1>Start Page</h1>

        <c:if test="${not empty error}">
           ${error}
        </c:if>

        <form action="${path}/user/registeruser" method="POST">
            User Name <input type="text" name="username"/>
            Password <input type="text" name="password"/>
            Confirmed Password <input type="text" name="confirmPassword"/>
            <input type="submit" align="center" value="Registration"/>
        </form>

    </body>
</html>