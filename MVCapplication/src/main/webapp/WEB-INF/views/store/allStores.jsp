<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by Nazar Vynnyk
--%>

<style>
    #edit, #delete, #prod {
        text-align: center
    }
</style>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="stores"/></h1>

    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-body">

                <button type="button" class="btn btn-outline btn-primary" onclick="location.href='<c:url
                        value="/addStore"/>'"
                        id="addStoreButton"><spring:message code="store.addStore"/>
                </button>
                <br>
                <br>

                <table class="table table-striped table-bordered table-hover" id="storeTab">
                    <thead>
                    <tr>
                        <th><spring:message code="name"/></th>
                        <th><spring:message code="address"/></th>
                        <th><spring:message code="store.products"/></th>
                        <th><spring:message code="edit"/></th>
                        <th><spring:message code="delete"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${stores}" var="store">
                    <tr class="gradeA">
                        <td id="name">${store.name}</td>
                        <td id="address">${store.address}</td>
                        <td id="prod"><a href="<c:url value='/stores/storeProducts?storeId=${store.id}'/>">
                            <spring:message code="products"/></a>
                        </td>
                        <td id="edit"><a href="<c:url value='/editStore?storeId=${store.id}'/>"
                                         class="fa fa-pencil fa-lg"></a></td>
                        <td onclick="deleteStore(${store.id});" id="delete">
                            <p class="fa fa-times fa-lg"></p>
                        </td>
                    </tr>
                    </c:forEach>
                    <tbody>
                </table>

            </div>
        </div>
    </div>
</div>

