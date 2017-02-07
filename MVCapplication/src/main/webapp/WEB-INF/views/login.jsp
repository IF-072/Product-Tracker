<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                    <form:form role="form" method="POST" modelAttribute="loginForm">
                        <fieldset>
                            <c:if test="${not empty loginError}">
                                <div class="alert alert-danger">
                                    <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                    <c:out value="${loginError}" />
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
                                <form:input path="email" class="form-control" placeholder="E-mail" type="email" />
                            </div>
                            <div class="form-group">
                                <form:input path="password" class="form-control" placeholder="Password" name="password" type="password" value=""/>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="true">Remember Me
                                </label>
                            </div>
                            <input type="submit" class="btn btn-lg btn-success btn-block" value="Login"/>
                        </fieldset>
                    </form:form>
                </div>
            </div>
        </div>