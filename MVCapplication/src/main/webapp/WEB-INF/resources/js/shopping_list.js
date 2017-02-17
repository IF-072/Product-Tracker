/**
 * This function sets input values and submits a form.
 *
 * @param userId
 * @param prodId
 * @param val
 */
function edit(userId, prodId, val) {
    $("#user").val(userId);
    $("#product").val(prodId);
    $("#val").val(val);
    $("#edit").submit();
}
