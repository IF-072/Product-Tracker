<%--
  Created by: Pavlo Bendus
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="category.helloMessage"/></h1>
    </div>
</div>

<!-- Table with the list of categories -->
<div class="row">
    <div class="col-lg-6">
        <input type="button" class="btn btn-outline btn-primary addNewCategory" value="<spring:message code="category.addNew"/>">
        <br />
        <br />
        <c:if test="${empty categories}">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <spring:message code="category.notFound"/>
                </div>
                <div class="panel-body">
                    <p><spring:message code="category.listIsEmpty" /></p>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty categories}">
        <div class="panel panel-default">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="text-center"><spring:message code="category.headerID"/></th>
                        <th class="text-center"><spring:message code="category.headerName"/></th>
                        <th class="text-center"><spring:message code="category.headerEdit"/></th>
                        <th class="text-center"><spring:message code="category.headerDelete"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${categories}" var="category" varStatus="loop">
                        <tr class="gradeA">
                            <td>${loop.count}</td>
                            <td>${category.name}</td>
                            <td class="text-center"><a href="#" class="categoryBtnEdit" id="${category.id}"><i class="fa fa-pencil fa-lg"></i></a></td>
                            <td class="text-center"><a href="#" class="categoryBtnDelete" id="${category.id}"><i  class="fa fa-trash-o fa-lg"></i></a></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
        </div>
        </c:if>
    </div>
</div>

<div id="deleteForm">
    <h2 class="text-center">Are you sure?</h2>
    <br />
    <br />
    <div class="center-block text-center">
        <%--<button onclick="acceptDeleting()" class="btn btn-success btn-reset-custom btnAcceptDeleting()">Yes</button>--%>
        <button class="btn btn-success btn-reset-custom btnAcceptDeleting"><spring:message code="yes"/></button>
        <%--<button onclick="cancelDeleting()" class="btn btn-default btn-reset-custom">No</button>--%>
        <button class="btn btn-default btn-reset-custom btnCancelDeleting"><spring:message code="no"/></button>
    </div>
</div>
