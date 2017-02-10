/**
 * Created by Vitaliy Malisevych on 06.02.2017.
 */

var table = $('#productData').DataTable();

$('#dialogAdd').dialog({autoOpen:false, buttons:{
    Add:function(){

        var product = {
            name : $('#name').val(),
            description : $('#description').val()
        }

        /*$.ajax({
            url : "http://localhost:8080/product/add",
            contentType : 'application/json',
            data : JSON.stringify(product),
            type : 'POST',
            success : function(data) {
                alert('save');
            },
            error : function(xhr, status, errorThrown) {
                alert('adding component failed with status: ' + status + ". "
                    + errorThrown);
            }

        });*/



        $.post("http://localhost:8080/product/add", product,
        $.post("http://localhost:8080/product/add", {name : $('#name').val(),'description':$('#description').val()},
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

$('#addRow').click(function(){

    $('#dialogAdd').dialog("open");
    $('#name').val('');

});

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