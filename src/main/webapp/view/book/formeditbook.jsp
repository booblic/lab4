<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <script type="text/javascript" src="${path}/js/editbook.js"></script>
    </head>
    <body>
    <h1>Edit Book</h1>

        <form action="${path}/book/${book.bookId}/editbook" method="POST">
            <div>
                <b>Book:</b><br/>
                Name <input type="text" name="bookName" value="${book.bookName}"/>
                ISBN <input type="text" name="isbn" value="${book.isbn}"/>
                Year <input type="text" name="year" value="${book.year}"/>
            </div>

            <br>

            <div id="genreItems">
                <div>
                    <b>Genre:</b><br/>
                    <c:forEach  items="${genres}" var ="genre">
                        Genre Name <input type="text" name="genreName" value="${genre.genreName}"/>
                        <br><br>
                    </c:forEach>
                </div>
            </div>

            <div>
                <br>
                Genre Name
                <input type="text" name="genreName"/>
            </div>

            <input type="button" value="Add new Genre" onclick="addGenreFormItem()"/>

            <br><br>

            <div id="authorItems">
                <div>
                    <b>Author:</b><br/>
                     <c:forEach  items="${authors}" var ="author">
                        First Name <input type="text" name="firstName" value="${author.firstName}"/>
                        Last Name <input type="text" name="lastName" value="${author.lastName}"/>
                        Middle Name <input type="text" name="middleName" value="${author.middleName}"/>
                        <br><br>
                     </c:forEach>
                </div>
            </div>

            <div>
                <br>
                First Name <input type="text" name="firstName"/>
                Last Name <input type="text" name="lastName"/>
                Middle Name <input type="text" name="middleName"/>
            </div>

            <input type="button" value="Add new Author" onclick="addAuthorFormItem()"/>

            <br><br>

            <div id="publisherItems">
                <div>
                    <b>Publisher:</b><br/>
                    <c:forEach  items="${publishers}" var ="publisher">
                        Publisher Name <input type="text" name="publisherName" value="${publisher.publisherName}"/>
                        <br><br>
                    </c:forEach>
                </div>
            </div>

            <div>
                <br>
                Publisher Name <input type="text" name="publisherName"/>
            </div>

            <input type="button" value="Add new Publisher" onclick="addPublisherFormItem()"/>

            <br><br>

            <div>
                <input type="submit" align="center" value="Ghange"/>
            </div>
        </form>

        <br><br>

        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>