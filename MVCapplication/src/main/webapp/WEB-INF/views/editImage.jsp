<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<b>Change or delete image for your product: ${product.name}</b>

<i>Current image</i>
<br/>
<img src="../image/${product.image.id}" width="100" height="100">
<br/>
<input type="button" onClick="window.location.href = '/image/delete?id=${product.image.id}'" value="Delete image">


<sf:form method="POST" modelAttribute="image" enctype="multipart/form-data">
    <sf:input type="file" path="multipartFile" id="imageToUpload"/>
    <input type="submit" value="Change image">
</sf:form>