<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Vitaliy Malisevych
  Date: 14.02.2017
  Time: 22:51
--%>



<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="product.editProduct"/></h1>
    </div>
</div>

<br/>

<div class="col-lg-6">
    <div class="form-group">
        <div class="panel-body">
            <sf:form role="form" modelAttribute="product" method="post">
                <fieldset>
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                ${message}
                        </div>
                    </c:if>
                    <c:if test="${not empty errorMessages}">
                        <div class="alert alert-danger">
                            <c:forEach items="${errorMessages}" var="errorItem">
                                <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                        ${errorItem.getDefaultMessage()}</p>
                            </c:forEach>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label class="control-label" for="inputWarning">
                            <spring:message code="product.productName"/>
                        </label>
                        <sf:input path="name" class="form-control" id="inputWarning"
                                  placeholder="Product name" type="text"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">
                            <spring:message code="product.productDescription"/>
                        </label>
                        <sf:input path="description" class="form-control" placeholder="Description" type="text"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">
                            <spring:message code="product.unit"/>
                        </label>
                        <sf:select path="unit.id" class="form-control" placeholder="Unit">
                            <sf:option label="${product.unit.name}" value="${product.unit.id}"/>
                            <sf:options items="${units}" itemLabel="name" itemValue="id"/>
                        </sf:select>
                    </div>
                    <div class="form-group">
                        <label class="control-label">
                            <spring:message code="product.category"/>
                        </label>
                        <sf:select path="category.id" class="form-control" placeholder="Category">
                            <sf:option label="${product.category.name}" value="${product.category.id}"/>
                            <sf:options items="${categories}" itemLabel="name" itemValue="id"/>
                        </sf:select>
                        <sf:hidden path="image.id"/>
                        <sf:hidden path="id"/>
                    </div>
                    <input type="submit" class="btn btn-lg btn-success btn-custom"
                           value=<spring:message code="product.editProductBut"/>/>
                    <input type="reset" class="btn btn-lg btn-reset-custom btn-reset-custom"
                           onclick="document.location.href='<c:url value="/product/"/>'"
                           value=<spring:message code="product.cancel"/>>
                </fieldset>
            </sf:form>
        </div>
    </div>
</div>