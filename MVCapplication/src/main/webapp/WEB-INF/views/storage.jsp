<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="/css/storage.css"/>
<div class="panel panel-default">
    <div class="panel-heading">
        Storage
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Product</th>
                    <th>End date</th>
                    <th>Amount</th>
                    <th>Add to sopping list</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="storage" items="${list}" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${storage.product.name}</td>
                        <td>${storage.endDate}</td>
                        <td>
                            <form method="post" action="update">
                                <input type="hidden" name="userId" value="${storage.user.id}">
                                <input type="hidden" name="productId" value="${storage.product.id}">
                                <input type="number" name="amount" min="0" value="${storage.amount}" class="form-control" onchange="allowBtn(${loop.count}, ${storage.amount})">
                                <input type="submit" class="btn disabled btn-default" value="confirm">
                            </form>
                        </td>
                        <td onclick="addToShoppingList(${storage.user.id}, ${storage.product.id});">
                            <p class="fa fa-check"></p></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="<c:url value="/js/storage.js"/>"></script>