<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
    </head>
    <body>
        <h1>Book List</h1>

        <br/><br/>
        <div>
          <table border="1">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Year</th>
                <th>Mod</th>
            </tr>
            <c:forEach  items="${books}" var ="book">
            <tr>

              <form action="${path}/book/${book.bookId}/formedit" method="POST">
                <td>${book.bookId}</td>
                <td>${book.bookName}</td>
                <td>${book.year}</td>
                <td><input type="submit" align="center" value="Edit"/></td>
              </form>

            </tr>
            </c:forEach>
          </table>
        </div>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>