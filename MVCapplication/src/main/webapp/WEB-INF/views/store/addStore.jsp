<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Nazar Vynnyk
 --%>

<div class="row">
    <div class="col-lg-6">
        <div class="form-group has-warning">
            <div class="panel-heading">
                <h1 class="page-header">Add new Store</h1>
            </div>

            <div class="panel-body">

                <c:url var="addAction" value="/addStore/"/>

                <form:form role="form" action="${addAction}" method="POST" modelAttribute="store">
                    <fieldset>

                        <c:if test="${not empty errorMessages}">
                            <div class="alert alert-danger">
                                <c:forEach items="${errorMessages}" var="errorItem">
                                    <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                            ${errorItem.getDefaultMessage()}</p>
                                </c:forEach>
                            </div>
                        </c:if>

                        <div class="form-group">
                            <label class="control-label" for="inputWarning">Store Name</label>
                            <form:input path="name" class="form-control" id="inputWarning"
                                        placeholder="Store Name" type="text"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="inputWarning">Store Address</label>
                            <form:input path="address" class="form-control" placeholder="Address" type="text"/>
                        </div>

                        <input type="submit" class="btn btn-lg btn-success btn-custom" value="Add Store"/>
                        <input type="reset" class="btn btn-lg btn-reset-custom btn-reset-custom"
                               onclick="document.location.href='<c:url value="/stores/"/>'" value="Cancel" />

                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>
</div>

