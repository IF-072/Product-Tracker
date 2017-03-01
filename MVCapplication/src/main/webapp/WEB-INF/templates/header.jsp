<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="wrapper">

<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/"><spring:message code="productTracker"/></a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">

        <li class="dropdown" hidden="hidden" style="display:none">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-alerts">
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-comment fa-fw"></i> New Comment
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                            <span class="pull-right text-muted small">12 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-envelope fa-fw"></i> Message Sent
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-tasks fa-fw"></i> New Task
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-upload fa-fw"></i> Server Rebooted
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a class="text-center" href="#">
                        <strong>See All Alerts</strong>
                        <i class="fa fa-angle-right"></i>
                    </a>
                </li>
            </ul>
            <!-- /.dropdown-alerts -->
        </li>

        <!-- languages -->
        <div class="btn-group dropdown">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                <spring:message code="changeLanguage"/>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><span class="lang-sm lang-lbl-full lang-choice" lang="en"></span></li>
                <li><span class="lang-sm lang-lbl-full lang-choice" lang="uk"></span></li>
                <li><span class="lang-sm lang-lbl-full lang-choice" lang="ru"></span></li>
            </ul>
        </div>

        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="<c:url value="/profile" />"><i class="fa fa-user fa-fw"></i> User Profile</a>
                </li>
                <li class="divider"></li>
                <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav in" id="side-menu">
                <li>
                    <a href="<c:url value="../home"/>" class="active"><i class="fa fa-home fa-fw"></i> <spring:message code="home"/></a>
                </li>
                <li>
                    <a href="<c:url value="../category"/>" class="active"><i class="fa fa-list-alt fa-fw"></i> <spring:message code="categories"/></a>
                </li>
                <li>
                    <a href="<c:url value="../product/"/>" class="active"><i class="fa fa-inbox fa-fw"></i> <spring:message code="products"/></a>
                </li>
                <li>
                    <a href="<c:url value="/shopping_list" />" class="active"><i class="fa fa-archive fa-fw"></i> <spring:message code="shoppingList"/></a>
                </li>
                <li>
                    <a href="<c:url value="../stores/"/>" class="active"><i class="fa fa-globe fa-fw"></i> <spring:message code="stores"/></a>
                </li>
                <li>
                    <a href="<c:url value="../storage/"/>" class="active"><i class="fa fa-archive fa-fw"></i> <spring:message code="myStorage"/></a>
                </li>
                <li>
                    <a href="<c:url value="../cart/"/>" class="active"><i class="fa fa-shopping-cart"></i> <spring:message code="cart"/></a>
                </li>
                <li>
                    <a href="<c:url value="../history/"/>" class="active"><i class="fa fa-calendar"></i> <spring:message code="history"/></a>
                </li>
                <li>
                    <a href="<c:url value="../goShoppingStores/"/>"><button type="button" class="btn btn-outline btn-primary"><spring:message code="goShopping"/></button></a>
                </li>

            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>

<!-- Left Sidevar -->
<div id="page-wrapper" style="min-height: 158px;">