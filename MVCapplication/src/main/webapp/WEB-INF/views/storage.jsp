<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="myStorage"/></h1>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>#</th>
                    <th><spring:message code="product"/></th>
                    <th><spring:message code="endDate"/></th>
                    <th><spring:message code="amount"/></th>
                    <th><spring:message code="addToShoppingList"/></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="storage" items="${list}" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${storage.product.name}</td>
                        <td>${storage.endDate}</td>
                        <td>
                            <form:form method="post" action="update" modelAttribute="storage">
                                <form:hidden path="productId" value="${storage.product.id}"/>
                                <input type="number" path="amount" name="amount" min="0" value="${storage.amount}"
                                       class="form-control" onchange="allowBtn(${loop.count}, ${storage.amount})"/>
                            <form:button class="btn disabled btn-default" id="confirm"><spring:message code="confirm"/></form:button>
                            </form:form>
                        </td>
                        <td>
                            <button type="button" class="btn btn-default btn-primary"
                                    onclick="addToShoppingList(${storage.product.id});">
                                <spring:message code="add"/>
                            </button>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
<div id="success" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-success" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><spring:message code="info"/></h4>
            </div>
            <div class="modal-body text-center">
                <spring:message code="successAdd"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-confirm"><spring:message code="ok"/></button>
            </div>
        </div>
    </div>
</div>

<div id="error" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><spring:message code="error"/></h4>
            </div>
            <div class="modal-body text-center" id="message">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-confirm"><spring:message code="ok"/></button>
            </div>
        </div>
    </div>
</div>
