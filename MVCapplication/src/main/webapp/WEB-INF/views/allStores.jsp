<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<!DOCTYPE html>
<html>
<head>
    <title>UserStores</title>
</head>
<body>
<a href="<c:url value="../"/>">Stores</a> | <a href="<c:url value="../addStore"/>">Add new Store</a> | <a href="<c:url
value="allStores"/>">Show All Stores</a>
<br/><br/>
<table border="1">
    <tr>
        <th>name</th>
        <th>address</th>
        <th colspan="2">Edit / Delete</th>
    </tr>
    <c:forEach items="${stores}" var="store">
        <tr>
            <td><c:out value="${store.name}"/></td>
            <td><c:out value="${store.address}"/></td>
            <%--<td><a href="<c:url value="editStore?id=${user.id}"/>">Edit</a></td>--%>
            <%--<td><a href="<c:url value="deleteStore?id=${user.id}"/>">Delete</a></td>--%>
        </tr>
    </c:forEach>
</table>
</body>
</html>
