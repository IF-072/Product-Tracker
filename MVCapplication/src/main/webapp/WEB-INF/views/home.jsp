<%--
    User: Pavlo Bendus;
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><spring:message code="home.helloMessage" />, ${user.name}!</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<!-- Information pannels -->
<div class="row">
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-shopping-cart fa-5x"></i>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge"><spring:message code="home.go"/></div>
                        <div><spring:message code="home.cart"/></div>
                    </div>
                </div>
            </div>
            <a href="<c:url value="/cart"/>">
                <div class="panel-footer">
                    <span class="pull-left"><spring:message code="home.viewDetails"/></span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-archive fa-5x"></i>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">${storage}</div>
                        <div><spring:message code="home.storage"/></div>
                    </div>
                </div>
            </div>
            <a href="<c:url value="/storage"/>">
                <div class="panel-footer">
                    <span class="pull-left"><spring:message code="home.viewDetails"/></span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-archive fa-5x"></i>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">${shoplist}</div>
                        <div><spring:message code="home.shopList"/></div>
                    </div>
                </div>
            </div>
            <a href="<c:url value="/shopping_list"/>">
                <div class="panel-footer">
                    <span class="pull-left"><spring:message code="home.viewDetails"/></span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
</div>
