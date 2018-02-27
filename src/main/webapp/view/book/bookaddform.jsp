<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
</head>
<body>
<h1>Add Book</h1>

    <form action="${path}/book/add" method="POST">
        <h2>Book info</h2>
        Name <input type="text" name="bookName"/>
        ISBN <input type="text" name="isbn"/>
        Year <input type="text" name="year"/>
        <br>
        <h2>Author info</h2>
        FirstName <input type="text" name="firstName"/>
        LastName <input type="text" name="lastName"/>
        MiddleName <input type="text" name="middleName"/>
        <br>
        <h2>Publisher info</h2>
        PublisherName <input type="text" name="publisherName"/>
        <br>
        <input type="submit" align="center" value="Save"/>
    </form>

</body>
</html>