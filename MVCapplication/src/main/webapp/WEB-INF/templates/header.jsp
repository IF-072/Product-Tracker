<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <a class="navbar-brand" href="index.html">Product Tracker</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">

        <li class="dropdown">
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
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="/profile"><i class="fa fa-user fa-fw"></i> User Profile</a>
                </li>
                <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                </li>
                <li class="divider"></li>
                <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
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
                <li class="sidebar-search">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                    </div>
                    <!-- /input-group -->
                </li>
                <li>
                    <a href="<c:url value="../home"/>" class="active"><i class="fa fa-home fa-fw"></i> Home</a>
                </li>
                <li>
                    <a href="<c:url value="../product"/>" class="active"><i class="fa fa-list-alt fa-fw"></i> My
                        Products</a>
                </li>
                <li>
                    <a href="<c:url value="../stores/"/>" class="active"><i class="fa fa-globe fa-fw"></i> My
                        Stores</a>
                </li>
                <li>
                    <a href="#" class="active"><i class="fa fa-archive fa-fw"></i> Shopping List</a>
                </li>
                <li>
                    <p><button type="button" class="btn btn-outline btn-primary">Go shopping</button> </p>
                </li>
                <%--<li>--%>
                    <%--<a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Charts<span class="fa arrow"></span></a>--%>
                    <%--<ul class="nav nav-second-level collapse">--%>
                        <%--<li>--%>
                            <%--<a href="flot.html">Flot Charts</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="morris.html">Morris.js Charts</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                    <%--<!-- /.nav-second-level -->--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<a href="tables.html"><i class="fa fa-table fa-fw"></i> Tables</a>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<a href="forms.html"><i class="fa fa-edit fa-fw"></i> Forms</a>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<a href="#"><i class="fa fa-wrench fa-fw"></i> UI Elements<span class="fa arrow"></span></a>--%>
                    <%--<ul class="nav nav-second-level collapse">--%>
                        <%--<li>--%>
                            <%--<a href="panels-wells.html">Panels and Wells</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="buttons.html">Buttons</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="notifications.html">Notifications</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="typography.html">Typography</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="icons.html"> Icons</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="grid.html">Grid</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                    <%--<!-- /.nav-second-level -->--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<a href="#"><i class="fa fa-sitemap fa-fw"></i> Multi-Level Dropdown<span class="fa arrow"></span></a>--%>
                    <%--<ul class="nav nav-second-level collapse">--%>
                        <%--<li>--%>
                            <%--<a href="#">Second Level Item</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="#">Second Level Item</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="#">Third Level <span class="fa arrow"></span></a>--%>
                            <%--<ul class="nav nav-third-level collapse">--%>
                                <%--<li>--%>
                                    <%--<a href="#">Third Level Item</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a href="#">Third Level Item</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a href="#">Third Level Item</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a href="#">Third Level Item</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                            <%--<!-- /.nav-third-level -->--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                    <%--<!-- /.nav-second-level -->--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<a href="#"><i class="fa fa-files-o fa-fw"></i> Sample Pages<span class="fa arrow"></span></a>--%>
                    <%--<ul class="nav nav-second-level collapse">--%>
                        <%--<li>--%>
                            <%--<a href="blank.html">Blank Page</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a href="login.html">Login Page</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                    <%--<!-- /.nav-second-level -->--%>
                <%--</li>--%>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>

<!-- Left Sidevar -->
<div id="page-wrapper" style="min-height: 158px;">