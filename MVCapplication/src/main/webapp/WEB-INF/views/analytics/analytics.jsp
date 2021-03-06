<%--
  Created by Igor Kryviuk
--%>

<%@ page import="com.softserve.if072.common.model.ProductStatistics" %>
<%@ page import="java.sql.Timestamp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="analytics.myAnalytics"/></h1>
    </div>
</div>

<p id="usingProductDatesForChart" style="display: none">${productStatistics.usingProductDatesForChart}</p>
<p id="usingProductAmountsForChart" style="display: none">${productStatistics.usingProductAmountsForChart}</p>
<p id="purchasingProductDatesForChart" style="display: none">${productStatistics.purchasingProductDatesForChart}</p>
<p id="purchasingProductAmountsForChart" style="display: none">${productStatistics.purchasingProductAmountsForChart}</p>

<div class="row">
    <div class="col-lg-12">
    <div class="panel-primary text-right">
        <button type="button" class="btn btn-primary" id="selectOtherProduct"
                href="<c:url value="/analytics/cleanSession"/>">
            <spring:message code="analytics.selectOtherProduct"/>
        </button>
    </div>
    </div>
</div>
<br/>

<div class="row">
    <!-- Using Products Chart -->
    <div class="col-lg-6"><div class="panel panel-red">
        <div class="panel-heading text-center">
            <spring:message code="analytics.usingProductsChartPanelHeader" />
        </div>
        <div class="panel-body">
            <canvas id="usingProductsChart"></canvas>
        </div>
    </div>
    </div>

    <!-- Purchasing Products Chart -->
    <div class="col-lg-6">
        <div class="panel panel-green">
            <div class="panel-heading text-center">
                <spring:message code="analytics.purchasingProductsChartPanelHeader" />
            </div>
            <div class="panel-body">
                <div id="noDataFound" class="text-center"></div>
                <canvas id="purchasingProductsChart"></canvas>
            </div>
        </div>
    </div>
</div>
<br/>
<br/>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-primary">
            <div class="panel-heading text-center">
                <spring:message code="analytics.statTableHead"/><br>
                "${productStatistics.productName}"
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="text-center "><spring:message code="analytics.statTableColumnHead1"/></th>
                        <th class="text-center" id="analyticsTableColumnValueWidth"><spring:message
                                code="analytics.statTableColumnHead2"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueMean"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                              value="${productStatistics.mean}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueMin"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                              value="${productStatistics.min}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueMax"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                              value="${productStatistics.max}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueCount"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                              value="${productStatistics.count}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueRange"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                              value="${productStatistics.range}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message
                                code="analytics.statTableColumnValueTotalInStorage"/> ${productStatistics.productUnit}</td>
                        <td><fmt:formatNumber type="number"
                                              value="${productStatistics.amountInStorage}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message
                                code="analytics.statTableColumnValueTotalPurchased"/> ${productStatistics.productUnit}</td>
                        <td><fmt:formatNumber type="number"
                                              value="${productStatistics.totalPurchased}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message
                                code="analytics.statTableColumnValueTotalUsed"/> ${productStatistics.productUnit}</td>
                        <td><fmt:formatNumber type="number"
                                              value="${productStatistics.totalUsed}"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueDateOfLastPurchasing"/></td>
                        <td>
                            <c:if test="${empty productStatistics.lastPurchasingDate}">
                                ----------
                            </c:if>
                            <c:if test="${!empty productStatistics.lastPurchasingDate}">
                                <% Timestamp lastPurchasingDate = ((ProductStatistics) request.getAttribute("productStatistics"))
                                        .getLastPurchasingDate();%>
                                <%=lastPurchasingDate.toLocalDateTime().toLocalDate()%>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueDateOfLastUsing"/></td>
                        <td>
                            <c:if test="${empty productStatistics.lastUsingDate}">
                                ----------
                            </c:if>
                            <c:if test="${!empty productStatistics.lastUsingDate}">
                                <% Timestamp lastUsingDate = ((ProductStatistics) request.getAttribute("productStatistics"))
                                        .getLastUsingDate();%>
                                <%=lastUsingDate.toLocalDateTime().toLocalDate()%>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td><spring:message code="analytics.statTableColumnValueEndDate"/>
                        </td>
                        <td>
                            <c:if test="${empty productStatistics.endDate}">
                                ----------
                            </c:if>
                            <c:if test="${!empty productStatistics.endDate}">
                                <div id="endDate">
                                <% Timestamp endDate = ((ProductStatistics) request.getAttribute("productStatistics"))
                                        .getEndDate();%>
                                <%=endDate.toLocalDateTime().toLocalDate()%>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<p id="notEnoughData" style="display: none"><spring:message code="analytics.notEnoughDataForChart" /></p>
<p id="usingProducts" style="display: none"><spring:message code="analytics.usingProductsChartHeader" /></p>
<p id="purchasingProducts" style="display: none"><spring:message code="analytics.purchasingProductsChartHeader" /></p>





