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
        <h1 class="page-header">My products</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-body">

                <button id="addRow">Add new product</button>
                <button id="deleteRow">Delete product</button>

                    <table width="100%" class="table table-striped table-bordered table-hover" id="productData">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Category</th>
                            <th>Unit</th>
                            <th>Image</th>
                            <th>Stores</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${products}" var="product">
                            <tr class="gradeA">
                                <td><c:out value="${product.name}"></c:out></td>
                                <td><c:out value="${product.description}"></c:out></td>
                                <td><c:out value="${product.category.name}"></c:out></td>
                                <td><c:out value="${product.unit.name}"></c:out></td>
                                <td><c:out value="${product.image}"></c:out></td>
                                <td><c:out value="${product.image}"></c:out></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                <!-- /.table-responsive -->
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>

<div id="dialogAdd" title="Add new product">
    Please fill in the form.
    <br/>
    <sf:form modelAttribute="product" method="post">
        <span>
                <label for="name">Please enter product's name</label>
                <input type="text" name="name" id="name">
            </span>
        <br/>
        <span>
                <label for="description">Please enter product's description</label>
                <input type="text" name="description" id="description">
            </span>
        <br/>
        <span>
                <label for="image">Please chose image for product</label>
                <input type="text" name="image" id="image">
            </span>
        <br/>
        <span>
                <label for="category">Please chose product's category</label>
                <input type="text" name="category" id="category">
            </span>
        <br/>
        <span>
                <label for="unit">Please chose product's unit</label>
                <input type="text" name="unit" id="unit">
            </span>
        <br/>
        <span>
                <label for="stores">Please select stores where you can buy this product</label>
                <input type="text" name="stores" id="stores">
        </span>

    </sf:form>
</div>




