<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
       <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <c:set var="path" value="${pageContext.request.contextPath}"></c:set>
       <html>
       <head>
       </head>
       <body>

       <h1>Send data as separate values</h1>
       <p>
           <form action="${path}/book/params/arrays" method="POST">
               <div id="bookItems">
                   <div>
                       <b>Entry the data:</b><br/>
                       First Name <input type="text" name="firstName"/>
                       Last Name <input type="text" name="lastName"/>
                       Genre Name <input type="text" name="genreName"/>
                   </div>
               </div>

               <input type="submit" align="center" value="Save"/>
           </form>
       </p>

       </body>
       </html>