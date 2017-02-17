<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<div><b>Add image to your product</b></div>
<br/>
<sf:form method="POST" modelAttribute="image" enctype="multipart/form-data">
    <sf:input type="file" path="multipartFile" id="imageToUpload"/>
    <input type="submit" value="Upload">
</sf:form>

<b>${message}</b>