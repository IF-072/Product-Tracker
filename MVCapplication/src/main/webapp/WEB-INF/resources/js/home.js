$(document).ready(function () {
    if (document.referrer.match(/.*\/login[^\/]*/g)) {
        $.ajax({
            url: "storage/review",
            method: "POST"
        });
    }
});
