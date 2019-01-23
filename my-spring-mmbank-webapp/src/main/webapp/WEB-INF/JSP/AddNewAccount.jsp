<%@ page isELIgnored="false" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.error {
	color: red
}
</style>
</head>
<body>
	<spring:form action="createNewAccount" method="post"
		modelAttribute="savings">
		<label> Enter Account Holder's Name:</label>
		<spring:input path="${savings.bankAccount.accountHolderName}"
			type="text" name="name" />
		<br>
		<br>
		<label> Enter Initial Account Balance:</label>
		<spring:input path="${savings.bankAccount.accountBalance}"
			type="number" name="bal" />
		<br>
		<br>
		<select name="sal">Is Salary <br> <%-- <spring:radiobutton path="salary" value="true" name="sal" />Yes
<spring:radiobutton path="salary" value="false" name="sal"/>No
 --%> <!-- <input type="radio" name="salary" value="true">yes<br><br>
<input type="radio" name="salary" value="false">false<br><br> -->
 <%-- <spring:radiobutton path="salary" value="true" label="yes" />
  <spring:radiobutton path="salary" value="false" label="no" /> --%>
  
  <option value="true">YES</option>
  <option value="false">NO</option>
		</select>
		<input type="submit" name="submit" />
		<input type="reset" name="reset" />
	</spring:form>
</body>
</html>
