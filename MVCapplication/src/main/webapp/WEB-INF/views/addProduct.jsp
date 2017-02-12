<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<%--
  Created by Vitaliy Malisevych
  Date: 03.02.2017
  Time: 21:40
--%>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Add product</h1>
    </div>
</div>
<br/><br/>
<form action="<c:url value="../product/addProduct"/>" name="product" enctype="text/plain" method="post">
    <table>
        <tr>
            <td><b>Name:</b></td>
            <td><input type="text" name='name' id="name"/></td>
        </tr>
        <tr>
            <td><b>Description:</b></td>
            <td><input type="text" name='description' id="description"/></td>
        </tr>
        <tr>
            <td><b>Unit</b></td>
            <td><select name='unit' id="selUnit">

                <c:forEach items="${units}" var="unit">
                    <option value="${unit}">${unit.name}</option>
                </c:forEach>

            </select></td>

        </tr>
        <tr>

            <td colspan="2"><input type="submit" value="Add new product"/></td>
        </tr>
    </table>
</form>

<button id="addBut">Add</button>

<%--
<sf:form modelAttribute="product" method="post">
    <table>
        <tr>
            <td><b>Name:</b></td>
            <td><sf:input path="name"/></td>
        </tr>
        <tr>
            <td><b>Description:</b></td>
            <td><sf:input path="description"/></td>
        </tr>
        <tr>
            <td><b>Unit</b></td>
            <td><sf:select path="unit">
                <sf:option value="NONE" label="--- Select ---"/>
                <sf:options items="${units}"/>
            </sf:select></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Add new product"/></td>
        </tr>
    </table>
</sf:form>--%>

