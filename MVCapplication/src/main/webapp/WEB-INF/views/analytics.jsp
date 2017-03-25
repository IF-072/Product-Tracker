<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%--
  Created by Pavlo Bendus
--%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="analytics.helloMessage"/></h1>
    </div>
</div>

<!-- Filter -->
<sf:form role="form" modelAttribute="analyticsDTO" method="post">
    <fieldset>
        <div class="row">

            <div class="col-lg-3 search-item">
                <div class="form-group">
                    <sf:select path="productId" class="form-control" placeholder="Product">
                        <option value="0" selected>default text</option>
                        <sf:options items="${products}" itemLabel="name" itemValue="id" />
                    </sf:select>
                        <c:forEach items="${products}" var="product">
                        <c:out value="${product}" />
                        <br />
                        </c:forEach>
                </div>
            </div>

            <%--<div class="col-lg-2 search-item">--%>
                <%--<div class="form-group has-feedback">--%>
                    <%--<spring:message code='history.filter.dateFrom' var="filterDateFrom"/>--%>
                    <%--<sf:input path="fromDate" class="form-control" type="text" name="fromDate" placeholder="${filterDateFrom}"/>--%>
                    <%--<i class="glyphicon glyphicon-calendar form-control-feedback"></i>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--&lt;%&ndash;<div class="col-lg-2 search-item">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="form-group has-feedback">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<spring:message code='history.filter.dateTo' var="filterDateTo"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<sf:input path="toDate" class="form-control" type="text" name="toDate" placeholder="${filterDateTo}"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<i class="glyphicon glyphicon-calendar form-control-feedback"></i>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<div class="col-lg-1">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<spring:message code='history.filter.search' var="searchButtonMessage"/>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input type="submit" class="btn btn-primary" value="${searchButtonMessage}"/>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        </div>
    </fieldset>
</sf:form>

<!-- Chart -->
<div id="chart" class="col-lg-4" style="height: 250px;"></div>
