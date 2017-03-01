/**
 * Created by Vitaliy Malisevych on 06.02.2017.
 */

function addProductToShoppingList(productId) {
    $.ajax({
        url : "../shopping_list/add",
        method : "POST",
        data : {productId: productId},
        success: function(){alert("Your product was added to shopping list!");}
    });

};

var prId;

function deleteProduct(productId) {
    $("#dialog").dialog("open");
    prId = productId;
};

$(document).ready(function() {
    var table = $('#productData').DataTable();

    $( "#dialog" ).dialog({
        autoOpen: false
    });

    $("#yes").click(function () {
        window.location.replace("/product/delProduct?productId=" + prId);
        $("#dialog").dialog("close");
    });

    $("#no").click(function () {
        $("#dialog").dialog("close");
    });
});