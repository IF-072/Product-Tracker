<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

            <%--<form:errors path="*" cssClass="errorblock" element="div"/>--%>
            <table>

                <%--<tr>--%>
                    <%--<td>Store :</td>--%>
                    <%--<td><form:select path="${store.id}">--%>
                        <%--<form:option value="NONE" label="--- Select ---"/>--%>
                        <%--<form:options items="${stores}" itemLabel="name" itemValue="id"/>--%>
                    <%--</form:select>--%>
                    <%--</td>--%>
                        <%--&lt;%&ndash;<td><form:errors path="country" cssClass="error"/></td>&ndash;%&gt;--%>
                <%--</tr>--%>



                <%--<form:bind path="${store.id}">--%>
                    <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
                        <%--<label class="col-sm-2 control-label">Store</label>--%>
                        <%--<div class="col-sm-5">--%>
                            <%--<form:select path="${store.id}" class="form-control">--%>
                                <%--<form:option value="NONE" label="--- Select ---" />--%>
                                <%--<form:options items="${stores}" itemLabel="name" itemValue="id"  multiple="true"/>--%>
                            <%--</form:select>--%>
                            <%--&lt;%&ndash;<form:errors path="country" class="control-label" />&ndash;%&gt;--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-5"></div>--%>
                    <%--</div>--%>
                <%--</form:bind>--%>

                    <label class="col-sm-2 control-label">Store</label>
                    <div class="col-sm-5">
                        <td><form:select path="${product.id}" items="${products}" itemLabel="name" itemValue="id"
                                         multiple="true"/></td>

                    <%--<form:errors path="country" class="control-label" />--%>
                    </div>
                    <div class="col-sm-5"></div>


                    <%--<tr>--%>
                        <%--<td>Java Skills :</td>--%>
                        <%--<td><form:select path="${product.id}" items="${products}" itemLabel="name" itemValue="id"--%>
                                         <%--multiple="true"/></td>--%>
                            <%--<td><form:errors path="javaSkills" cssClass="error"/></td>--%>
                    <%--</tr>--%>
                <tr>
                    <td colspan="3"><input type="submit"/></td>
                </tr>
            </table>
        </form:form>
    </div>
</div>