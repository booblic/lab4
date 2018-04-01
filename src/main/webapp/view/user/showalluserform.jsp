<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
        <link rel="stylesheet" href="/css/test.css"/>
    </head>
    <body>
        <h1>All User</h1>

        <c:if test="${not empty users}">
            <br/><br/>
            <div>
              <table border="1">
                <tr>
                    <th>Id</th>
                    <th>User Name</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Middle Name</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th>Role</th>
                    <th>Edit</th>
                </tr>
                    <c:forEach  items="${users}" var ="user">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.username}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.middleName}</td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                        <td>
                        <c:forEach  items="${user.roles}" var ="role">
                            ${role.roleName}
                        </c:forEach>
                        </td>
                        <td><a href="${path}/user/getformedituserbyadmin?id=${user.userId}">Edit</a></td>

                    </tr>
                    </c:forEach>
              </table>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            ${error}
        </c:if>


        <p><a href="${path}/">Start Page</a></p>

        <p><a href="${path}/console">H2 Console</a></p>

    </body>
</html>