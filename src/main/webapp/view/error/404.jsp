<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<html>
    <head>
		    <meta charset="utf-8"/>
		    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		    <meta name="viewport" content="width=device-width, initial-scale=1"/>
		    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
    </head>
    <body>
        <h1 class="h1">Sorry, resource not found 404</h1>

        	<footer class="my-5 pt-5 text-muted text-center text-small">
                    <p class="mb-1">© 2018 Library</p>
                    <ul class="list-inline">
                      <li class="list-inline-item"><a href="${path}/console">H2 Console</a></li>
                    </ul>
            </footer>
    </body>
</html>