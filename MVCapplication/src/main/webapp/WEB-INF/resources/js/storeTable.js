/**
 * Created by Nazar Vynnyk
 */


var Id;
var prodId;
var storId;

function deleteStore(storeId) {
    $("#dialog").dialog("open");
    Id = storeId;
};

$("#dialog").dialog({autoOpen:false,buttons:{
    Delete:function(){
        $.ajax({
            url : "/stores/delStore",
            method : "POST",
            data : {storeId: Id}
        });
        $(this).dialog("close");
        setTimeout(function() {window.location.reload();}, 1000);
    },
    Cancel:function(){
        $(this).dialog("close");}}

});

function dellProduct(storeId, productId) {
    $("#dialogDel").dialog("open");
    storId = storeId;
    prodId = productId;
};

$("#dialogDel").dialog({autoOpen:false,buttons:{
    Delete:function(){
        $.ajax({
            url : "/stores/delProduct",
            method : "POST",
            data : {
                storeID: storId,
                 productID: prodId}
        });
        $(this).dialog("close");
        setTimeout(function() {window.location.reload();}, 1000);
    },
    Cancel:function(){
        $(this).dialog("close");}}
});


$(document).ready(function() {
    var table = $('#storeTab').DataTable();
});

