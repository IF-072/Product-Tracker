/**
 * Created by Nazar Vynnyk
 */

var table = $('#storeTab').DataTable();


    function productList(storeId) {
        $.ajax({
            url: "/stores/storeProducts",
            method: "GET",
            data: {
                storeId: storeId
            },
            success: function () {
            },
            error: function (jqXHR, exception) {
                console.log(jqXHR);
                console.log(exception);
            }
        });
    }