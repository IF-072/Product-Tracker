<%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">My Cart</h1>
    </div>
</div>

<!-- Table -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Store: ${carts[0].store.name}
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="text-center table-number-width">#</th>
                        <th class="text-center">Product</th>
                        <th class="text-center">Description</th>
                        <th class="text-center">Category</th>
                        <th class="text-center table-toBeBought-width">To be bought</th>
                        <th class="text-center table-bought-width">Bought</th>
                        <th class="text-center table-delete-width">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${carts}" var="cart" varStatus="status">
                        <tr class="gradeA">
                            <td>${status.count}</td>
                            <td>${cart.product.name}</td>
                            <td>${cart.product.description}</td>
                            <td>${cart.product.category.name}</td>
                            <td class="text-center">
                                    ${cart.amount} ${cart.product.unit.name}
                                <a href="#"/><i class="fa fa-edit fa-fw"/>
                            </td>
                            <td class="text-center">
                                <div class="input-append">
                                    <form method="POST" action="bought">
                                        <input type="hidden" name="userId" value="${cart.user.id}"/>
                                        <input type="hidden" name="storeId" value="${cart.store.id}"/>
                                        <input type="hidden" name="productId" value="${cart.product.id}"/>
                                        <input class="span2 number table-input-number-width" id="appendedInputButton"
                                               size="16" type="number" name="amount" min="1" value="${cart.amount}"/>
                                        <input class="span2" type="button" class="btn btn-default boughtOkBtn"
                                               value="Ok"/>
                                    </form>
                                </div>
                            </td>
                            <td class="text-center"><a href="#"><i class="fa fa-trash-o fa-fw"></i></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


