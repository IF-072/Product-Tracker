<%--
    User: Pavlo Bendus;
--%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Welcome back, ${user.name}!</h1>
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
                        <div class="huge">Go</div>
                        <div>to the cart</div>
                    </div>
                </div>
            </div>
            <a href="/cart">
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
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
                        <div>in storage</div>
                    </div>
                </div>
            </div>
            <a href="/storage">
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
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
                        <div>in shopping list</div>
                    </div>
                </div>
            </div>
            <a href="/shopping_list">
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
</div>
