<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:if test="${empty stores}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
                <div class="panel panel-primary">
                    <div class="panel-heading text-center">
                        <h4><spring:message code="goShopping.cantGoShopping"/></h4>
                    </div>
                    <div class="panel-body text-center">
                        <p><spring:message code="goShopping.cantMessage"/></p>
                    </div>
                    <div class="panel-footer">
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <a href="<c:url value='/storage/'/>">
                                    <button type="button" class="btn btn-default">
                                        <spring:message code="goShopping.storage"/>
                                    </button>
                                </a>
                            </div>
                            <div class="btn-group" role="group">
                                <a href="<c:url value='/cart/'/>">
                                    <button type="button" class="btn btn-default"><spring:message code="cart"/></button>
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
            <h1 class="page-header"><spring:message code="goShopping.chooseStore"/></h1>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="table-responsive">
                <form action="../goShoppingProducts" method="post">
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th></th>
                            <th><spring:message code="goShopping.store"/></th>
                            <th><spring:message code="address"/></th>
                            <th><spring:message code="products"/></th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="store" items="${stores}" varStatus="loop">
                            <tr class="check-tr">
                                <td>
                                    <input type="radio" value="${store.id}" name="stores" class="checkbox"
                                           required>
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
                    <input type="submit" class="btn btn-default" value="<spring:message code="goShopping.next"/>">
                </form>
            </div>
        </div>
    </div>
</c:if>
