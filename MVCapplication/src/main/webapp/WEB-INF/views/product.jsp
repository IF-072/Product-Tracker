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
    #image, #edit,#delete, #goStores, #addImage, #addImage, #addToShoppingList {text-align:center; cursor: pointer}
    #image {text-align:center}
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
                <input type="button" class="btn btn-outline btn-primary" onClick="window.location.href = 'addProduct'"
                       value="Add new product">
                    <table width="100%" class="table table-striped" id="productData">
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
                                <td onclick="addProductToShoppingList(${product.id});"
                                id="addToShoppingList">
                                    <p class="fa fa-plus fa-lg"></p>
                                </td>
                                <td id="edit">
                                    <p onclick="document.location.href='/product/editProduct?id=${product.id}'"
                                       class="fa fa-pencil fa-lg"></p>
                                </td>
                                <td onclick="deleteProduct(${product.id});" id="delete">
                                    <p class="fa fa-times fa-lg" ></p>
                                </td>
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









