<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by NazarVynnyk
--%>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="store"/></h1>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <div class="panel panel-primary">
                <div class="panel-heading text-center">
                    <h4><spring:message code="store.disabled"/></h4>
                </div>
                <div class="panel-body text-center">
                    <p><spring:message code="store.retrieveWish"/></p>
                </div>
                <div class="panel-footer">
                    <div class="btn-group btn-group-justified" role="group" aria-label="...">
                        <div class="btn-group" role="group">
                            <a href="<c:url value='/retrieveStore?storeId=${store.id}'/>">
                                <button type="button" class="btn btn-default"><spring:message code="ok"/></button>
                            </a>
                        </div>
                        <div class="btn-group" role="group">
                            <a href="<c:url value='/addStore'/>">
                                <button type="button" class="btn btn-default"><spring:message code="cancel"/></button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>