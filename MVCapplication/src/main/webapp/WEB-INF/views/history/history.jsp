<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">My History</h1>
    </div>
</div>

<!-- Table -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="text-center table-number-width">#</th>
                        <th class="text-center">Product</th>
                        <th class="text-center">Description</th>
                        <th class="text-center">Category</th>
                        <th class="text-center table-toBeBought-width">Amount</th>
                        <th class="text-center table-bought-width">Used Date</th>
                        <th class="text-center table-delete-width">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${histories}" var="history" varStatus="status">
                    <tr class="gradeA">
                        <td>${status.count}</td>
                        <td>${history.product.name}</td>
                        <td>${history.product.description}</td>
                        <td>${history.product.category.name}</td>
                        <td class="text-center">
                                ${history.amount} ${history.product.unit.name}
                        </td>
                        <td class="text-center">
                            <jsp:useBean id="dateValue" class="java.util.Date"/>
                            <jsp:setProperty name="dateValue" property="time" value="${history.usedDate}"/>
                            <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy"/>
                        </td>
                        <td class="text-center">
                            <div class="input-append">
                                <form method="GET" action="delete">
                                    <input type="hidden" name="historyId" value="${history.id}"/>
                                    <a class="text-center" href="<c:url value="/history/delete"/>"
                                       productName="${history.product.name}"><i class="fa fa-trash-o fa-fw"></i></a>
                                </form>
                            </div>
                        </td>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal window for delete history-->
<div id="modalDeleteHistory" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete</h4>
            </div>
            <div class="modal-body text-center">
                Do you really want to delete "<b class="productName"></b>" from your history?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary btn-confirm">Yes</button>
            </div>
        </div>
    </div>
</div>