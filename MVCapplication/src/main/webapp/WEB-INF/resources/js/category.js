var id;
$("#deleteForm").dialog({autoOpen:false});

$(document).ready(function () {
    $(".categoryBtnDelete").click(function () {
        id = $(this).attr("id");
        $("#deleteForm").dialog("open");
    });

    $(".btnAcceptDeleting").click(function () {
        window.location.replace("category/delete?id=" + id);
    });

    $(".btnCancelDeleting").click(function () {
        $("#deleteForm").dialog("close");
    });

    $(".categoryBtnEdit").click(function () {
        var id = $(this).attr("id");
        window.location.href="/category/edit?id=" + id;
    });

    $(".addNewCategory").click(function () {
        window.location.href="/category/add";
    });
});
