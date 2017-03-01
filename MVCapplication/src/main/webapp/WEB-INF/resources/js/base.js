$(function () {
    $(".lang-choice").click(function () {
        var currentUrl = window.location.href;
        var locale = $(this).attr("lang");

        if(currentUrl.indexOf("mylocale") > -1) {
            var newUrl = currentUrl.replace(/mylocale=[a-z][a-z]/, "mylocale=" + locale);
            window.location.replace(newUrl);
        } else {
            window.location.replace(currentUrl + "?mylocale=" + locale);
        }
    });
});