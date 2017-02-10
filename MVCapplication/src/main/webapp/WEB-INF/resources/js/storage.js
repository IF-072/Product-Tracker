function minus(userId, productId, index) {
    var tr = document.getElementsByTagName("tr");
    var amount = tr[index].children[3].innerHTML;
    amount--;
    if (amount >= 0) {
        $.ajax({
            url: "update",
            method: "POST",
            data: {
                userId: userId,
                productId: productId,
                amount: amount
            },
            success: function () {
                tr[index].children[3].innerHTML = amount;
            },
            error: function (jqXHR, exception) {
                console.log(jqXHR);
                console.log(exception);
            }
        });
    }
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