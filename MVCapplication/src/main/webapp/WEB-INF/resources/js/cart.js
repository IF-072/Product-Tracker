/**
 *   Created by Igor Kryviuk
 */

$(document).ready(function () {

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
     * for user to confirm or cancel deleting product
     */
    $("a[purpose='deleteProduct']").click(function () {
        var form = $("#purchaseDeleteForm" + $(this).attr("number"));
        var href = $(this).attr("href");
        form.attr("action", href);
        form.attr("method", "GET");
        var productName = $(this).attr("productName");
        $("#modalDeleteConfirm").modal('show');
        $("b.productName").text(productName);
        $("span.pageName").text("cart");
        $(".btn-confirm").click(function () {
            form.submit();
        });
        event.preventDefault();
    });

});

