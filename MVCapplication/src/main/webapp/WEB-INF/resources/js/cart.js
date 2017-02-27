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
        form.attr("action", "delete");
        form.attr("method", "GET");
        var productName = $(this).attr("productName");
        $("#modalDeleteProduct").modal('show');
        $("b.productName").text(productName);
        $(".btn-confirm").click(function () {
            form.submit();
        });
        event.preventDefault();
    });

});

