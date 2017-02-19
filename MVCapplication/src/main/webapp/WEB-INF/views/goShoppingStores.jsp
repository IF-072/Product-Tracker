<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

                    <c:forEach var="store" items="${list}" varStatus="loop">
                        <tr onclick="checkBox(${loop.index})">
                            <td>
                                <input type="checkbox" value="${store.id}" name="stores" id="checkbox${loop.index}"
                                       onclick="checkBox(${loop.index})">
                            </td>
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
                <input type="submit" class="btn btn-default">
            </form>
        </div>
    </div>
</div>