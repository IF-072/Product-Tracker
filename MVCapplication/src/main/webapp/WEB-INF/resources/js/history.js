/**
 *   Created by Igor Kryviuk
 */




    /**
     * This function is used to show modal dialog window
     * for user to confirm or cancel deleting record
     */
    $("a[purpose='deleteRecord']").click(function () {
        var href = $(this).attr("href");
        var deleteName = $(this).attr("deleteName");

        $("#modalDeleteConfirm").modal('show');

        $("b.deleteName").text(deleteName);
        $("span.pageName").text("history");


        $(".btn-confirm").click(function () {
            location.href = href;
        });
        event.preventDefault();
    });

    /**
     * This function is used to show modal dialog window
     * for user to confirm or cancel deleting all records
     */
    /*$("#btn-deleteAll").click(function () {
        var href = $(this).attr("href");
        var deleteName = $(this).attr("deleteName");
        $("#modalDeleteConfirm").modal('show');
        $("b.deleteName").text(deleteName);
        $("span.pageName").text("cart");
        $(".btn-confirm").click(function () {
            location.href = href;
        });

    });*/
    /**
     * This function is used to init and configure bootstrap-datepicker
     */
    $('input[name="fromDate"]').daterangepicker({
        singleDatePicker: true,
        autoUpdateInput: false,
        locale: {
            cancelLabel: 'Clear'
        }
    });

    $('input[name="toDate"]').daterangepicker({
        singleDatePicker: true,
        autoUpdateInput: false,
        locale: {
            cancelLabel: 'Clear'
        }
    });

    $('input[name="fromDate"]').on('apply.daterangepicker', function(ev, picker) {
        $(this).val(picker.startDate.format('MM/DD/YYYY'));
        console.log(new Date(picker.startDate.format('MM/DD/YYYY')).getTime());
    });

    $('input[name="fromDate"]').on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
    });

    $('input[name="toDate"]').on('apply.daterangepicker', function(ev, picker) {
        $(this).val(picker.startDate.format('MM/DD/YYYY'));
    });

    $('input[name="toDate"]').on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
    });

    /**
     * This function is used to send request for creating PDF file
     */
    $("#pdfButton").click(function () {
     var href = $(this).attr("href");
        location.href = href;
     });