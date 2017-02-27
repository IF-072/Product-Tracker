<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<%--
  Created by Vitaliy Malisevych
  Date: 27.02.2017
  Time: 18:29
--%>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Product with such name has been deleted</h1>
        <h3>Do you want to restore the product?</h3>
    </div>
</div>

<br/>

<div class="col-lg-6">
    <div class="form-group has-warning">
        <div class="panel-body">
            <sf:form role="form" modelAttribute="product" method="post" action="/product/restore">
                <fieldset>
                    <div class="form-group">
                        <label class="control-label" for="inputWarning">Product name</label>
                        <sf:input path="name" class="form-control" id="inputWarning"
                                  placeholder="Product name" type="text" disabled="true"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Product description</label>
                        <sf:input path="description" class="form-control" placeholder="Description"
                                  type="text" disabled="true"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Unit</label>
                        <sf:input path="unit.name" class="form-control" placeholder="Unit"
                                  type="text" disabled="true"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Category</label>
                        <sf:input path="category.name" class="form-control" placeholder="Category"
                                  type="text" disabled="true"/>
                    </div>
                    <sf:hidden path="user.id"/>
                    <sf:hidden path="id"/>
                    <input type="submit" class="btn btn-lg btn-success btn-block" value="Restore product"/>
                </fieldset>
            </sf:form>
        </div>
    </div>
</div>
