<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default credit-card-box">
                <div class="panel-heading display-table">
                    <div class="row display-tr">
                        <h3 class="panel-title display-td"><spring:message code="payment.required"/></h3>
                        <div class="display-td">
                            <img class="img-responsive pull-right" src="http://i76.imgup.net/accepted_c22e0.png">
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <form role="form" id="payment-form" method="POST" action="javascript:void(0);">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="cardNumber"><spring:message code="payment.card.number"/></label>
                                    <div class="input-group">
                                        <input type="tel" class="form-control" id="cardNumber" name="cardNumber" placeholder="XXXX XXXX XXXX XXXX" autocomplete="cc-number" required="" autofocus="">
                                        <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-7 col-md-7">
                                <div class="form-group">
                                    <label for="cardExpiry"><spring:message code="payment.expiration.date"/></label>
                                    <input type="tel" class="form-control" id="cardExpiry" name="cardExpiry" placeholder="MM / YY" autocomplete="cc-exp" required="">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-5 pull-right">
                                <div class="form-group">
                                    <label for="cardCVC">CVC/CVV2</label>
                                    <input type="tel" class="form-control" id="cardCVC" name="cardCVC" placeholder="XXX" autocomplete="cc-csc" required="">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">

                                    <div class="list-group-item disabled"><div><span class="pull-right">$10</span><spring:message code="payment.amount"/></div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <button class="subscribe btn btn-success btn-lg btn-block" type="button"><spring:message code="payment.start.subscription"/></button>
                            </div>
                        </div>
                        <div class="row" style="display:none;">
                            <div class="col-xs-12">
                                <p class="payment-errors"></p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var invalidCardNumber = '<spring:message code="payment.error.card.number"/>';
    var invalidExpireDate = '<spring:message code="payment.error.expire.date"/>';
    var invalidCvc = '<spring:message code="payment.error.cvc"/>';
    var validating = '<spring:message code="payment.validating"/>';
    var paymentSuccessful = '<spring:message code="payment.successful"/>';
</script>
