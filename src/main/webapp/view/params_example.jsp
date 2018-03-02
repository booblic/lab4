<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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


<h1>Send data as single argument</h1>
<p>
    <form action="${path}/params/single" method="POST">
        <label for="bookData">Book information:</label><br/>
        <textarea rows="5" cols="200" name="bookData" id="bookData" style="width: 400px"></textarea>

        <p>
            Please enter book data in format:
            <pre>Name;ISBN;Year</pre>
        </p>
        <p>
            Example:
<pre>
Thinking in Java;9780131872486;2006
Design Patterns: Elements of Reusable Object-Oriented Software;9780201633610;1994
</pre>
        </p>
        <input type="submit" align="center" value="Save"/>
    </form>
</p>


<h1>Send data as separate values</h1>
<p>
    <form action="${path}/params/arrays" method="POST">
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
</p>

</body>
</html>