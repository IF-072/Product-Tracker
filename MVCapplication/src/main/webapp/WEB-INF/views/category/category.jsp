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
                    <spring:message code="category.noCategoriesFound" />
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
                            <td class="text-center"><a href="#" class="categoryBtnDelete" id="${category.id}" deleteName="${category.name}"><i  class="fa fa-trash-o fa-lg"></i></a></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
        </div>
        </c:if>
    </div>
</div>

<!-- Modal window for category deleting-->
<div id="categoryDeleteConfirm" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-delete" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i  class="fa fa-times"></i></button>
                <h4 class="modal-title"><spring:message code="category.deleteWindowHeader" /></h4>
            </div>
            <div class="modal-body text-center">
                <spring:message code="category.deleteMessage"/>
                "<b class="deleteName"></b>"
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success btn-success-custom btn-confirm"> <spring:message code="yes"/></button>
                <button type="button" class="btn btn-default btn-reset-custom" data-dismiss="modal"> <spring:message code="no"/></button>
            </div>
        </div>
    </div>
</div>
