<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="storage" items="${list}" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${storage.product.name}</td>
                        <td>${storage.endDate}</td>
                        <td>${storage.amount}</td>
                        <td onclick="minus(${storage.user.id}, ${storage.product.id}, ${storage.amount}, ${loop.count});">
                            <p class="fa fa-minus"></p></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script type="text/javascript" language="JavaScript">
    function minus(userId, productId, amount, index) {
        amount--;
        if (amount >= 0) {
            $.ajax({
                url: "http://localhost:8080/client/storage/update",
                method: "POST",
                data: {
                    userId: userId,
                    productId: productId,
                    amount: amount
                },
                success: function () {
                    var tr = document.getElementsByTagName("tr");
                    tr[index].children[3].innerHTML = amount;
                },
                error: function (jqXHR, exception) {
                    console.log(jqXHR);
                    console.log(exception);
                }
            });
        }
    }
</script>
