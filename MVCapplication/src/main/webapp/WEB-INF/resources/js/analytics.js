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

function getAnArray(jqueryString) {
    var string = jqueryString.text();
    return string.split(" ");
}

$(document).ready( function() {

    var purchasingProductDatesForChart = getAnArray($("#purchasingProductDatesForChart"));
    var purchasingProductAmountsForChart = getAnArray($("#purchasingProductAmountsForChart"));
    var usingProductDatesForChart = getAnArray($("#usingProductDatesForChart"));
    var usingProductAmountsForChart = getAnArray($("#usingProductAmountsForChart"));



    var chartArr = [];

    for (var i = 0; i < arr1.length; i++) {
        chartArr.push(parseInt(arr2[i]));
    }

    // console.log(chartArr);

    var data = {
        labels: arr1,
        datasets: [
            {
                label: "Using Products",
                // fillColor: "rgba(220,220,220,0.2)",
                // strokeColor: "rgba(0,0,0,1)",
                // pointColor: "rgba(220,220,220,1)",
                // //pointStrokeColor: "#000",
                // //pointHighlightFill: "#000",
                // pointHighlightStroke: "rgba(220,220,220,1)",
                borderColor: "rgba(112,190,68,0.5)",
                backgroundColor: "rgba(173,214,138,0.3)",
                data: chartArr
            },
            // {
            //     label: "My Second dataset",
            //     fillColor: "rgba(151,187,205,0.2)",
            //     strokeColor: "rgba(151,187,205,1)",
            //     pointColor: "rgba(151,187,205,1)",
            //     pointStrokeColor: "#fff",
            //     pointHighlightFill: "#fff",
            //     pointHighlightStroke: "rgba(151,187,205,1)",
            //     data: [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]
            // }
        ]
    };

    var ctx = document.getElementById("chart").getContext("2d");
    var options = {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        };
    var chart = new Chart(ctx, {
        type: 'line',
        data : data,
        options: options
    });
});
