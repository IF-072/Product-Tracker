function checkBox(index) {
    var checkbox = $("#checkbox"+index);
    checkbox.prop("checked",checkbox.prop("checked") ? 0 : 1);
}

function selectAll() {
    $('.checkbox').prop('checked', true);
}

function prevStep() {
    window.history.back();
}