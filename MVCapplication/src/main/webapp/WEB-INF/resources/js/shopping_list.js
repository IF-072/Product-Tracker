/**
 * This function is intended for changing product amount in a shopping list
 * or deleting product from a shopping list.
 *
 * It sets input values and submits a form.
 *
 * @param prodId
 * @param val if val is positive product amount is increased by val,
 *            if val is positive product amount is decreased by val,
 *            if val equals 0 product is removed from a shopping list.
 */
function edit(prodId, val) {
    $("#product").val(prodId);
    $("#val").val(val);
    $("#edit").submit();
}
