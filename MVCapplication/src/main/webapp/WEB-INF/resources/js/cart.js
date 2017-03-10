/**
 *   Created by Igor Kryviuk
 */



    /**
     * Avoid form submit by pressing
     * enter within the input number field
     */
    $(":input[type='number']").keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            return false;
        }
    });

    /**
     * This function is used to show modal dialog window
     * for user to confirm or cancel deleting a product
     */
    $("a[purpose='deleteProduct']").click(function () {
        var form = $("#purchaseDeleteForm" + $(this).attr("number"));
        var href = $(this).attr("href");
        form.attr("action", href);
        form.attr("method", "GET");
        var deleteName = $(this).attr("deleteName");
        $("#modalDeleteConfirm").modal('show');
        $("b.deleteName").text(deleteName);
        $("span.pageName").text("cart");
        $(".btn-confirm").click(function () {
            form.submit();
        });
        event.preventDefault();
    });

    /**
     * This function is used to show modal dialog window
     * for user to confirm or cancel deleting all product
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


