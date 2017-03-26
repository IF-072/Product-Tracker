function allowBtn() {
    var amount = $(this).attr("init");
    var value = $(this).val();
    var jbtn = $(this).parent().find('button.confirm');

    if (amount == value) {
        jbtn.addClass("disabled");
    } else {
        jbtn.removeClass("disabled");
    }

}

function subForm(e) {
    e.preventDefault();
    var url = $(this).closest('form').attr('action'),
        data = $(this).closest('form').serialize();
    var jbtn = $(this).find('button');
    var inputNum = $(this).find(".num");
    $.ajax({
        url: url,
        type: 'post',
        data: data,
        success: function (data) {
            if (data != "") {
                $("#message").text(data);
                $("#error").modal('show');

                $(".btn-confirm").click(function () {

                    $("#error").modal('hide');
                });
            } else {
                jbtn.addClass("disabled");
                inputNum.attr("init", inputNum.val());
            }

        },
        error: function () {
            $("#message").text("Something went wrong!!!");
            $("#error").modal('show');

            $(".btn-confirm").click(function () {

                $("#error").modal('hide');
            });
        }
    });
}

function addToShoppingList() {
    var productId = $(this).attr("product");
    $.ajax({
        url: "addToSL",
        method: "POST",
        data: {
            productId: productId
        },
        success: function () {
            $("#message").text("Something went wrong!!!");
            $("#success").modal('show');

            $(".btn-confirm").click(function () {

                $("#success").modal('hide');
            });
        },
        error: function () {
            $("#error").modal('show');

            $(".btn-confirm").click(function () {

                $("#error").modal('hide');
            });
        }
    });

}

$(document).ready(function () {
    $("form").on("submit", subForm);
    $(".addToSH").on("click", addToShoppingList);
    $(".num").on("change", allowBtn);
});
