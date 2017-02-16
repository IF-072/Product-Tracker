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
                        <th></th>
                        <th>Product's name</th>
                        <th>Amount</th>
                        <th>Stores</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="product" items="${selected}" varStatus="loop">
                        <tr onclick="checkBox(${loop.index})">
                            <td >

                                <input type="checkbox" value="${loop.index}" name="checkbox[${loop.index}]"
                                       id="checkbox${loop.index}" onclick="checkBox(${loop.index})">

                            </td>
                            <td ><input type="hidden"
                                                                         name="carts[${loop.index}].product.id"
                                                                         value="${product.id}"> ${product.name}</td>
                            <td><input type="number" name="carts[${loop.index}].amount" min="1" value="1"
                                       class="form-control" onclick="checkBox(${loop.index})"></td>
                            <td>
                                <select class="form-control" name="carts[${loop.index}].store.id" onclick="checkBox(${loop.index})">
                                    <c:forEach var="store" items="${product.stores}">

                                        <option value="${store.id}" selected>${store.name}, ${store.address}</option>

                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button type="button" class="btn btn-default" onclick="selectAll()">Select all</button>
                <button type="button" class="btn btn-default" onclick="prevStep()">Previous step</button>
                <input type="submit" class="btn btn-default selected btn-success" value="Add to cart">
            </form>


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
                        <th>Stores</th>
                    </tr>
                    </thead>
                    <tbody>


                    <c:forEach var="product" items="${remained}" varStatus="loop">
                        <tr>
                            <td>
                                    ${loop.count}
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
</c:if>