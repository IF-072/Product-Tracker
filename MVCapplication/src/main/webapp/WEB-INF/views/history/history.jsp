<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="history.myHistory"/></h1>
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
                        <th class="text-center"><spring:message code="product"/></th>
                        <th class="text-center"><spring:message code="product.description"/></th>
                        <th class="text-center"><spring:message code="product.category"/></th>
                        <th class="text-center table-toBeBought-width"><spring:message code="amount"/></th>
                        <th class="text-center table-bought-width"><spring:message code="history.usedDate"/></th>
                        <th class="text-center table-delete-width"><spring:message code="delete"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${histories}" var="history" varStatus="status">
                        <c:if test="${history.action=='PURCHASED'}">
                            <tr class="gradeA PURCHASED">
                        </c:if>
                        <c:if test="${history.action=='USED'}">
                            <tr class="gradeA USED">
                        </c:if>
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
                                <a class="text-center" purpose="deleteRecord"
                                   href="<c:url value="/history/delete/${history.id}"/>"
                                   deleteName="${history.product.name}"><i class="fa fa-trash-o fa-fw"></i></a>
                                <c:set var="pageName" value="history" scope="request"/>
                            </div>
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="panel-footer text-right">
                <div class="row">
                    <div class="col-md-8">
                        <table>
                            <tbody class="text-left">
                            <tr>
                                <td>
                                    <span class="square_PURCHASED"></span>
                                <td>
                                <td class="text-left color_PURCHASED">
                                    -
                                    <spring:message code="history.legendPurchased"/>
                                <td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="square_USED"></span>
                                <td>
                                <td class="text-left color_USED">
                                    -
                                    <spring:message code="history.legendUsed"/>
                                <td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-primary" id="btn-deleteAll"
                                href="<c:url value="/history/delete"/>"
                                deleteName="<spring:message code="deleteDialog.messageDeleteAllHistory"/>">
                            <spring:message code="deleteAll"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>