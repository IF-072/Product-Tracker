<link rel="stylesheet" type="text/css" href="/css/product.css"/>
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

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <input type="button" onClick="window.location.href ='<c:url value= "/product/addProduct"/>'"
                       value="Add new product">
                    <table width="100%" class="table table-striped" id="productData">
                        <thead>
                        <tr>
                            <th>ID</th>
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
                                <td><c:out value="${product.id}"></c:out></td>
                                <td><c:out value="${product.name}"></c:out></td>
                                <td><c:out value="${product.description}"></c:out></td>
                                <td><c:out value="${product.category.name}"></c:out></td>
                                <td><c:out value="${product.unit.name}"></c:out></td>
                                <td><c:out value="${product.image.id}"></c:out></td>
                                <td><c:url value="../stores/">Stores</c:url></td>
                                <td><p class="fa fa-pencil fa-lg" id="edit"></p></td>
                                <td onclick="deleteProduct(${product.id});" id="delete"><p class="fa fa-times fa-lg" ></p></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </div>
        </div>
    </div>
    <div id="dialogDelete" title="Delete product">
    <br/>
    <b>Are you shure?</b>
    <br/>
    </div>
</div>









