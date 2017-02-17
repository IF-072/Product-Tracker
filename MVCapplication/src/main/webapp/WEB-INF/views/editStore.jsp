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
                <h3 class="panel-title">Edit Store</h3>
            </div>

            <div class="panel-body">

                <c:url var="addAction" value="/editStore/"/>

                <form:form role="form" action="${addAction}" method="POST" modelAttribute="store">
                    <fieldset>

                        <div class="form-group">
                            <label class="control-label" for="inputWarning">Store Name</label>
                            <form:input path="name" class="form-control" id="inputWarning"
                                        placeholder="Store Name" type="text"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="inputWarning">Store Address</label>
                            <form:input path="address" class="form-control" placeholder="Address" type="text"/>
                        </div>


                        <input type="submit" class="btn btn-lg btn-success btn-default" value="Submit"/>
                        <input type="reset"  class="btn btn-lg btn-warning btn-default" value="Cancel"/>
                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>
</div>

