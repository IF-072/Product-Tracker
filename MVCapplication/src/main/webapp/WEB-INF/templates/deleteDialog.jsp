<%--
  Created by Igor Kryviuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<!-- Modal window for confirm deleting-->
<div id="modalDeleteConfirm" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete</h4>
            </div>
            <div class="modal-body text-center">
                <spring:message code="deleteDialog.messagePart1"/>
                "<b class="deleteName"></b>"
                <spring:message code="deleteDialog.messagePart2"/>
                <c:choose>
                    <c:when test="${pageName eq 'cart'}">
                        <spring:message code="deleteDialog.messagePart3Cart"/>
                    </c:when>
                    <c:when test="${pageName eq 'history'}">
                        <spring:message code="deleteDialog.messagePart3History"/>
                    </c:when>
                </c:choose>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-confirm"> <spring:message code="yes"/></button>
                <button type="button" class="btn btn-outline btn-primary" data-dismiss="modal"> <spring:message code="no"/></button>
            </div>
        </div>
    </div>
</div>
