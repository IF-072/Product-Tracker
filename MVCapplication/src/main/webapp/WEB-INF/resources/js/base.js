$(function () {
    $(".lang-choice").click(function () {
        var locale = $(this).attr("lang");

        $.cookie('myLocaleCookie', locale, {path : '/'});

        location.reload();
    });
});

function getTimeRemaining(endtime) {
    var t = endtime * 1000 - Math.floor(Date.now());
    var seconds = Math.floor((t / 1000) % 60);
    var minutes = Math.floor((t / 1000 / 60) % 60);
    var hours = Math.floor((t / (1000 * 60 * 60)));
    var days = Math.floor(t / (1000 * 60 * 60 * 24));
    return {
        'total': t,
        'days': days,
        'hours': hours,
        'minutes': minutes,
        'seconds': seconds
    };
}

function initializeClock(id) {
    var premiumExpiresSpan = document.getElementById("premiumExpiresInfo");
    if (!premiumExpiresSpan)
        return;
    var clockDiv = document.getElementById(id);
    var endtime = clockDiv.innerHTML;
    displayRemainingTime();
    var timeinterval = setInterval(function () {
        premiumExpiresSpan.style.display = "";
        displayRemainingTime();
    }, 1000);

    function displayRemainingTime() {
        premiumExpiresSpan.style.display = "";
        var t = getTimeRemaining(endtime);
        if (t.total <= 0) {
            clearInterval(timeinterval);
            clockDiv.innerHTML = "00:00:00";
        } else {
            clockDiv.innerHTML = t.hours < 10 ? '0':'' + t.hours + ':' + ('0' + t.minutes).substr(-2) + ':' + ('0' + t.seconds).substr(-2);
        }
    }
}


initializeClock('premiumRemaining');
