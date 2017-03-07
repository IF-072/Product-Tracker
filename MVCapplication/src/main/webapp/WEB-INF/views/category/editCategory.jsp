<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--
  User: Pavlo Bendus
--%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="category.edit"/></h1>
    </div>
</div>

<!-- Form -->
<div class="col-lg-6">
    <div class="form-group has-warning">
        <spring:message code="category.nameExample" var="nameExample"/>
        <sf:form role="form" modelAttribute="category" method="post">
            <fieldset>
                <div class="form-group">
                    <label class="control-label" for="inputWarning"><spring:message code="category.name"/></label>
                    <sf:input path="name" class="form-control" id="inputWarning"
                              placeholder="${nameExample}" type="text"/>
                </div>
                <input type="submit" class="btn btn-success btn-success-custom" value="<spring:message code="submit"/>"/>
                <input type="reset" class="btn btn-default btn-reset-custom" onclick="location.href='/category'" value="<spring:message code="cancel"/>" />
            </fieldset>
        </sf:form>
    </div>
</div>
