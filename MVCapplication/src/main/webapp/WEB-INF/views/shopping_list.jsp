<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
.

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
                        <th colspan="2">Edit</th>
                        <th>Delete</th>
                    </tr>
                    <c:forEach items="${shoppingList}" var="elem">
                        <tr>
                            <td>${elem.product.name}</td>
                            <td>${elem.amount}</td>
                            <td><a href="/shopping_list/decrease" class="fa fa-minus-square fa-lg"/></td>
                            <td><a href="/shopping_list/increase?id=7" class="fa fa-plus-square fa-lg"/></td>
                            <td><a class="fa fa-times fa-lg"/></td>
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