<%--
  Created by: Pavlo Bendus
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!-- Block for empty categories -->
<c:if test="${empty categories}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-4">
                <div class="panel panel-primary">
                    <div class="panel-heading text-center">
                        <h4>List of categories is empty!</h4>
                    </div>
                    <div class="panel-body text-center">
                        <p>You can add category or return to the homepage</p>
                    </div>
                    <div class="panel-footer">
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <a href="<c:url value='/category/add'/>">
                                    <button type="button" class="btn btn-default"><spring:message code="category.addNew"/></button>
                                </a>
                            </div>
                            <div class="btn-group" role="group">
                                <a href="<c:url value='/home'/>">
                                    <button type="button" class="btn btn-default">Homepage</button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>


<c:if test="${not empty categories}">
    <!-- Header -->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><spring:message code="category.helloMessage"/></h1>
        </div>
    </div>

<!-- Table with the list of categories -->
<div class="row">
    <div class="col-lg-6">
        <input type="button" class="btn btn-outline btn-primary" onclick="location.href = '/category/add'"
               value="<spring:message code="category.addNew"/>">
        <br />
        <br />
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
                            <td onclick="location.href='/category/edit?id=${category.id}'" class="text-center"><a href="#"><i class="fa fa-pencil fa-lg"></i></a></td>
                            <td onclick="deleteCategory(${category.id})" class="text-center"><a href="#"><i  class="fa fa-trash-o fa-lg"></i></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
        </div>
    </div>
</div>

<div id="deleteForm">
    <h2 class="text-center">Are you sure?</h2>
    <br />
    <br />
    <div class="center-block text-center">
        <button onclick="acceptDeleting()" class="btn btn-success btn-reset-custom">Yes</button>
        <button onclick="cancelDeleting()" class="btn btn-default btn-reset-custom">No</button>
    </div>
</div>
</c:if>