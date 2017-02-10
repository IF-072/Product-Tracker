<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<%--
  Created by Nazar Vynnyk
--%>

<style>
    #edit,#delete {text-align:center}
</style>

<div class="row">
<div class="col-lg-12">
    <h1 class="page-header">Stores</h1>
</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-body">

                <button type="button" class="btn btn-outline btn-primary" id="addStoreButton">AddStore</button>
                <button type="button" class="btn btn-outline btn-primary" id="addProductButton">AddProducts</button>

                    <table width="100%" class="table table-striped table-bordered table-hover" id="storeTab">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Address</th>
                                <th>Products at store</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${stores}" var="store">
                            <tr class="gradeA">
                                    <td id="name">${store.name}</td>
                                    <td id="address">${store.address}</td>
                                    <td> <button type="button" class="btn btn-outline btn-link"
                                                onclick="location.href='../product/';" id="productButton"> products</button>
                                    </td>
                                    <td id="edit"><i class="fa fa-pencil fa-lg"></i></td>
                                    <td id="delete"><i class="fa fa-times fa-lg"></i></td>
                            </tr>
                        </c:forEach>
                        <tbody>
                    </table>
         </div>

    </div>
</div>
</div>
