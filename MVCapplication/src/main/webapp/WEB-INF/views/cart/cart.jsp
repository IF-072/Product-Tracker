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
                            <td class="productName">${cart.product.name}</td>
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
                            <td class="text-center">
                                <div class="input-append">
                                    <form method="GET" action="delete">
                                        <input type="hidden" name="userId" value="${cart.user.id}"/>
                                        <input type="hidden" name="productId" value="${cart.product.id}"/>
                                        <input type="hidden" name="amount" value="${cart.amount}"/>
                                        <a class="text-center" href="<c:url value="/cart/delete"/>"
                                           productName="${cart.product.name}"><i class="fa fa-trash-o fa-fw"></i></a>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal window for delete product-->
<div id="modalDeleteProduct" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete</h4>
            </div>
            <div class="modal-body text-center">
                Do you really want to delete "<b class="productName"></b>" from your cart?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary btn-confirm">Yes</button>
            </div>
        </div>
    </div>
</div>


