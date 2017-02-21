<%--
  Created by: Pavlo Bendus
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">My Categories</h1>
    </div>
</div>

<!-- Table with the list of categories -->
<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-default">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">ID</th>
                        <th class="text-center">Name</th>
                        <th class="text-center">Edit</th>
                        <th class="text-center">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${categories}" var="category" varStatus="loop">
                        <tr class="gradeA">
                            <td>${loop.count}</td>
                            <td>${category.name}</td>
                            <td class="text-center"><a href="#"><i onclick="document.location.href='/categories/edit?id=${category.id}'" class="fa fa-pencil fa-lg"></i></a></td>
                            <td onclick="deleteCategory(${category.id})" class="text-center"><a href="#"><i  class="fa fa-trash-o fa-lg"></i></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
        </div>
    </div>
</div>

<div id="deleteForm">
    <h2 class="text-center">Are you sure?</h2>
    <br />
    <br />
    <div class="center-block text-center">
        <button onclick="acceptDeleting()" class="btn btn-success btn-reset-custom">Yes</button>
        <button onclick="cancelDeleting()" class="btn btn-default btn-reset-custom">No</button>
    </div>
</div>
