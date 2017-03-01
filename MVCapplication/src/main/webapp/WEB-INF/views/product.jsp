<link rel="stylesheet" type="text/css" href="/css/product.css"/>
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
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <input type="button" class="btn btn-outline btn-primary" onClick="window.location.href = '/product/addProduct'"
                       value="Add new product">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="productData">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Category</th>
                            <th>Unit</th>
                            <th>Image</th>
                            <th>Stores</th>
                            <th>ShopList</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${products}" var="product">
                            <tr class="gradeA">
                                <td>${product.name}</td>
                                <td>${product.description}</td>
                                <td>${product.category.name}</td>
                                <td>${product.unit.name}</td>
                                <td id="image">
                                    <c:if test="${product.image.id == null}">
                                    <p id="addImage"
                                       onclick="document.location.href='/image/upload?productId=${product.id}'">
                                        Add image</p>
                                    </c:if>
                                    <c:if test="${product.image.id != null}">
                                    <img src="../image/${product.image.id}" width="50" height="50" id="editImage"
                                    onclick="document.location.href='/image/edit?productId=${product.id}'">
                                    </c:if>
                                </td>
                                <td>
                                    <p onclick="document.location.href='/product/stores?productId=${product.id}'"
                                       id="goStores">Stores</p>
                                </td>
                                <td style="text-align:center; vertical-align: middle">
                                    <button type="button" class="btn btn-default"
                                            onclick="addProductToShoppingList(${product.id});"
                                            id="addToShoppingList">
                                        Add to<br/>Shopping list
                                    </button>
                                </td>
                                <td style="text-align:center; vertical-align: middle">
                                    <p onclick="document.location.href='/product/editProduct?productId=${product.id}'"
                                       id="edit" class="fa fa-pencil fa-2x"></p>
                                </td>
                                <td style="text-align:center; vertical-align: middle">
                                    <p onclick="deleteProduct(${product.id});" class="fa fa-times fa-2x" id="delete"></p>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </div>
        </div>
    </div>
</div>









