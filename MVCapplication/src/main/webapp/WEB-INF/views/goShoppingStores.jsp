<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="panel panel-default">
    <div class="panel-heading">
        Choose stores
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Store</th>
                    <th>Address</th>
                    <th>Products</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="store" items="${list}" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${store.name}</td>
                        <td>${store.address}</td>
                        <td>
                            <c:forEach var="product" items="${store.products}">
                                <p>${product.name}</p>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>