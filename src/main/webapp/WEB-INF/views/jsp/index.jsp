<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=b1zvqbb7541m3yvpup44ku8xobusklb6q1dv6512gl03t056"></script>
<script>tinymce.init({ 
	selector:'textarea',
	width: '85%',
	plugins: "save",
	menubar: false,
	toolbar: 'save | undo redo'
	  
});</script>
<title>Spring MVC + Embedded database example</title>
</head>
<body>
<h1>Spring MVC + Embedded database example</h1>
<h2> ${hello} </h2>
<%@ include file="menu.html" %>
<jsp:include page="${request.contextPath}/content"></jsp:include>
<%-- <%@ include file="hello.jsp" %> --%>
</body>
</html>