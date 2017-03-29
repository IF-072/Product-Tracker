<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="row">
    <div class="panel panel-default">
        <div class="panel-heading">
            <spring:message code="shoppingList"/>
        </div>
        <!-- /.panel-heading -->
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <tbody>
                    <tr>
                        <th id="id-col">#</th>
                        <th id="prod-col"><spring:message code="product"/></th>
                        <th id="am-col"><spring:message code="amount"/></th>
                        <th id="cat-col"><spring:message code="category"/></th>
                        <th><spring:message code="image"/></th>
                        <th id="edit-col" colspan="2"><spring:message code="edit"/></th>
                        <th id="del-col"><spring:message code="delete"/></th>
                    </tr>
                    <c:forEach items="${shoppingList}" var="elem" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td>${elem.product.name}</td>
                            <td id="am${loop.count}">${elem.amount} ${elem.product.unit.name}</td>
                            <td>${elem.product.category.name}</td>
                            <td class="align-mid">
                                <c:if test="${elem.product.image != null}">
                                    <img src="<c:url value="/image/${elem.product.image.id}"/>" width="50" height="50">
                                </c:if>
                                <c:if test="${elem.product.image == null}">
                                    <img src="<c:url value="/img/noimage.jpg"/>" width="50" height="50">
                                </c:if>
                            </td>
                            <td class="align-mid"><p class="fa fa-minus-square fa-lg fa-lg edit"
                                                     prodId="${elem.product.id}" val="-1" index="${loop.count}"/></td>
                            <td class="align-mid"><p class="fa fa-plus-square fa-lg fa-lg edit"
                                                     prodId="${elem.product.id}" val="1" index="${loop.count}"/></td>
                            <td class="align-mid"><p prodId="${elem.product.id}" class="fa fa-times fa-lg del"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
        <!-- /.panel-body -->
    </div>
    <!-- /.panel -->
</div>