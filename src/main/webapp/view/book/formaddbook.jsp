<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <script type="text/javascript" src="${path}/js/addbook.js"></script>
        <link rel="stylesheet" href="/css/test.css"/>
    </head>
    <body>
        <h1>Add Book</h1>

        <form action="${path}/book/addbook" method="POST">
            <div id="bookItems">
                <div>
                    <b>Book:</b><br/>
                    Name <input type="text" name="bookName" value="${bookName}"/>
                    ISBN <input type="text" name="isbn"/>
                    Year <input type="text" name="year"/>
                </div>
            </div>

            <input type="button" value="Add new Book" onclick="addBookFormItem()"/>

            <input type="submit" align="center" value="Save"/>
        </form>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>