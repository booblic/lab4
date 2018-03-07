<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
    </head>
    <body>
        <h1>Start Page</h1>

        <form:form action="${path}/user/registeruser" modelAttribute="user" method="POST">

            First Name:  <form:input path="firstName"/><br><br>

            Last Name:  <form:input path="lastName"/><br><br>

            Middle Name:  <form:input path="middleName"/><br><br>

            Email: <form:input path="email"/><br><br>
            <form:errors path="email"/><br><br>

            Phone Number: <form:input path="phoneNumber"/><br><br>
            <form:errors path="phoneNumber"/><br><br>

            User Name: <form:input path="username"/><br><br>
            <form:errors path="username"/><br><br>

            Password: <form:input path="password"/><br><br>

            Confirmed Password <input type="text" name="confirmPassword"/><br><br>

            <input type="submit" value="Registration"/>
        </form:form>

    </body>
</html>