<%@  page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<spring:form method="post" modelAttribute="list">
	<spring:input path="accountNumber"/>
	<spring:input path="accountHolderName"/>
	<spring:input path="accountBalance"/>
	<spring:input path="isSalary"/>
	</spring:form>
</body>
</html>