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
            url : "http://localhost:8080/product/delProduct",
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


/*

function deleteProduct(productId) {
    $('#dialogDelete').dialog();
    prId = productId;
    //rowIndex = this.rowIndex;
};*/

//$('#dialogDelete').dialog({autoOpen:false, buttons:{
    //Yes:function(){
        //$(this).dialog("close");
        /*$.ajax({
            url : "http://localhost:8080/product/delProduct",
            method : "POST",
            data : {productId: prId},
            success : function(data) {
                table.row(rowIndex).remove().draw( false );
                $(this).dialog("close");
            },
            error : function(xhr, status, errorThrown) {
                alert('adding component failed with status: ' + status + ". " + errorThrown);
            }
        });*/
    //},
   // Cancel:function(){
     //   $(this).dialog("close");
   // }
// }});



/*$('#deleteRow').click( function () {
    table.row('.selected').remove().draw( false );
} );*/


/*function edit(productId) {
        $.ajax({
            url: "update",
            method: "GET",
            data: {
                productId: productId
            },
            error: function (jqXHR, exception) {
                console.log(jqXHR);
                console.log(exception);
            }
        });
}*/





/*$('#productData tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
        $(this).removeClass('selected');
    }
    else {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
} );*/



/*
$('#addRow').click( function () {
    $('#dialogDelete').dialog();
} );

//Function to add new product

$( "#addBut" ).click(function add() {
    var x = $( "#selUnit option:selected" ).val();
    /!*document.getElementById("selUnit");*!/
    var product = {
        name: $("#name").val(),
        description: $("#description").val(),
        unit: {id: '1', name: 'кг'}
    };

    $.ajax({
        url: 'http://localhost:8080/product/addProduct',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data: JSON.stringify(product)
    });
});*/
