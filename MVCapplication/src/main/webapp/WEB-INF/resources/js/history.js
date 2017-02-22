/**
 *   Created by Igor Kryviuk
 */

/**
 * This function is used to show modal dialog window
 * for user to confirm or cancel deleting product
 */
$("form[action='delete'] a").click(function () {
    var form = $(this).parent("form[action='delete']");
    var productName = $(this).attr("productName");

    $("#modalDeleteHistory").modal('show');

    $("b.productName").text(productName);

    $(".btn-confirm").click(function () {
        form.submit();
    });
    event.preventDefault();
});