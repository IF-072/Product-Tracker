<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<!DOCTYPE html>
<html>
<head>
    <title>UserStores</title>
</head>
<body>

<div class="col-lg-12">
    <h1 class="page-header">Stores</h1>
</div>

<div class="col-lg-12">
    <div class="panel panel-default">

        <div class="panel-body">
            <table width="100%" class="table table-striped table-bordered table-hover">
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
                        <td>"${store.name}"</td>
                        <td>"${store.address}"</td>
                    <td>
                        <p> <button type="button" class="btn btn-outline btn-link" id="productButton">products</button>
                        </p>
                    </td>
                    <td> <span class = "glyphicon glyphicon-edit"></span></td>
                    <td><span class = "glyphicon glyphicon-remove-circle"></span></td>
                </tr>
                </c:forEach>
        </div>
    </div>
</div>
<p>
    <button type="button" class="btn btn-outline btn-primary" id="button1">AddStore</button>
</p>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript">
    document.getElementById("productButton").onclick = function () {
        location.href = "../product";
    };
</script>

</body>
</html>



