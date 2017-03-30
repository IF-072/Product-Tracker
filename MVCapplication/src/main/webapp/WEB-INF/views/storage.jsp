<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${empty list}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
                <div class="panel panel-primary">
                    <div class="panel-heading text-center">
                        <h4><spring:message code="storage.empty"/></h4>
                    </div>
                    <div class="panel-body text-center">
                        <p><spring:message code="storage.emptyMessage"/></p>
                    </div>
                    <div class="panel-footer">
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <a href="<c:url value='/product/'/>">
                                    <button type="button" class="btn btn-default"><spring:message
                                            code="products"/></button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${not empty list}">
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
                        <th><spring:message code="storage.endDate"/></th>
                        <th><spring:message code="amount"/></th>
                        <th><spring:message code="storage.addToShoppingList"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="storage" items="${list}" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td>${storage.product.name}</td>
                            <td class="date">
                                <c:if test="${empty storage.endDate}">
                                    ----------
                                </c:if>
                                <c:if test="${!empty storage.endDate}">
                                    <jsp:useBean id="dateValue" class="java.util.Date"/>
                                    <jsp:setProperty name="dateValue" property="time"
                                                     value="${storage.endDate.getTime()}"/>
                                    <fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd"/>
                                </c:if>
                            </td>
                            <td>
                                <form:form method="post" action="/storage/update" modelAttribute="storage">
                                    <form:hidden path="productId" value="${storage.product.id}"/>
                                    <form:hidden path="productName" value="${storage.product.name}"/>
                                    <form:hidden path="previousAmount" value="${storage.amount}" class="init"/>
                                    <input type="number" path="amount" name="amount" min="0" value="${storage.amount}"
                                           class="form-control num"/>
                                    <form:button class="btn disabled btn-default confirm" disabled="true">
                                        <spring:message code="storage.confirm"/></form:button>
                                </form:form>
                            </td>
                            <td>
                                <button type="button" class="btn btn-default btn-primary addToSH"
                                        product="${storage.product.id}">
                                    <spring:message code="storage.add"/>
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
                    <h4 class="modal-title"><spring:message code="storage.info"/></h4>
                </div>
                <div class="modal-body text-center">
                    <spring:message code="storage.successAdd"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary btn-confirm"><spring:message
                            code="storage.ok"/></button>
                </div>
            </div>
        </div>
    </div>

    <div id="error" class="modal fade" tabindex="-1">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header alert alert-danger" role="alert">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="storage.error"/></h4>
                </div>
                <div class="modal-body text-center" id="message">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary btn-confirm"><spring:message
                            code="storage.ok"/></button>
                </div>
            </div>
        </div>
    </div>
</c:if>