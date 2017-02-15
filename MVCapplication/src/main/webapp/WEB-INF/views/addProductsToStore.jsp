<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Nazar Vynnyk
 --%>

<div class="row">

    <div class="col-lg-6">
        <h1 class="page-header">Add products to Store</h1>
    </div>
</div>
<div class="row">
    <div class="panel-body">
        <c:url var="addAction" value="/addProductsToStore"/>
        <form:form role="form" action="${addAction}" modelAttribute="myStore" method="post">
            <table>
                <tr>
                    <td>
                        <label>Your Stores</label>
                    </td>
                    <td><form:select path="${store.id}">
                        <form:option label="--- Select ---" value="-1"/>
                        <form:options items="${stores}" itemLabel="name" itemValue="id"/>
                    </form:select></td>
                </tr>

                <tr>
                    <td>
                        <label>Your Products</label>
                    </td>
                    <td><form:select path="${product.id}">
                        <form:option label="--- Select ---" value="-1"/>
                        <form:options items="${products}" itemLabel="name" itemValue="id"/>
                    </form:select></td>
                </tr>
                <br/><br/>
                <tr>
                    <td colspan="2"><input type="submit" value="Add new product"/></td>
                </tr>
            </table>
        </form:form>
    </div>
</div>