/**
 * Script provides adding new, editing and deleting categories functionality
 */

$(document).ready(function () {
    $(".categoryBtnDelete").click(function () {
        var id = $(this).attr("id");
        var name = $(this).attr("deleteName");

        $("#categoryDeleteConfirm").modal('show');

        $("b.deleteName").text(name);

        $(".btn-confirm").click(function () {
            location.href = "category/delete?id=" + id;
        });
    });

    $(".categoryBtnEdit").click(function () {
        var id = $(this).attr("id");
        window.location.href="/category/edit?id=" + id;
    });

    $(".addNewCategory").click(function () {
        window.location.href="/category/add";
    });
});
