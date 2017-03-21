<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Vitaliy Malisevych
  Date: 03.02.2017
  Time: 21:40
--%>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="product.myProducts"/></h1>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-body">
                    <input type="button" class="btn btn-outline btn-primary"
                           onClick="window.location.href = '/product/addProduct'"
                           value=<spring:message code="product.addNewProductBut"/>>
                    <table width="100%" class="table table-striped table-bordered table-hover" id="productData">
                        <thead>
                        <tr>
                            <th><spring:message code="product.name"/></th>
                            <th><spring:message code="product.description"/></th>
                            <th><spring:message code="product.category"/></th>
                            <th><spring:message code="product.unit"/></th>
                            <th><spring:message code="product.image"/></th>
                            <th><spring:message code="product.stores"/></th>
                            <th><spring:message code="product.shopList"/></th>
                            <th><spring:message code="product.edit"/></th>
                            <th><spring:message code="product.delete"/></th>
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
                                       id="goStores"><spring:message code="product.stores"/></p>
                                </td>
                                <td style="text-align:center; vertical-align: middle">
                                    <button type="button" class="btn btn-outline btn-primary"
                                            onclick="addProductToShoppingList(${product.id});"
                                            id="addToShoppingList">
                                        <spring:message code="product.addTo"/><br/>
                                        <spring:message code="product.ShoppingList"/>
                                    </button>
                                </td>
                                <td style="text-align:center; vertical-align: middle">
                                    <p onclick="document.location.href='/product/editProduct?productId=${product.id}'"
                                       id="edit" class="fa fa-pencil fa-lg"></p>
                                </td>
                                <td style="text-align:center; vertical-align: middle">
                                    <p onclick="deleteProduct(${product.id});" class="fa fa-times fa-lg" id="delete"></p>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </div>
        </div>
    </div>
</div>
<div id="Show" hidden><spring:message code="table.show"/></div>
<div id="Products" hidden><spring:message code="table.products"/></div>
<div id="showing" hidden><spring:message code="table.showing"/></div>
<div id="to" hidden><spring:message code="table.to"/></div>
<div id="of" hidden><spring:message code="table.of"/></div>
<div id="records" hidden><spring:message code="table.recordsProduct"/></div>
<div id="search" hidden><spring:message code="table.search"/></div>
<div id="previous" hidden><spring:message code="table.previous"/></div>
<div id="next" hidden><spring:message code="table.next"/></div>
<div id="non" hidden><spring:message code="table.non"/></div>




