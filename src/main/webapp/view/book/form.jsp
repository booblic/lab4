<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
</head>
<body>
<h1>Add Book</h1>

    <form:form method="post" action="${path}/book/add" commandName="formBook">
        <p>Name : <form:input path="name" placeholder="Entry book name"/></p>
        <p>ISBN : <form:input path="isbn" placeholder="Entry ISBN"/></p>
        <p>Year : <form:input path="year" placeholder="Entry year"/></p>
        <p>
            <form:button value="save">Save</form:button>
            <a href="${path}/book/show">Show Book</a>
        </p>
    </form:form>

</body>
</html>