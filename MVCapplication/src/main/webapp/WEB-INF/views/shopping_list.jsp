<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="panel panel-default">
        <div class="panel-heading">
            Shopping list
        </div>
        <!-- /.panel-heading -->
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <tbody>
                    <tr>
                        <th>#</th>
                        <th>Product</th>
                        <th>Amount</th>
                        <th>Image</th>
                        <th colspan="2">Edit</th>
                        <th>Delete</th>
                    </tr>
                    <c:forEach items="${shoppingList}" var="elem" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td>${elem.product.name}</td>
                            <td id="am${loop.count}">${elem.amount} ${elem.product.unit.name}</td>
                            <td>
                                <c:if test="${elem.product.image != null}">
                                    <img src="<c:url value="/image/${elem.product.image.id}"/>" width="50" height="50">
                                </c:if>
                                <c:if test="${elem.product.image == null}">
                                    <img src="<c:url value="/img/noimage.jpg"/>" width="50" height="50">
                                </c:if>
                            </td>
                            <td><a onclick="edit(${elem.product.id}, -1, ${loop.count})"
                                   class="fa fa-minus-square fa-lg"/></td>
                            <td><a onclick="edit(${elem.product.id}, 1, ${loop.count})"
                                   class="fa fa-plus-square fa-lg"/></td>
                            <td><a prodId="${elem.product.id}" class="fa fa-times fa-lg del"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
        <!-- /.panel-body -->
    </div>
    <!-- /.panel -->
</div>