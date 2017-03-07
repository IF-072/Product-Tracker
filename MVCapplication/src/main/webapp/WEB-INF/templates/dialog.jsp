<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="dialog" hidden="hidden">
    <h2 class="text-center"><spring:message code="sure"/></h2>
    <br />
    <br />
    <div class="center-block text-center">
        <button id="yes" class="btn btn-success btn-reset-custom"><spring:message code="yes"/></button>
        <button id="no" class="btn btn-default btn-reset-custom"><spring:message code="no"/></button>
    </div>
</div>