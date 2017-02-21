var id;

// function formShow() {
//     document.getElementById('deleteForm').style.display = "block";
// }
//
// function formHide() {
//     document.getElementById('deleteForm').style.display = "none";
// }

function deleteCategory(id) {
    $("#deleteForm").dialog("open");
    this.id = id;
    console.log(id);
}

function acceptDeleting() {
    $.ajax({
        url: "categories/delete",
        method: "POST",
        data: {id: this.id}
    });

    $("#deleteForm").dialog("close");
    setTimeout(function() {window.location.reload();}, 1000);
}

function cancelDeleting() {
    $("#deleteForm").dialog("close");
}

$("#deleteForm").dialog({autoOpen:false});