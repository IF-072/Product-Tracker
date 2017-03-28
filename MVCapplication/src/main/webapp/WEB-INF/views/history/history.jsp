<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ht" uri="hashtag.tld" %>
<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="history.myHistory"/></h1>
    </div>
</div>

<c:url var="firstUrl" value="/history?pageNumber=${beginIndex}&pageSize=${pageSize}"/>
<c:url var="lastUrl" value="/history?pageNumber=${endIndex}&pageSize=${pageSize}"/>
<c:url var="prevUrl" value="/history?pageNumber=${currentIndex - 1}&pageSize=${pageSize}"/>
<c:url var="nextUrl" value="/history?pageNumber=${currentIndex + 1}&pageSize=${pageSize}"/>

<!-- Search form -->
<sf:form role="form" modelAttribute="historySearchDTO" method="post" action="/history">
    <fieldset>
        <div class="row">
            <div class="col-lg-2 search-item">
                <div class="form-group">
                    <spring:message code='history.filter.name' var="filterName"/>
                    <sf:input path="name" class="form-control" id="inputWarning" type="text"
                              placeholder="${filterName}"/>
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
                    <sf:input path="fromDate" class="form-control" type="text" name="fromDate"
                              placeholder="${filterDateFrom}"/>
                    <i class="glyphicon glyphicon-calendar form-control-feedback"></i>
                </div>
            </div>
            <div class="col-lg-2 search-item">
                <div class="form-group has-feedback">
                    <spring:message code='history.filter.dateTo' var="filterDateTo"/>
                    <sf:input path="toDate" class="form-control" type="text" name="toDate"
                              placeholder="${filterDateTo}"/>
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
                        <th class="text-center table-bought-width"><spring:message code="history.date"/></th>
                        <th class="text-center table-delete-width"><spring:message code="delete"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${empty historiesPage}">
                        <tr class="noitems">
                            <td colspan="7"><spring:message code="history.filter.empty"/></td>
                        </tr>
                    </c:if>

                    <c:forEach items="${historiesPage.getContent()}" var="history" varStatus="status">
                        <tr class="gradeA">
                            <td>${status.count}</td>
                            <td>${history.product.name}</td>
                            <td><ht:hashtagResolver>${history.product.description}</ht:hashtagResolver></td>
                            <td>${history.product.category.name}</td>
                            <c:if test="${history.action=='PURCHASED'}">
                            <td class="text-center PURCHASED">
                                </c:if>
                                <c:if test="${history.action=='USED'}">
                            <td class="text-center USED">
                                </c:if>

                                    ${history.amount} ${history.product.unit.name}
                            </td>
                            <td class="text-center">
                                <fmt:formatDate value="${history.usedDate}" pattern="MM/dd/yyyy"/>
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

                <div class="panel-footer text-rigth">
                    <div class="col-sm-6">

                        <label>Show products </label>
                        <form action="/history?pageNumber=${beginIndex}">
                            <select name="pageSize">
                                <option value="25">25</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>
                            <input type="submit" value="Submit">
                        </form>
                    </div>

                    <div id=" historyData_paginate" class="dataTables_paginate paging_simple_numbers">

                        <div class="col-sm-6">
                            <div class="pagination">
                                <c:choose>
                                    <c:when test="${currentIndex == 1}">
                                        <li class="paginate_button previous disabled"><a href="#">First</a></li>
                                        <li class="paginate_button previous disabled"><a href="#">Previous</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a class="paginate_button previous" href="${firstUrl}">First</a></li>
                                        <li><a class="paginate_button previous" href="${prevUrl}">Previous</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
                                    <c:url var="pageUrl" value="history?pageNumber=${i}&pageSize=${pageSize}"/>
                                    <c:choose>
                                        <c:when test="${i == currentIndex}">
                                            <li class="paginate_button active">
                                                <a href="${pageUrl}"><c:out value="${i}"/></a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a class="paginate_button" href="${pageUrl}"><c:out value="${i}"/></a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${currentIndex == historiesPage.getTotalPages()}">
                                        <li class="paginate_button next disabled"><a href="#">Next</a></li>
                                        <li class="paginate_button next disabled"><a href="#">Last</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a class="paginate_button next" href="${nextUrl}">Next</a></li>
                                        <li><a class="paginate_button next" href="${lastUrl}">Last</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                        </div>
                    </div>
                </div>

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
                                    <spring:message code="history.legendPurchased"/>
                                <td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="square_USED"></span>
                                <td>
                                <td class="text-left color_USED">
                                    <spring:message code="history.legendUsed"/>
                                <td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-primary" id="pdfButton"
                                href="<c:url value="/history/getpdf"/>">
                            PDF
                        </button>
                    </div>
                </div>
            </div>


        </div>
    </div>
</div>