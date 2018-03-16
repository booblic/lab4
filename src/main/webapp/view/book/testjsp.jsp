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
                       div.appendChild(createInputElement("text", "genresNames"));
                       getElementById("bookItems").appendChild(div);
                   }
               }
           </script>
       </head>
       <body>

       <h1>Send data as separate values</h1>
       <p>
           <form action="${path}/book/params/arrays" method="POST">
               <div id="bookItems">
                   <div>
                       <b>Book:</b><br/>
                       Id <input type="text" name="id"/>
                       Name <input type="text" name="genresNames"/>
                   </div>
               </div>

               <input type="button" value="Add new Book" onclick="addBookFormItem()"/>
               <input type="submit" align="center" value="Save"/>
           </form>
       </p>

       </body>
       </html>