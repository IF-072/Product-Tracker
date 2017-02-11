<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

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
                <table width="100%" class="table table-striped table-bordered table-hover" id="productData">
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
                            <td><"${store.name}"/></td>
                            <td><"${store.address}"/></td>
                            <td>products</td>
                            <td id="edit"><i class="fa fa-pencil fa-lg"></i></td>
                            <td id="delete"><i class="fa fa-times fa-lg"></i></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<p>
    <button type="button" class="btn btn-outline btn-primary" id="button1">AddStore</button>
</p>