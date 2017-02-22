var id;

function deleteCategory(id) {
    $("#deleteForm").dialog("open");
    this.id = id;
    console.log(id);
}

function acceptDeleting() {
    $.ajax({
        url: "category/delete",
        method: "POST",
        data: {id: this.id}
    });

    $("#deleteForm").dialog("close");
    setTimeout(function() {window.location.reload();}, 500);
}

function cancelDeleting() {
    $("#deleteForm").dialog("close");
}

$("#deleteForm").dialog({autoOpen:false});