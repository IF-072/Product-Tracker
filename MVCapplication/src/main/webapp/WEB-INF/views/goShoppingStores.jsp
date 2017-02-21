<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty stores}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
                <div class="panel panel-primary">
                    <div class="panel-heading text-center">
                        <h4>You can't go shopping!</h4>
                    </div>
                    <div class="panel-body text-center">
                        <p> Maybe you haven't confirmed previous shopping yet or your shopping list is empty</p>
                    </div>
                    <div class="panel-footer">
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <a href="<c:url value='/storage/'/>">
                                    <button type="button" class="btn btn-default">Storage</button>
                                </a>
                            </div>
                            <div class="btn-group" role="group">
                                <a href="<c:url value='/cart/'/>">
                                    <button type="button" class="btn btn-default">Cart</button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty stores}">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Choose store</h1>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="table-responsive">
                <form action="../goShoppingProducts" method="post">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th></th>
                            <th>Store</th>
                            <th>Address</th>
                            <th>Products</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="store" items="${stores}" varStatus="loop">
                            <tr onclick="checkBox(${loop.index})">
                                <td>
                                    <input type="radio" value="${store.id}" name="stores" id="checkbox${loop.index}"
                                           onclick="checkBox(${loop.index})" required>
                                </td>
                                <td>${store.name}</td>
                                <td>${store.address}</td>
                                <td>
                                    <c:forEach var="product" items="${store.products}">
                                        <p class="product">${product.name}</p>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <input type="submit" class="btn btn-default" value="Next">
                </form>
            </div>
        </div>
    </div>
</c:if>
