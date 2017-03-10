/**
 *   Created by Igor Kryviuk
 */




    /**
     * This function is used to show modal dialog window
     * for user to confirm or cancel deleting record
     */
    $("a[purpose='deleteRecord']").click(function () {
        var href = $(this).attr("href");
        var deleteName = $(this).attr("deleteName");

        $("#modalDeleteConfirm").modal('show');

        $("b.deleteName").text(deleteName);
        $("span.pageName").text("history");


        $(".btn-confirm").click(function () {
            location.href = href;
        });
        event.preventDefault();
    });

    /**
     * This function is used to show modal dialog window
     * for user to confirm or cancel deleting all records
     */
    $("#btn-deleteAll").click(function () {
        var href = $(this).attr("href");
        var deleteName = $(this).attr("deleteName");
        $("#modalDeleteConfirm").modal('show');
        $("b.deleteName").text(deleteName);
        $("span.pageName").text("cart");
        $(".btn-confirm").click(function () {
            location.href = href;
        });

    });
