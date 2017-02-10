function allowBtn(index, amount) {
    var tr = document.getElementsByTagName("tr");
    var value = tr[index].children[3].children[0].children[2].value;
    var list = tr[index].children[3].children[0].children[3].classList;
    if (amount == value) {
        if (list.contains("btn-default"))
            list.remove("btn-default");
        if (!list.contains("disabled"))
            list.add("disabled");
    } else {
        if (list.contains("disabled"))
            list.remove("disabled");
        if (!list.contains("btn-default"))
            list.add("btn-default");
    }

}

function subForm(e) {
    e.preventDefault();
    var url = $(this).closest('form').attr('action'),
        data = $(this).closest('form').serialize();
    $.ajax({
        url: url,
        type: 'post',
        data: data,
    });
}

function addToShoppingList(userId, productId) {
    $.ajax({
        url: "addToSL",
        method: "POST",
        data: {
            userId: userId,
            productId: productId
        },
        success: function () {
        },
        error: function (jqXHR, exception) {
            console.log(jqXHR);
            console.log(exception);
        }
    });

}

Array.prototype.slice.call(document.getElementsByTagName("form")).forEach(function(item) {
    item.addEventListener("submit", subForm);
})