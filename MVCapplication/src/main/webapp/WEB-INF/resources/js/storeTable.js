/**
 * Created by Nazar Vynnyk
 */


var Id;
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

$(document).ready(function() {
    var table = $('#storeTab').DataTable();
});

