<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
</head>
<body>
<h1>Edit Book</h1>

          <form action="${path}/book/${book.bookId}/editbook" method="POST">
                  Id ${book.bookId}
                  Name <input type="text" name="bookName" value="${book.bookName}"/>
                  ISBN <input type="text" name="isbn" value="${book.isbn}"/>
                  Year <input type="text" name="year" value="${book.year}"/>
                  <br>
                   <h2>Genre Name</h2>
                   <p><textarea rows="5" cols="8" name="genreNames"></textarea></p>
                   <br>
                   <br>
                   <h2>Author</h2>
                   <p><textarea rows="5" cols="8" name="authorNames"></textarea></p>
                   <br>
                   <br>
                   <h2>Publisher</h2>
                   <p><textarea rows="5" cols="8" name="publisherNames"></textarea></p>
                   <br>

                  <input type="submit" align="center" value="Ghange"/>
          </form>

    <p><a href="${path}/">Start Page</a></p>

</body>
</html>