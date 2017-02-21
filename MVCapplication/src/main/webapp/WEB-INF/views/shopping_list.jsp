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
                        <th>Product</th>
                        <th>Amount</th>
                        <th>Image</th>
                        <th colspan="2">Edit</th>
                        <th>Delete</th>
                    </tr>
                    <form action="/shopping_list/edit" id="edit" method="post">
                        <input type="hidden" name="userId" id="user">
                        <input type="hidden" name="productId" id="product">
                        <input type="hidden" name="val" id="val">
                        <c:forEach items="${shoppingList}" var="elem">
                            <tr>
                                <td>${elem.product.name}</td>
                                <td>${elem.amount} ${elem.product.unit.name}</td>
                                <td><img src="<c:url value="/image/${elem.product.image.id}"/>" width="50" height="50" id="editImage"></td>

                                <td><a onclick="edit(${elem.user.id}, ${elem.product.id}, -1)" class="fa fa-minus-square fa-lg"/></td>
                                <td><a onclick="edit(${elem.user.id}, ${elem.product.id}, 1)" class="fa fa-plus-square fa-lg"/></td>
                                <td><a onclick="edit(${elem.user.id}, ${elem.product.id}, 0)" class="fa fa-times fa-lg"/></td>
                            </tr>
                        </c:forEach>
                    </form>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
        <!-- /.panel-body -->
    </div>
    <!-- /.panel -->
</div>