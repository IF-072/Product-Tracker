<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="image.changeOrDeleteImageForYourProduct"/></h1>
        <h3 class="page-header">${product.name} (${product.description})</h3>

    </div>
</div>



<div class="col-lg-6">
    <div class="form-group has-warning">
        <div class="panel-body">
            <h3><spring:message code="image.currentImage"/></h3>
            <br/>
            <img src="../image/${product.image.id}" width="100" height="100">
            <br/>
            <input type="button" class="btn btn-lg btn-success"
                   onClick="window.location.href = '/image/delete?id=${product.image.id}'" value=<spring:message code="image.deleteImage"/>>
            <br/>
            <h3><spring:message code="image.orYouCanChooseAnotherImage"/></h3>
            <sf:form method="POST" role="form" modelAttribute="image" enctype="multipart/form-data">
                <sf:input type="file" path="multipartFile" id="imageToUpload"/>
                <br/>
                <input type="submit" class="btn btn-lg btn-success btn-custom" value=<spring:message code="image.changeImage"/> disabled>
                <input type="reset" class="btn btn-lg btn-reset-custom"
                       onclick="document.location.href='<c:url value="/product/"/>'" value=<spring:message code="product.cancel"/>>
            </sf:form>
        </div>
    </div>
</div>