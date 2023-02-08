<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Error</title>
</head>
<body>
	<div>Error</div>
	<c:forEach var="errorMsg" items="${errorMsgs}">
		<div>${errorMsg.value}</div>
	</c:forEach>
</body>
</html>