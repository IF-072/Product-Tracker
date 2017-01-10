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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

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
                        <td>${unit.name}</td>
                        <td><a href="<c:url value="/units/remove/${unit.id}"/>">x</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <hr/>
            <h2>Add unit</h2>
            <form:form action="add"  method="post" commandName="unit">
                <div class="form-group">
                    <form:input path="name" id="name" cssClass="form-control" placeholder="Unit name"/>
                </div>
                <button type="submit" class="btn btn-primary">Add</button>
            </form:form>
            <hr/>

       </div>
    </div>
</div>
</body>
</html>