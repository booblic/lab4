<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <link rel="stylesheet" href="/css/test.css"/>
    </head>
    <body>
        <h1>Finding Result</h1>
            <c:if test="${not empty books}">
                <br/><br/>
                <div>
                  <table border="1">
                    <tr>
                        <th>№</th>
                        <th>Name</th>
                        <th>Genres</th>
                        <th>Authors</th>
                        <th>Publishers</th>
                        <th>Year</th>
                        <th>Add</th>
                    </tr>
                        <c:forEach  items="${books}" var ="book">
                        <tr>

                          <form action="${path}/book/addfindingbook" method="POST">
                            <td>${book.count}</td>
                            <td><input type="text" name="bookName" value="${book.bookName}"/></td>
                            <td><input type="text" name="genresNames" value="${book.genresNames}"/></td>
                            <td><input type="text" name="authorsNames" value="${book.authorsNames}"/></td>
                            <td><input type="text" name="publishersNames" value="${book.publishersNames}"/></td>
                            <td><input type="text" name="year" value="${book.year}"/></td>
                            <input type="hidden" name="href" value="${book.href}"/>
                            <td><input type="submit" align="center" value="Add"/></td>
                          </form>

                        </tr>
                        </c:forEach>
                  </table>
                </div>

            </c:if>

            <br>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>