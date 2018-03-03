<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
    <script>
        function createInputElement(type, name) {
            var i = document.createElement("input");
            i.type = type;
            i.name = name;
            return i;
        }
        function addBookFormItem() {
            with (document) {
                var div = createElement("div");
                div.appendChild(createTextNode("Book"));
                div.appendChild(createElement("br"));
                div.appendChild(createTextNode("Name"));
                div.appendChild(createInputElement("text", "bookName"));
                div.appendChild(createTextNode("ISBN"));
                div.appendChild(createInputElement("text", "isbn"));
                div.appendChild(createTextNode("Year"));
                div.appendChild(createInputElement("text", "year"));
                getElementById("bookItems").appendChild(div);
            }
        }
    </script>
</head>
<body>
<h1>Add Book</h1>

    <form action="${path}/book/addbook" method="POST">
        <div id="bookItems">
            <div>
                <b>Book:</b><br/>
                Name <input type="text" name="bookName"/>
                ISBN <input type="text" name="isbn"/>
                Year <input type="text" name="year"/>
            </div>
        </div>

        <input type="button" value="Add new Book" onclick="addBookFormItem()"/>
        <input type="submit" align="center" value="Save"/>
    </form>


</body>
</html>