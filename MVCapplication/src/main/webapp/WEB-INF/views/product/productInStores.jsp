<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><b>${product.name} (${product.description})</b></h1>
        <h1><spring:message code="product.youCanBuyInStores"/></h1>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <sf:form modelAttribute="storesInProduct" method="post">
                    <sf:checkboxes element="li" path="storesId" items="${storesId}"/>
                    <input type="submit" class="btn btn-lg btn-success"
                           value=<spring:message code="product.submitChanges"/>>
                </sf:form>
            </div>
        </div>
    </div>
</div>
