function openModalForId(uid){
    $('#myModal').data("uid", uid);
    $('#myModal').modal('show');
    $('#modalName').html('Change name of element <u>' +  $('#unit'+uid).html() + '</u>');
}
function sendUpdateQuery() {

    var uid = $('#myModal').data("uid");
    var newName = $('#newName').val();

    $.ajax({
        url: window.location.pathname + "/edit/" + uid + '/name/'+newName, method: 'GET', success: function (result) {
            $('#unit'+uid).html(newName);
        }, error: function (e) {
            alert('error!');
            console.log(e);
        }
    });
}

