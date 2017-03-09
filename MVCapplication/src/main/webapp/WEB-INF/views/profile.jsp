<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="row">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
        <div class="panel panel-default">
            <div class="panel-heading">
                <spring:message code="profile.info"/>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <tbody>
                        <tr>
                            <td>id</td>
                            <td>${user.id}</td>
                        </tr>
                        <tr>
                            <td><spring:message code="profile.name"/></td>
                            <td>${user.name}</td>
                        </tr>
                        <tr>
                            <td>email</td>
                            <td>${user.email}</td>
                        </tr>
                        <tr>
                            <td><spring:message code="profile.acc.type"/></td>
                            <td> <spring:message code="${user.role}"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- /.table-responsive -->
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <div class="col-lg-2"></div>
</div>