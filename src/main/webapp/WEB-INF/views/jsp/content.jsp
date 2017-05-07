<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Message: ${msg}</h2>
<h2>Students:</h2>
<div>
    <table>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>AGE</th>
        </tr>
        <c:forEach var="student" items="${students}">
            <tr>
                <td><c:out value="${student.id}" /></td>              
                <td><c:out value="${student.name}" /></td>              
                <td><c:out value="${student.age}" /></td>                
            </tr>
        </c:forEach>
    </table>
</div>
<form method="post">
	<div style="width: 15%; height: 100px">123</div>
	<textarea style="float:left">Next, get a free TinyMCE Cloud API key!</textarea>
</form>