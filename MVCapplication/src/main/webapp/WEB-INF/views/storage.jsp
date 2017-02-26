<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">My storage</h1>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Product</th>
                    <th>End date</th>
                    <th>Amount</th>
                    <th>Add to shopping list</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="storage" items="${list}" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${storage.product.name}</td>
                        <td>${storage.endDate}</td>
                        <td>
                            <form:form method="post" action="update" modelAttribute="storage">
                                <form:hidden path="productId" value="${storage.product.id}"/>
                                <input type="number" path="amount" name="amount" min="0" value="${storage.amount}"
                                       class="form-control" onchange="allowBtn(${loop.count}, ${storage.amount})"/>
                            <form:button class="btn disabled btn-default">confirm</form:button>
                            </form:form>
                        </td>
                        <td>
                            <button type="button" class="btn btn-default btn-success"
                                    onclick="addToShoppingList(${storage.product.id});">
                                Add
                            </button>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
<div id="success" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-success" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Info</h4>
            </div>
            <div class="modal-body text-center">
                Product was added to shopping list
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-confirm">Ok</button>
            </div>
        </div>
    </div>
</div>
