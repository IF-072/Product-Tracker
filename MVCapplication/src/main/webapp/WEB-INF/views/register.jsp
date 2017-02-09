<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign Up to continue</h3>
                </div>
                <div class="panel-body">


                    <form:form role="form" method="POST" modelAttribute="registrationForm" class="form-horizontal">
                        <fieldset>

                            <!-- Text input-->
                            <div class="form-group">
                                <form:label path="email" cssClass="col-md-4 control-label">Your Email</form:label>
                                <div class="col-md-8">
                                    <form:input path="email" class="form-control" placeholder="address@example.com"
                                                type="email"/>
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <form:label path="name" cssClass="col-md-4 control-label">Name</form:label>
                                <div class="col-md-8">
                                    <form:input path="name" class="form-control" placeholder="John Doe"
                                                type="text"/>
                                </div>
                            </div>

                            <!-- Password input-->
                            <div class="form-group">
                                <form:label path="password" cssClass="col-md-4 control-label">Password</form:label>
                                <div class="col-md-8">
                                    <form:input path="password" class="form-control" placeholder="Password"
                                                name="password" type="password" value=""/>
                                </div>
                            </div>

                            <!-- Select Basic -->
                            <div class="form-group">
                                <label for="roleId" class="col-md-4 control-label">Account Type</label>
                                <div class="col-md-8">
                                    <select id="roleId" name="roleId" class="form-control">
                                        <c:forEach items="${roleMap}" var="currentRole">
                                            <option value="${currentRole.key}">${currentRole.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <input type="submit" class="btn btn-lg btn-success btn-block" value="Create Account"/>
                        </fieldset>
                    </form:form>


                </div>
            </div>
        </div>