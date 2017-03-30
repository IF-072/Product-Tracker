/**
 * This function is used to make request for viewing analytics for selected product
 */

$("#selectProductSubmit").click(function () {
    var href = $(this).attr("href");
    var selectElement = document.getElementById("select");
    var selectValue = selectElement.options[selectElement.selectedIndex].value;
    href = href + selectValue;
    location.href = href;
});

/**
 * This function is used to change product for analytics view
 */
$("#selectOtherProduct").click(function () {
    location.href = $(this).attr("href");
});


/**
 * This block of code gets data from .jsp and displays charts of using and purchasing products
 */

$(document).ready( function() {

    var purchasingProductDatesForChart = getArray($("#purchasingProductDatesForChart"));
    var purchasingProductAmountsForChart = getArray($("#purchasingProductAmountsForChart"));
    var usingProductDatesForChart = getArray($("#usingProductDatesForChart"));
    var usingProductAmountsForChart = getArray($("#usingProductAmountsForChart"));
    var endDate = $("#endDate").text().trim();
    console.log(endDate);

    var usingProductsChartArray = convertStringArrayToInteger(usingProductAmountsForChart);
    var purchasingProductsChartArray = convertStringArrayToInteger(purchasingProductAmountsForChart);

    if (endDate) {
        usingProductDatesForChart.push(endDate);
        usingProductsChartArray.push(0);
    }

    var dataForUsingProductsChart = {
        labels: usingProductDatesForChart,
        datasets: [
            {
                label: $("#usingProducts").text(),
                borderColor: "rgba(238,28,37,0.5)",
                backgroundColor: "rgba(245,121,109,0.3)",
                data: usingProductsChartArray
            }
        ]
    };

    var purchasingProducts = $("#purchasingProducts").text();

    var dataForPurchasingProductsChart = {
        labels: purchasingProductDatesForChart,
        datasets: [
            {
                label: purchasingProducts,
                borderColor: "rgba(112,190,68,0.5)",
                backgroundColor: "rgba(173,214,138,0.3)",
                data: purchasingProductsChartArray
            }
        ]
    };

    var ctxForUsingProductsChart = $("#usingProductsChart");
    var ctxForPurchasingProductsChart = $("#purchasingProductsChart");

    var options = {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        };

    var usingProductsChart = new Chart(ctxForUsingProductsChart, {
        type: 'line',
        data : dataForUsingProductsChart,
        options: options
    });

    if (1 < dataForPurchasingProductsChart.labels.length) {
        var purchasingProductsChart = new Chart(ctxForPurchasingProductsChart, {
            type: 'line',
            data : dataForPurchasingProductsChart,
            options: options
        })
     } else {
        var message = $("#notEnoughData").text();
        $("#noDataFound").text(message);
    }

});

/**
 * This function converts string to array
 *
 * @param jqueryString
 */

function getArray(jqueryString) {
    var string = jqueryString.text();
    return string.split(" ");
}

/**
 * This function converts strings' array to integers' array
 *
 * @param array
 * @returns {Array}
 */

function convertStringArrayToInteger(array) {
    var arr = [];

    for (var i = 0; i < array.length; i++) {
        arr.push(parseInt(array[i]));
    }

    return arr;
}