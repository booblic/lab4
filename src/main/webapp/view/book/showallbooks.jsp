<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <link rel="stylesheet" href="/css/test.css"/>
    </head>
    <body>
        <h1>Book List</h1>

        <c:if test="${not empty books}">
            <br/><br/>
            <div>
              <table border="1">
                <tr>
                    <th></th>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Year</th>
                    <th>Edit</th>
                    <th>View</th>
                    <th>Delete</th>
                </tr>
                    <c:forEach  items="${books}" var ="book">
                    <tr>

                      <form action="${path}/book/formedit" method="POST">
                        <td><input type="checkbox" form="export" name="id" value="${book.bookId}"/></td>
                        <td><input type="text" name="bookId" value="${book.bookId}" readonly/></td>
                        <td>${book.bookName}</td>
                        <td>${book.year}</td>
                        <td><input type="submit" name="kind" align="center" value="Edit"/></td>
                        <td><input type="submit" name="kind" align="center" value="View"/></td>
                        <td><input type="submit" name="kind" align="center" value="Delete"/></td>
                      </form>

                    </tr>
                    </c:forEach>
              </table>
            </div>

            <br>

            <form id="export" action="${path}/book/exportbooks" method="POST">
                <td><input type="hidden" name="id" value="0"/>
                <input type="submit" align="center" value="Export"/> Choose which books you want to export
            </form>


        </c:if>


        <c:if test="${not empty error}">
            ${error}
        </c:if>

        <br><br>

        <form action="${path}/book/getaddform" method="POST">
            <input type="hidden" name="bookName" value="${bookName}" readonly/>
            <input type="submit" align="center" value="Add this book"/>
        </form>

        <br><br>

        <form action="${path}/book/getfindbookform" method="POST">
            <input type="hidden" name="bookName" value="${bookName}" readonly/>
            <input type="submit" align="center" value="Find this book"/>
       </form>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>