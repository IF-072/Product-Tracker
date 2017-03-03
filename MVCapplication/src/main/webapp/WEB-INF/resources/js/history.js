/**
 *   Created by Igor Kryviuk
 */

/**
 * This function is used to show modal dialog window
 * for user to confirm or cancel deleting product
 */
$("a[purpose='deleteRecord']").click(function () {
    var href = $(this).attr("href");
    var productName = $(this).attr("productName");

    $("#modalDeleteConfirm").modal('show');

    $("b.productName").text(productName);
    $("span.pageName").text("history");


    $(".btn-confirm").click(function () {
        location.href = href;
    });
    event.preventDefault();
});