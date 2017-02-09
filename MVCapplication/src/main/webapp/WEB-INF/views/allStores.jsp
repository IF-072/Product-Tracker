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

        <!-- /.panel-heading -->

        <div class="panel-body">
            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Products at store</th>
                    <th colspan="2">Edit </th>
                    <th colspan="2">/ Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${stores}" var="store">
                <tr class="gradeA">
                        <td><"${store.name}"/></td>
                        <td><"${store.address}"/></td>
                    <td>
                        <button type="button" class="btn btn-outline btn-link"
                                onclick="<c:url value="../product"/>">products</button>
                    </td>
                </tr>
                </c:forEach>
        </div>
    </div>
</div>
<p>
    <button type="button" class="btn btn-outline btn-primary" id="button1">AddStore</button>
</p>


<%--<input type="button" value="Click Me" id="button1" />--%>
<%--<script type="text/javascript">--%>
<%--$('document').ready(function () {--%>
<%--$('#button1').click(function () {--%>
<%--alert('jQuery Tutorial');--%>
<%--});--%>
<%--});--%>
<%--</script>--%>


<script type="text/javascript">
    window.onload = function ()
    {
        // For all modern browsers
        if (document.addEventListener)
        {
            document.getElementById('button1')
                    .addEventListener('click', clickHandler, false);
        }
        else
        // For Internet Explorer < 9
        {
            document.getElementById('button1')
                    .attachEvent('onclick', clickHandler);
        }

        function clickHandler()
        {
            alert('jQuery Tutorial');
        }
    };
</script>
<%--<input type="button" value="Click Me" id="button1" />--%>


</body>
</html>



