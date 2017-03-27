<%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="analytics.myAnalytics"/></h1>
    </div>
</div>

<br/>

<div class="col-lg-6">
    <div class="form-group" id="baseForm">
        <div class="panel-body">
            <form role="form" method="get">
                <div class="form-group">
                    <c:if test="${empty analyticsProductDTOs}">
                        <h4><spring:message code="selectProductForAnalytics.dialogHasNoProduct"/></h4>
                    </c:if>
                    <c:if test="${!empty analyticsProductDTOs}">
                        <div>
                            <label><spring:message code="selectProductForAnalytics.dialogHeader"/></label>
                            <select class="form-control" id="select">
                                <c:forEach items="${analyticsProductDTOs}" var="productDTO">
                                    <option value="${productDTO.id}">${productDTO.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <br>
                        <div>${value}</div>
                        <div class="text-right">
                            <button type="button" class="btn btn-primary" id="selectProductSubmit"
                                    href="<c:url value="/analytics/"/>">
                                <spring:message code="submit"/></button>
                        </div>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
