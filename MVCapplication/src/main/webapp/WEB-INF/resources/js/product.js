/**
 * Created by Vitaliy Malisevych on 06.02.2017.
 */

var prId;
var rowIndex;

function deleteProduct(productId) {
    $("#dialogDelete").dialog("open");
    prId = productId;
};

$("#dialogDelete").dialog({autoOpen:false,buttons:{
    Delete:function(){
        location.href = '/product/delProduct?productId='+prId;
    },
    Cancel:function(){
        $(this).dialog("close");}}
});

function addProductToShoppingList(productId) {
    $.ajax({
        url : "../shopping_list/add",
        method : "POST",
        data : {productId: productId}
    });
};

$(document).ready(function() {
    var table = $('#productData').DataTable();
});