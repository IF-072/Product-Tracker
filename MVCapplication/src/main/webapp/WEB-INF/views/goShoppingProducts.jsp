<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Choose products</h1>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-body">
        <div class="table-responsive">
            <form:form action="../addToCart" method="post" modelAttribute="cartForm" id="addToCart">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Product's name</th>
                        <th>Amount</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="shoppingList" items="${selected}" varStatus="loop">
                        <tr class="check-tr">
                            <td>

                                <input type="checkbox" value="${loop.index}" name="checkbox[${loop.index}]"
                                       id="checkbox${loop.index}" class="checkbox">
                                <input type="hidden" value="${shoppingList.product.stores[0].id}"
                                       name="carts[${loop.index}].store.id">
                            </td>
                            <td><input type="hidden"name="carts[${loop.index}].product.id"
                                       value="${shoppingList.product.id}"> ${shoppingList.product.name}
                            </td>
                            <td><input type="number" name="carts[${loop.index}].amount" min="1"
                                       value="${shoppingList.amount}" class="form-control">
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button type="button" class="btn btn-default" id="selectAll">Select all</button>
                <button type="button" class="btn btn-default" id="prevStep">Previous step</button>
                <input type="submit" class="btn btn-default selected btn-success" value="Add to cart">
            </form:form>


        </div>
    </div>
</div>

<c:if test="${not empty remained}">
    <div class="panel panel-default">
        <div class="panel-heading">
            <p class="fa fa-info-circle"></p><b> Products that are remained in shopping list!</b>
        </div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Product's name</th>
                    </tr>
                    </thead>
                    <tbody>


                    <c:forEach var="shoppingList" items="${remained}" varStatus="loop">
                        <tr>
                            <td>
                                    ${loop.count}
                            </td>
                            <td>${shoppingList.product.name}</td>

                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>

<!-- Modal window -->
<div id="modalInfo" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Info</h4>
            </div>
            <div class="modal-body text-center">
                You need to choose at least one product
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-confirm">Ok</button>
            </div>
        </div>
    </div>
</div>