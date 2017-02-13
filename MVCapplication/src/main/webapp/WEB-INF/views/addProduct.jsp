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

            <td><sf:select path="unit.id">
                <sf:option label="--- Select ---" value="-1"/>
                <sf:options items="${units}" itemLabel="name" itemValue="id"/>
            </sf:select></td>
        </tr>
        <tr>
            <td><b>Category</b></td>
            <td><sf:select path="category.id">
                <sf:option label="--- Select ---" value="-1"/>
                <sf:options items="${categories}" itemLabel="name" itemValue="id"/>
            </sf:select></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Add new product"/></td>
        </tr>
    </table>
</sf:form>
<br/>
<br/>
    <b>Select a picture for your product</b>
<br/>
    <b>Here is your image</b>
    <img src="../../rest/image/${imageId}"/>
