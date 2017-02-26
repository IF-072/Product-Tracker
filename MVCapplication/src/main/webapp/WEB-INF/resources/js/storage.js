function allowBtn(index, amount) {
    var tr = document.getElementsByTagName("tr");
    var jtr = $(tr[index]);
    var value = jtr.find(' input[type=number]').val();
    var jbtn = jtr.find(' button');

    if (amount == value) {
        jbtn.removeClass("btn-default");
        jbtn.addClass("disabled");
    } else {
        jbtn.removeClass("disabled");
        jbtn.addClass("btn-default");
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
        success: function () {
            jbtn.removeClass("btn-default");
            jbtn.addClass("disabled");
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
            $("#success").modal('show');

            $(".btn-confirm").click(function () {

                $("#success").modal('hide');
            });
        },
        error: function (jqXHR, exception) {
            console.log(jqXHR);
            console.log(exception);
        }
    });

}

Array.prototype.slice.call(document.getElementsByTagName("form")).forEach(function (item) {
    item.addEventListener("submit", subForm);
})
