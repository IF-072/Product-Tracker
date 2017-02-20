/**
 *   Created by Igor Kryviuk
 */


$(document).ready(function () {
    // todo: implement behavior when user bought less product amount
    // that he/she had planned. What program should do in this case: delete current product
    // form the shopping list or update it with new amount
    $("form[action='bought'] :button").click(function () {
        $(this).parent().submit();
    });

    /**
     * Avoid form submit by pressing
     * enter within the input number field
     */
    $("form[action='bought'] .number").keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            return false;
        }
    });

    /**
     * This function is used to show modal dialog window
     * for user to confirm or cancel deleting product
     */
    $("form[action='delete'] a").click(function () {
        var form = $(this).parent("form[action='delete']");
        var productName = $(this).attr("productName");

        $("#modalDeleteProduct").modal('show');

        $("b.productName").text(productName);

        $(".btn-confirm").click(function () {
            form.submit();
        });
        event.preventDefault();
    });


});

