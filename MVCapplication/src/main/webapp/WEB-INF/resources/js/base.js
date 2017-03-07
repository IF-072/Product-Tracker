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



function getTimeRemaining(endtime){
    var t = endtime * 1000 - Math.floor(Date.now());
    var seconds = Math.floor( (t/1000) % 60 );
    var minutes = Math.floor( (t/1000/60) % 60 );
    var hours = Math.floor( (t/(1000*60*60)) % 24 );
    var days = Math.floor( t/(1000*60*60*24) );
    return {
        'total': t,
        'days': days,
        'hours': hours,
        'minutes': minutes,
        'seconds': seconds
    };
}

function initializeClock(id){
    var premiumExpiresSpan = document.getElementById("premiumExpiresInfo");
    if(!premiumExpiresSpan)
        return;
    var clock = document.getElementById(id);
    var endtime = clock.innerHTML;
    var timeinterval = setInterval(function(){
        premiumExpiresSpan.style.display="";
        var t = getTimeRemaining(endtime);
        clock.innerHTML = t.hours + ':' + t.minutes + ':' +t.seconds;
        if(t.total<=0){
            clearInterval(timeinterval);
        }
    },1000);
}

initializeClock('premiumRemaining');