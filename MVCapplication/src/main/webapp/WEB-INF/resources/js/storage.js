function allowBtn() {
    var amount = $(this).parent().find(".init").val();
    var value = $(this).val();
    var jbtn = $(this).parent().find('button.confirm');

    if (amount == value) {
        jbtn.addClass("disabled");
        jbtn.prop("disabled", true);
    } else {
        jbtn.removeClass("disabled");
        jbtn.prop("disabled", false);
    }

}

function subForm(e) {
    e.preventDefault();
    var url = $(this).closest('form').attr('action'),
        data = $(this).closest('form').serialize();
    var jbtn = $(this).find('button');
    jbtn.addClass("disabled");
    jbtn.prop("disabled", true);
    var init = $(this).find(".init");
    var inputNumValue = $(this).find(".num").val();
    var date = $(this).parent().parent().find(".date")
    $.ajax({
        url: url,
        type: 'post',
        data: data,
        dataType: 'text',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        success: function (data, textStatus, jqXHR) {
            if (data.length == 10 && jqXHR.status == 200) {
                date.text(data);
                init.val(inputNumValue);
            } else {
                $("#message").text(data);
                $("#error").modal('show');

                $(".btn-confirm").click(function () {

                    $("#error").modal('hide');
                });
            }

        },
        error: function () {
            jbtn.removeClass("disabled");
            jbtn.prop("disabled", false);
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
        url: "/storage/addToSL",
        method: "POST",
        data: {
            productId: productId
        },
        success: function () {
            $("#success").modal('show');

            $(".btn-confirm").click(function () {

                $("#success").modal('hide');
            });
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

$(document).ready(function () {
    $("form").on("submit", subForm);
    $(".addToSH").on("click", addToShoppingList);
    $(".num").on("change", allowBtn);
});
