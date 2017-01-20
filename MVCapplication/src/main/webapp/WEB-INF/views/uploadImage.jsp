<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Upload Image</title>
</head>
<body>

<form:form method="POST" modelAttribute="image" enctype="multipart/form-data">
   <form:input type="file" path="multipartFile" id="imageToUpload"/>
   <input type="submit" value="Upload">
</form:form>

<b>${message}</b>
</body>
</html>
