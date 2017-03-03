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

$(document).ready(function () {
    var table = $('#storeTab').DataTable();

    $("#dialog").dialog({
        autoOpen: false
    });

    $("#yes").click(function () {
        window.location.replace("/stores/delStore?storeId=" + Id);
        $("#dialog").dialog("close");
    });

    $("#no").click(function () {
        $("#dialog").dialog("close");
    });
});


function dellProduct(storeId, productId) {
    $("#dialog").dialog("open");
    storId = storeId;
    prodId = productId;

    $(document).ready(function () {
        var table = $('#ProductInStoreTable').DataTable();

        $("#dialog").dialog({
            autoOpen: false
        });

        $("#yes").click(function () {
            window.location.replace("/stores/delProduct?storeID=" + storId + "&productID=" + prodId);
            $("#dialog").dialog("close");
        });

        $("#no").click(function () {
            $("#dialog").dialog("close");
        });

    });
};


