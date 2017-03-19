<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="history.myHistory"/></h1>
    </div>
</div>

<!-- Search form -->
<sf:form role="form" modelAttribute="historySearchDTO" method="post">
    <fieldset>
        <div class="row">

            <div class="col-lg-2 search-item">
                <div class="form-group">
                    <spring:message code='history.filter.name' var="filterName"/>
                    <sf:input path="name" class="form-control" id="inputWarning" type="text" placeholder="${filterName}"/>
                </div>
            </div>
            <div class="col-lg-2 search-item">
                <div class="form-group">
                    <spring:message code='history.filter.description' var="filterDescription"/>
                    <sf:input path="description" class="form-control" type="text" placeholder="${filterDescription}"/>
                </div>
            </div>
            <div class="col-lg-3 search-item">
                <div class="form-group">
                    <sf:select path="categoryId" class="form-control" placeholder="Category">
                        <option value="0" selected><spring:message code="history.filter.category"/></option>
                        <sf:options items="${categories}" itemLabel="name" itemValue="id"/>
                    </sf:select>
                </div>
            </div>
            <div class="col-lg-2 search-item">
                <div class="form-group has-feedback">
                    <spring:message code='history.filter.dateFrom' var="filterDateFrom"/>
                    <sf:input path="fromDate" class="form-control" type="text" name="fromDate" placeholder="${filterDateFrom}"/>
                    <i class="glyphicon glyphicon-calendar form-control-feedback"></i>
                </div>
            </div>
            <div class="col-lg-2 search-item">
                <div class="form-group has-feedback">
                    <spring:message code='history.filter.dateTo' var="filterDateTo"/>
                    <sf:input path="toDate" class="form-control" type="text" name="toDate" placeholder="${filterDateTo}"/>
                    <i class="glyphicon glyphicon-calendar form-control-feedback"></i>
                </div>
            </div>
            <div class="col-lg-1">
                <spring:message code='history.filter.search' var="searchButtonMessage"/>
                <input type="submit" class="btn btn-primary" value="${searchButtonMessage}"/>
            </div>
        </div>
    </fieldset>
</sf:form>

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

                    <c:if test="${empty histories}">
                        <tr class="noitems">
                            <td colspan="7"><spring:message code="history.filter.empty"/></td>
                        </tr>
                    </c:if>

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