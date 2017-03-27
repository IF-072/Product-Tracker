/**
 * Created by Vitaliy Malisevych on 06.02.2017.
 */

function addProductToShoppingList(productId) {
    $.ajax({
        url : "../shopping_list/add",
        method : "POST",
        data : {productId: productId},
        success: function(){$("#okDialog").dialog("open");}
    });

};

var prId;

function deleteProduct(productId) {
    $("#dialog").dialog("open");
    prId = productId;
};

$(document).ready(function() {
    $('#productData').DataTable({
        "language": {
            "lengthMenu": $('#Show').text() + " _MENU_ " + $('#Products').text(),
            "zeroRecords": $('#non').text(),
            "info": $('#showing').text() + " _START_ " + $('#to').text() + " _END_ " + $('#of').text() + " _TOTAL_ " + $('#records').text(),
            "paginate": {
                "first":      "First",
                "last":       "Last",
                "next":       $('#next').text(),
                "previous":   $('#previous').text()
            },
            "search": $('#search').text()
        },
        "columnDefs": [
            { "orderable": false, "targets": [4, 5, 6, 7, 8] }
        ]
    });
    $( "#dialog" ).dialog({
        autoOpen: false
    });

    $( "#okDialog" ).dialog({
        autoOpen: false
    });

    $("#yes").click(function () {
        window.location.replace("/product/delProduct?productId=" + prId);
        $("#dialog").dialog("close");
    });

    $("#no").click(function () {
        $("#dialog").dialog("close");
    });

    $("#ok").click(function () {
        $("#okDialog").dialog("close");
    });

});