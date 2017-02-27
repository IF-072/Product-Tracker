function allowBtn(index, amount) {
    var tr = document.getElementsByTagName("tr");
    var jtr = $(tr[index]);
    var value = jtr.find(' input[type=number]').val();
    var jbtn = jtr.find(' button#confirm');

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
            }
        },
        error: function (jqXHR, exception) {
            console.log(jqXHR);
            console.log(exception);
            $("#message").text("Something went wrong!!!");
            $("#error").modal('show');

            $(".btn-confirm").click(function () {

                $("#error").modal('hide');
            });
        }
    });
}

function addToShoppingList(productId) {
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
        error: function (jqXHR, exception) {
            console.log(jqXHR);
            console.log(exception);
            $("#error").modal('show');

            $(".btn-confirm").click(function () {

                $("#error").modal('hide');
            });
        }
    });

}

Array.prototype.slice.call(document.getElementsByTagName("form")).forEach(function (item) {
    item.addEventListener("submit", subForm);
});
