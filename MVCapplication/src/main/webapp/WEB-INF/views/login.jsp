<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code="pleaseSignIn"/></h3>
                </div>
                <div class="panel-body">
                    <form:form role="form" method="POST" modelAttribute="loginForm">
                        <fieldset>
                            <c:if test="${not empty successMessage}">
                                <div class="alert alert-success">
                                    <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
                                    <spring:message code="${successMessage}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty informationMessage}">
                                <div class="alert alert-warning">
                                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                    <spring:message code="${informationMessage}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty loginError}">
                                <div class="alert alert-danger">
                                    <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                    <spring:message code="${loginError}"/>
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
                                <spring:message code='email' var="emailMessage"/>
                                <form:input path="email" class="form-control" placeholder="${emailMessage}" type="email" />
                            </div>
                            <div class="form-group">
                                <spring:message code='password' var="passwordMessage"/>
                                <form:input path="password" class="form-control" placeholder="${passwordMessage}" name="password" type="password" value=""/>
                            </div>
                            <input type="submit" class="btn btn-lg btn-success btn-block" value="<spring:message code="login"/>"/>
                        </fieldset>
                    </form:form>
                    <div style="margin-top:8px;"><a href="<c:url value="../register"/>"><spring:message code="createAccount"/></a></div>
                </div>
            </div>
        </div>
    </div>
</div>