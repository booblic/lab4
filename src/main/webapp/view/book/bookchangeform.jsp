<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
</head>
<body>
<h1>Book List</h1>

          <form action="${path}/book/${book.bookId}/changebook" method="POST">
                  Id ${book.bookId}
                  Name <input type="text" name="bookName" value="${book.bookName}"/>
                  ISBN <input type="text" name="isbn" value="${book.isbn}"/>
                  Year <input type="text" name="year" value="${book.year}"/>
                  <input type="submit" align="center" value="Ghange"/>
          </form>

    <p><a href="${path}/">Start Page</a></p>

</body>
</html>