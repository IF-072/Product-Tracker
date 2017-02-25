<link rel="stylesheet" type="text/css" href="/css/product.css"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Nazar Vynnyk
--%>

<style>
    #image, #delete {text-align:center; cursor: pointer}
</style>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Products in store</h1>
        <h1> ${store.name} (${store.address})</h1>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <button type="button" class="btn btn-outline btn-primary" onclick=
                        "location.href='/addProductsToStore?storeId=${store.id}'"
                        id="addProductButton">AddProducts
                </button>
                <br>
                <br>

                <table width="100%" class="table table-striped table-bordered table-hover" id="storeTab">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Category</th>
                        <th>Unit</th>
                        <th>Image</th>
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
                                <c:if test="${product.image.id != null}">
                                    <img src="../image/${product.image.id}" width="50" height="50">
                                </c:if>
                            </td>

                            <td onclick="dellProduct(${store.id}, ${product.id});" id="delete">
                                <p class="fa fa-times fa-lg" ></p>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div id="dialogDel" title="Delete product">
        <br/>
        <b>Are you sure?</b>
        <br/>
    </div>
</div>









