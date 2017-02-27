/**
 * This function is intended for changing product amount in a shopping list
 * or deleting product from a shopping list.
 *
 * It sets input values and submits a form.
 *
 * @param prodId
 * @param val if val is positive product amount is increased by val,
 *            if val is positive product amount is decreased by val.
 * @param index index of updating element
 */
function edit(prodId, val, index) {
    $.ajax({
        url: "/shopping_list/edit",
        method: "POST",
        data: {
            prodId: prodId,
            val: val
        },
        success: function (data) {
            var amount = $("#am" + index);
            amount.text(data);
        }
    })
}

$(function () {
    var prodId;

    $( "#dialog" ).dialog({
        autoOpen: false
    });

    $(".del").click(function() {
        prodId = $(this).attr("prodId");
        $("#dialog").dialog("open");
    });

    $("#yes").click(function () {
        window.location.replace("/shopping_list/delete?prodId=" + prodId);
        $("#dialog").dialog("close");
    });

    $("#no").click(function () {
        $("#dialog").dialog("close");
    });
});