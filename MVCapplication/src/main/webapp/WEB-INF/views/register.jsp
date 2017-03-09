<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code="signIn"/> </h3>
                </div>
                <div class="panel-body">


                    <form:form role="form" method="POST" modelAttribute="registrationForm" class="form-horizontal">
                        <fieldset>

                            <!-- Error messages-->
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger">
                                    <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                    <spring:message code="${errorMessage}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty validationErrors}">
                                <div class="alert alert-danger">
                                    <c:forEach items="${validationErrors}" var="errorItem">
                                        <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                                ${errorItem.getDefaultMessage()}</p>
                                    </c:forEach>
                                </div>
                            </c:if>

                            <!-- Text input-->
                            <div class="form-group">
                                <form:label path="email" cssClass="col-md-4 control-label"> <spring:message code='email'/></form:label>
                                <div class="col-md-8">
                                    <form:input path="email" class="form-control" placeholder="address@example.com"
                                                type="email"/>
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <form:label path="name" cssClass="col-md-4 control-label"><spring:message code='userName'/></form:label>
                                <div class="col-md-8">
                                    <spring:message code='username' var="usernameMessage"/>
                                    <form:input path="name" class="form-control" placeholder="${usernameMessage}"
                                                type="text"/>
                                </div>
                            </div>

                            <!-- Password input-->
                            <div class="form-group">
                                <form:label path="password" cssClass="col-md-4 control-label"><spring:message code='password'/></form:label>
                                <div class="col-md-8">
                                    <spring:message code='password' var="passwordMessage"/>
                                    <form:input path="password" class="form-control" placeholder="${passwordMessage}"
                                                name="password" type="password" value=""/>
                                </div>
                            </div>

                            <!-- Confirm Password input-->
                            <div class="form-group">
                                <form:label path="confirmPassword" cssClass="col-md-4 control-label"><spring:message code='confirmPassword'/></form:label>
                                <div class="col-md-8">
                                    <form:input path="confirmPassword" class="form-control" placeholder="${passwordMessage}"
                                                name="confirmPassword" type="password" value=""/>
                                </div>
                            </div>
                            <spring:message code='createAccountButton' var="createAccount"/>
                            <input type="submit" class="btn btn-lg btn-success btn-block" value="${createAccount}"/>
                        </fieldset>
                    </form:form>


                </div>
            </div>
        </div>