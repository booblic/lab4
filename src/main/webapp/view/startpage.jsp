<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
</head>
<body>
<h1>Start Page</h1>

    <p>${startMessage}</p>
    <p><a href="${path}/book/show">Show Books</a></p>

    <p><a href="${path}/book/form">Add Book</a></p>

</body>
</html>