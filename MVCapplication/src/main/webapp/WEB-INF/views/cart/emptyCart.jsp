<%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">My Cart</h1>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <div class="panel panel-primary">
                <div class="panel-heading text-center">
                    <h4>Your Shophing Cart is Empty!</h4>
                </div>
                <div class="panel-body text-center">
                    <p> Would you like to add some products to your cart?</p>
                </div>
                <div class="panel-footer">
                    <div class="btn-group btn-group-justified" role="group" aria-label="...">
                        <div class="btn-group" role="group">
                            <a href="<c:url value='/goShoppingStores'/>">
                                <button type="button" class="btn btn-default">Ok</button>
                            </a>
                        </div>
                        <div class="btn-group" role="group">
                            <a href="<c:url value='/'/>">
                                <button type="button" class="btn btn-default">Cancel</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>