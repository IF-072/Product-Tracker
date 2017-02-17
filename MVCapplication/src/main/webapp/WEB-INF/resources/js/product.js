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
        $.ajax({
            url : "product/delProduct",
            method : "POST",
            data : {productId: prId}
        });
        $(this).dialog("close");
        setTimeout(function() {window.location.reload();}, 1000);
    },
    Cancel:function(){
        $(this).dialog("close");}}
});

$(document).ready(function() {
    var table = $('#productData').DataTable();
});