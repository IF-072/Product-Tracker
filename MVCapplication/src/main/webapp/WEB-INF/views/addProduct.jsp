<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<%--
  Created by Vitaliy Malisevych
  Date: 03.02.2017
  Time: 21:40
--%>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Add product</h1>
    </div>
</div>

<br/>

<div class="col-lg-6">
    <div class="form-group has-warning">
        <div class="panel-body">
            <sf:form role="form" modelAttribute="product" method="post">
                <fieldset>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">
                                <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                    ${errorMessage}
                            </div>
                        </c:if>
                    <div class="form-group">
                        <label class="control-label" for="inputWarning">Product name</label>
                        <sf:errors path="name" cssClass="form-control label-danger"/>
                        <sf:input path="name" class="form-control" id="inputWarning"
                                    placeholder="Product name" type="text"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Product description</label>
                        <sf:errors path="description" cssClass="form-control label-danger"/>
                        <sf:input path="description" class="form-control" placeholder="Description" type="text"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Unit</label>
                        <sf:errors path="unit" cssClass="form-control label-danger"/>
                        <sf:select path="unit.id" class="form-control" placeholder="Unit">
                            <sf:option label="--- Select ---" value="-1"/>
                            <sf:options items="${units}" itemLabel="name" itemValue="id"/>
                        </sf:select>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Category</label>
                        <sf:errors path="category" cssClass="form-control label-danger"/>
                        <sf:select path="category.id" class="form-control" placeholder="Category">
                            <sf:option label="--- Select ---" value="-1"/>
                            <sf:options items="${categories}" itemLabel="name" itemValue="id"/>
                        </sf:select>
                    </div>
                    <input type="submit" class="btn btn-lg btn-success btn-block" value="Add product"/>
                </fieldset>
            </sf:form>
        </div>
    </div>
</div>
