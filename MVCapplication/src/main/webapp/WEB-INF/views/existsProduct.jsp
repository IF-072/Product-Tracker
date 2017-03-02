<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Vitaliy Malisevych
  Date: 27.02.2017
  Time: 18:29
--%>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="product.productWithSuchNameHasBeenDeleted"/></h1>
        <h3><spring:message code="product.doYouWantToRestoreTheProduct"/></h3>
    </div>
</div>

<br/>

<div class="col-lg-6">
    <div class="form-group">
        <div class="panel-body">
            <sf:form role="form" modelAttribute="product" method="post" action="/product/restore">
                <fieldset>
                    <div class="form-group">
                        <label class="control-label" for="inputWarning">
                            <spring:message code="product.productName"/>
                        </label>
                        <sf:input path="name" class="form-control" id="inputWarning"
                                  type="text" disabled="true"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            <spring:message code="product.productDescription"/>
                        </label>
                        <sf:input path="description" class="form-control"
                                  type="text" disabled="true"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            <spring:message code="product.unit"/>
                        </label>
                        <sf:input path="unit.name" class="form-control"
                                  type="text" disabled="true"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            <spring:message code="product.category"/>
                        </label>
                        <sf:input path="category.name" class="form-control"
                                  type="text" disabled="true"/>
                    </div>
                    <sf:hidden path="user.id"/>
                    <sf:hidden path="id"/>
                    <input type="submit" class="btn btn-lg btn-success btn-custom"
                           value=<spring:message code="product.restoreProduct"/>>
                    <input type="reset" class="btn btn-lg btn-reset-custom btn-reset-custom"
                           onclick="document.location.href='<c:url value="/product/"/>'"
                           value=<spring:message code="product.cancel"/>>
                </fieldset>
            </sf:form>
        </div>
    </div>
</div>
