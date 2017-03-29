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

$(document).ready( function() {

    function ProductObject(x, y) {
        this.x = x;
        this.y = y;
    }

    var productUsingSpeeds = $("#productUsingSpeeds").text();
    var usingProductAmounts = $("#usingProductAmounts").text();
    var usingProductDates = $("#usingProductDates").text();
    console.log(usingProductDates);

    var productUsingSpeedsArray = splitArray(productUsingSpeeds);

    var productUsingSpeedsDataArray = [];

    for(var i = 0; i < productUsingSpeedsArray.length; i++) {
        productUsingSpeedsDataArray.push(new ProductObject((i + 1), parseFloat(productUsingSpeedsArray[i])));
    }

    var ctx = $("#chartProductUsingSpeeds");
    var chartProductUsingSpeeds = new Chart(ctx, {
        type: 'line',
        data: {
            datasets: [{
                label: 'Using Speed By Days',
                data: productUsingSpeedsDataArray
            }]
        },
        options: {
            scales: {
                xAxes: [{
                    type: 'linear',
                    position: 'bottom'
                }]
            }
        }
    })
});

/**
 * This function is used to replace square brackets and split string into an array
 *
 * @param string
 * @returns {Array}
 */
function splitArray(string) {
    var newArray = string.replace('[', '').replace(']', '');
    return newArray.split(", ");
}
