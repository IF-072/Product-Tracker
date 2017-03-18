/**
 *   Created by Igor Kryviuk
 */

/**
 * This function is used to make request for viewing analytics for selected product
 */
$("#selectProductSubmit").click(function () {
    var href = $(this).attr("href");
    var selectElement = document.getElementById("select");
    var selectValue = selectElement.options[selectElement.selectedIndex].value;
    href = href + selectValue;
    location.href = href;
});

/**
 * This function is used to change product for analytics view
 */
$("#selectOtherProduct").click(function () {
    location.href = $(this).attr("href");
});

