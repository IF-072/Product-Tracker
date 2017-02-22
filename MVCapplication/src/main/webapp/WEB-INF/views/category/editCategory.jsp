<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%--
  User: Pavlo Bendus
--%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Edit category</h1>
    </div>
</div>

<!-- Form -->
<div class="col-lg-6">
    <div class="form-group has-warning">
            <sf:form role="form" modelAttribute="category" method="post">
                <fieldset>
                    <div class="form-group">
                        <label class="control-label" for="inputWarning">Category name</label>
                        <sf:input path="name" class="form-control" id="inputWarning"
                                  placeholder="Category name" type="text"/>
                    </div>
                    <input type="submit" class="btn btn-success btn-success-custom" value="Submit"/>
                    <input type="reset" class="btn btn-default btn-reset-custom" onclick="location.href='/category'" value="Cancel" />
                </fieldset>
            </sf:form>
    </div>
</div>
