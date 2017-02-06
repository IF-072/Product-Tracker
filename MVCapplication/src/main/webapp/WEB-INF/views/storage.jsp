<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel panel-default">
    <div class="panel-heading">
        Storage
    </div>
    <!-- /.panel-heading -->
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
                        <td onclick="minus(${storage});"><p class="fa fa-minus"></p></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- /.table-responsive -->
    </div>
    <!-- /.panel-body -->
</div>

<script type="text/javascript" language="JavaScript">
    function minus(storage) {
        storage.amount--;
        $.ajax({
            method: "PUT",
            dataType: 'json',
            data: storage,
            success: function(msg) {
                console.log( "Data Saved: " + msg );
            }
        });
    }
</script>
