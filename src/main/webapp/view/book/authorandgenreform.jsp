<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
		<html>

		<head>
		    <link rel="stylesheet" href="/css/test.css"/>
		</head>

		<body>
			<h1>Genre and year search</h1>
			<p>
				<form action="${path}/book/searcingbyauthorandgenre" method="POST">
					<div id="bookItems">
						<div> <b>Entry the data:</b>
						    <br>
                            First Name <input type="text" name="firstName"/>
                            Last Name <input type="text" name="lastName"/>
                            <br><br>
                            Genre Name <input type="text" name="genreName"/>
						</div>
					</div>
					<br>
					<input type="submit" align="center" value="Search" /> </form>
			</p>
		</body>

		</html>