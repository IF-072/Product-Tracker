/**
 * Created by Vitaliy Malisevych on 06.02.2017.
 */

var table = $('#productData').DataTable();

var prId;

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

function del(productId) {
    prId = productId;
    $('#dialogDelete').show();
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


$('#dialogDelete').dialog({autoOpen:false, buttons:{
    Yes:function(){

        $.ajax({
            url : "delete",
            method : "POST",
            data : {productId: prId},
            success : function(data) {
                table.row('.selected').remove().draw( false );
            },
            error : function(xhr, status, errorThrown) {
                alert('adding component failed with status: ' + status + ". "
                    + errorThrown);
            }

        });



        $.post("http://localhost:8080/product/add", product,
            function(result){
                $('#productData').dataTable().fnAddData([
                    $('#name').val(),
                    $('#description').val(),
                    $('#category').val(),
                    $('#unit').val(),
                    $('#image').val(),
                    $('#stores').val()
                ]);
                alert (result)
            });

        $(this).dialog("close");
    },
    Cancel:function(){
        $(this).dialog("close");
    }
}});

$('#productData tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
        $(this).removeClass('selected');
    }
    else {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
} );

$('#deleteRow').click( function () {
    table.row('.selected').remove().draw( false );
} );