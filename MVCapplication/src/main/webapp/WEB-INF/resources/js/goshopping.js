function checkBox() {
    var checkbox = $(this).find('.checkbox');
    checkbox.prop("checked", checkbox.prop("checked") ? 0 : 1);
}

function selectAll() {
    $('.checkbox').prop('checked', true);
}

function prevStep() {
    window.history.back();
}

$(document).ready(function () {
    $("form[action='../addToCart']").on("submit", function (event) {
        if ($('.checkbox:checked').length < 1) {
            event.preventDefault();

            $("#modalInfo").modal('show');

            $(".btn-confirm").click(function () {

                $("#modalInfo").modal('hide');
            });
        }
    });


    $(".check-tr").on("click", checkBox);
    $("#selectAll").on("click", selectAll);
    $("#prevStep").on("click", prevStep);
});
