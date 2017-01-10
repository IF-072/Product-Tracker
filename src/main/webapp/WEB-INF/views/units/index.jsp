<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>List of all units</title>
</head>

<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="/resources/js/units.js"></script>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
          <h2>Available units:</h2>
            <table class="table">
                <thead class="thead-default">
                    <tr>
                        <th>#</th>
                        <th>Unit Name</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="unit" items="${units}">
                    <tr>
                        <th scope="row">${unit.id}</th>
                        <td id="unit${unit.id}">${unit.name}</td>
                        <td>
                            <button type="button" class="btn btn-primary btn-xs" onclick="openModalForId(${unit.id});"><span class="glyphicon glyphicon-edit"></span> Edit</button>
                            <a href="<c:url value="/units/remove/${unit.id}"/>" class="btn btn-danger btn-xs" role="button"> <span class="glyphicon glyphicon-remove"></span> Remove</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <hr/>
            <h2>Add unit</h2>
            <form:form action="add"  method="post" commandName="unit">
                <div class="form-group">
                    <div class="input-group">
                        <form:input path="name" id="name" cssClass="form-control" placeholder="Unit name"/>
                        <span class="input-group-btn">
                            <button type="submit" class="btn btn-primary">Add</button>
                        </span>
                    </div>
                </div>
            </form:form>
            <hr/>

       </div>
    </div>
</div>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
              <span id="modalName"></span>
                <button type="button" class="close" data-dismiss="modal">&times;</button>

            </div>
            <div class="modal-body">
                <div class = "input-group">
                    <input id="newName" name="newName" class="form-control" placeholder="Enter new name..." type="text" value=""/>
                    <span class="input-group-btn">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="sendUpdateQuery()">Update</button>
   </span>
                </div>
            </div>

        </div>

    </div>
</div>

</div>

</body>
</html>