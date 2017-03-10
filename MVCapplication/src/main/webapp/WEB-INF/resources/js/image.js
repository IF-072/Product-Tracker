/**
 * Created by Vitaliy Malisevych on 01.03.2017.
 */

$(document).ready(function() {
    $( "#dialogFileType" ).dialog({
        autoOpen: false
    });

    $("#ok").click(function () {
        $("#dialogFileType").dialog("close");
    });

    var filesExt = ['jpg', 'jpeg', 'gif', 'png'];

    $('input[type=file]').change(function(){
        var parts = $(this).val().split('.');
        if(filesExt.join().search(parts[parts.length - 1]) != -1){
            $( "input[type=submit]" ).prop( "disabled", false);
        } else {
            $("#dialogFileType").dialog("open");
            if($("submit[disabled=false]")){
                $( "input[type=submit]" ).prop( "disabled", true);
            }
        }
    });
})