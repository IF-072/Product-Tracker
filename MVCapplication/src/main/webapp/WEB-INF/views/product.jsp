<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<%--
  Created by Vitaliy Malisevych
  Date: 03.02.2017
  Time: 21:40
--%>

<style>
    #edit,#delete {text-align:center}
</style>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">My products</h1>
    </div>
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-body">

                <input type="button" onClick="window.location.href = 'addProduct'" value="Add new product">

                    <table width="100%" class="table table-striped table-bordered table-hover" id="productData">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Category</th>
                            <th>Unit</th>
                            <th>Image</th>
                            <th>Stores</th>
                            <th>Edit</th>
                            <th>Delete</th>
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
                                <td id="edit" onclick="edit(${product.id});"><i class="fa fa-pencil fa-lg"></i></td>
                                <td id="delete" onclick="del(${product.id})"><i class="fa fa-times fa-lg"></i></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </div>
        </div>
    </div>
</div>

<div id="dialogDelete" title="Delete product">
    <br/>
    Are you shure?
    <br/>

</div>




