<%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="cart.myCart"/></h1>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <div class="panel panel-primary">
                <div class="panel-heading text-center">
                    <h4><spring:message code="emptyCart.dialogHeader"/></h4>
                </div>
                <div class="panel-body text-center">
                    <p><spring:message code="emptyCart.dialogBody"/></p>
                </div>
                <div class="panel-footer">
                    <div class="btn-group btn-group-justified" role="group" aria-label="...">
                        <div class="btn-group" role="group">
                            <a href="<c:url value='/goShoppingStores'/>">
                                <button type="button" class="btn btn-primary"><spring:message code="yes"/></button>
                            </a>
                        </div>
                        <div class="btn-group" role="group">
                            <a href="<c:url value='/home'/>">
                                <button type="button" class="btn  btn-outline btn-primary"><spring:message code="no"/></button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>