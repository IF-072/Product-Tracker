<%--
  User: Pavlo Bendus
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="category.name"/></h1>
    </div>
</div>

<!-- Form -->
<div class="col-lg-6">
    <div class="form-group has-warning">

        <c:if test="${not empty errors}">
            <div class="alert alert-danger">
                <c:forEach items="${errors}" var="errorItem">
                    <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            ${errorItem.getDefaultMessage()}</p>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                       <spring:message code="error.categoryAlreadyExists" /></p>
            </div>
        </c:if>

        <spring:message code="category.nameExample" var="nameExample"/>
        <sf:form role="form" modelAttribute="category" method="post">
            <fieldset>
                <div class="form-group">
                    <label class="control-label" for="inputWarning"><spring:message code="category.name"/></label>
                    <sf:input path="name" class="form-control" id="inputWarning"
                              placeholder="${nameExample}" type="text"/>
                </div>
                <input type="submit" class="btn btn-success btn-success-custom" value="<spring:message code="add"/>"/>
                <input type="reset" class="btn btn-default btn-reset-custom" onclick="window.location.href='/category'" value="<spring:message code="cancel"/>" />
            </fieldset>
        </sf:form>
    </div>
</div>
