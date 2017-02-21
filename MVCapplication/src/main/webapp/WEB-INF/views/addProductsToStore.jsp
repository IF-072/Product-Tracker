<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Nazar Vynnyk
 --%>

<div class="row">

    <div class="col-lg-6">
        <h1 class="page-header">
            <b>Add products to Store</b>
            <br>
             <b> ${myStore.name} (${myStore.address})</b>
        </h1>
    </div>
</div>
<div class="row">
    <div class="panel-body">

        <c:url var="addAction" value="/addProductsToStore"/>
        <form:form role="form" action="${addAction}" modelAttribute="wrapedProducts" method="post">

            <%--<form:errors path="*" cssClass="errorblock" element="div"/>--%>
            <table class="table table-striped" id="storeTab">


                <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Add</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="product">
                    <tr class="gradeA">
                        <td>${product.name}</td>
                        <td>${product.description}</td>
                        <td><form:checkbox path="products" value="${product.id}"/></td>
                          </tr>
                </c:forEach>
                </tbody>
            </table>
            <input type="submit" class="btn btn-lg btn-success btn-default"
                   value="Submit adding"/>
            <input type="hidden" name="storeId" value="${myStore.id}" />
        </form:form>
    </div>
</div>

