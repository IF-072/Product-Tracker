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
        location.href = '/stores/delStore?storeId='+Id;
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
        location.href ="/stores/delProduct?storeID=" + storId + "&&productID=" + prodId ;
        $(this).dialog("close");
    },

    Cancel:function(){
        $(this).dialog("close");}}
});

$(document).ready(function() {
    var table = $('#storeTab').DataTable();
});


