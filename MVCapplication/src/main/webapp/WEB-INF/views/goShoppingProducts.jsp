<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Choose products</h1>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-body">
        <div class="table-responsive">
            <form action="../addToCart" method="post" modelAttribute="cartForm">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Product's name</th>
                        <th>Amount</th>
                        <th>Stores</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="product" items="${selected}" varStatus="loop">
                            <tr>
                                <td onclick="checkBox(${loop.index})">

                                    <input type="checkbox" value="${loop.index}" name="checkbox[${loop.index}]" id="checkbox${loop.index}">

                                </td>
                                <td onclick="checkBox(${loop.index})"><input type="hidden" name="carts[${loop.index}].product.id"
                                           value="${product.id}"> ${product.name}</td>
                                <td><input type="number" name="carts[${loop.index}].amount" min="1" value="1" class="form-control"></td>
                                <td>
                                    <select class="form-control" name="carts[${loop.index}].store.id">
                                        <c:forEach var="store" items="${product.stores}">

                                            <option value="${store.id}">${store.name}, ${store.address}</option>

                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <input type="submit" class="btn btn-default">
            </form>

            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Product's name</th>
                    <th>Stores</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="product" items="${remained}" varStatus="loop">
                    <tr>
                        <td>
                            <label class="checkbox-inline">
                                <input type="checkbox" value="${store.id}" name="stores">
                            </label>
                        </td>
                        <td>${product.name}</td>
                        <td>
                            <c:forEach var="store" items="${product.stores}">
                                <p>${store.name}, ${store.address}</p>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>