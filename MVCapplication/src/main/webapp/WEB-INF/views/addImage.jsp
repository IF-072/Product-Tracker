<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Add image to product</h1>
        <h3 class="page-header">${product.name} (${product.description})</h3>

    </div>
</div>

<div class="col-lg-6">
    <div class="form-group has-warning">
        <div class="panel-body">
            <sf:form role="form" method="POST" modelAttribute="image" enctype="multipart/form-data">
                <sf:input type="file" path="multipartFile" id="imageToUpload"/>
                <br/>
                <input type="submit" class="btn btn-lg btn-success" value="Upload">
            </sf:form>
        </div>
    </div>
</div>


<b>${message}</b>