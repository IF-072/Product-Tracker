/**
 * Created by Vitaliy Malisevych on 06.02.2017.
 */

var table = $('#productData').DataTable();

var prId;
var rowIndex;

function edit(productId) {
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
}

function del(productId, rI) {
    prId = productId;
    rowIndex = rI;
    $('#dialogDelete').show();
}


$('#dialogDelete').dialog({autoOpen:false, buttons:{
    Yes:function(){
        $.ajax({
            url : "http://localhost:8080/product/delProduct",
            method : "POST",
            data : {productId: prId},
            success : function(data) {
                table.row(rowIndex).remove().draw( false );
                $(this).dialog("close");
            },
            error : function(xhr, status, errorThrown) {
                alert('adding component failed with status: ' + status + ". "
                    + errorThrown);
            }
        });
    },
    Cancel:function(){
        $(this).dialog("close");
    }
}});

/*$('#productData tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
        $(this).removeClass('selected');
    }
    else {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
} );*/

$('#deleteRow').click( function () {
    table.row('.selected').remove().draw( false );
} );

//Function to add new product

$( "#addBut" ).click(function add() {
    var x = $( "#selUnit option:selected" ).val();
    /*document.getElementById("selUnit");*/
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
})