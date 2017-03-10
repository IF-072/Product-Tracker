<%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="cart.myCart"/></h1>
    </div>
</div>

<!-- Table -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <spring:message code="store.name"/>: ${carts[0].store.name}
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="text-center table-number-width">#</th>
                        <th class="text-center"><spring:message code="product"/></th>
                        <th class="text-center"><spring:message code="product.description"/></th>
                        <th class="text-center"><spring:message code="product.category"/></th>
                        <th class="text-center table-toBeBought-width"><spring:message code="cart.toBeBought"/></th>
                        <th class="text-center table-bought-width"><spring:message code="cart.bought"/></th>
                        <th class="text-center table-delete-width"><spring:message code="delete"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${carts}" var="cart" varStatus="status">
                        <tr class="gradeA">
                            <td>${status.count}</td>
                            <td class="productName">${cart.product.name}</td>
                            <td>${cart.product.description}</td>
                            <td>${cart.product.category.name}</td>
                            <td class="text-center">
                                    ${cart.amount} ${cart.product.unit.name}
                            </td>
                            <c:url var="purchase" value="/cart/purchase/${cart.product.id}"/>
                            <sf:form method="POST" action="${purchase}" modelAttribute="cartDTO"
                                     id="purchaseDeleteForm${status.count}">
                                <td class="text-center">
                                    <div class="input-append">
                                        <sf:hidden path="userId" value="${cart.user.id}"/>
                                        <sf:hidden path="storeId" value="${cart.store.id}"/>
                                        <sf:input class="span2 number table-input-number-width"
                                                  id="appendedInputButton"
                                                  size="16" type="number" path="amount" min="1"
                                                  value="${cart.amount}"/>
                                        <sf:hidden path="initialAmount" value="${cart.amount}"/>
                                        <sf:button class="span2">Ok</sf:button>
                                    </div>
                                </td>
                                <td class="text-center">
                                    <div class="input-append">
                                        <a class="text-center" href="<c:url value="/cart/delete/${cart.product.id}"/>"
                                           purpose="deleteProduct"
                                           number="${status.count}" deleteName="${cart.product.name}">
                                            <i class="fa fa-trash-o fa-fw"></i></a>
                                        <c:set var="pageName" value="cart" scope="request"/>
                                    </div>
                                </td>
                            </sf:form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="panel-footer text-right">
                <button type="button" class="btn btn-primary" id="btn-deleteAll" href="<c:url value="/cart/delete"/>"
                        deleteName="<spring:message code="deleteDialog.messageDeleteAllCart"/>">
                    <spring:message code="deleteAll"/></button>
            </div>
        </div>
    </div>
</div>

