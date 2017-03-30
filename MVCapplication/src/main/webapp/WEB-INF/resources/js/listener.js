var key = "notif";
var connected = false;
var sessionUl = sessionStorage.getItem(key);
var removeAll = $("#notification").html();

var removeFunc = function () {
    $("#notification").html(removeAll);
    sessionStorage.setItem(key, removeAll);
    $("#notification-li").addClass("hidden");
};

var addToNotification = function(msg, hours) {
    $("#notification-li").removeClass("hidden");
    var ul = $("#notification");
    var text = ul.html();
    text += '<li class="divider"></li>';
    text = text + '<li><a href="#"><div><i class="fa fa-envelope fa-fw"></i>' + msg.data +
        '<span class="pull-right text-muted small">' + hours +
        '</span></div></a></li>';
    ul.html(text);
    sessionStorage.setItem(key, text);
    $("#remove-all").on("click", removeFunc);
}

var addToContainer = function(msg, hours) {
    var container = $("#container-notification");
    var notif = container.html();
    container.html(notif + '<div class="alert alert-info alert-dismissable">' +
        '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' +
        '<a href="#" class="alert-link pull-right">' + hours + '</a>' + msg.data + '</div>');
}

var id = $("#userId").attr("data");
var socket = new SockJS('/tracker');
var stompClient = Stomp.over(socket);
if (screen.width > 1170){
    $("#container-notification").css("width", "350");
} else if(screen.width > 970){
    $("#container-notification").css("width", "240");
} else {
    $("#container-notification").css("width", "100");
}

if (sessionUl != null && sessionUl.trim() != "" && sessionUl.indexOf('<li class="divider">') != -1){
    $("#notification-li").removeClass("hidden");
    if (sessionUl.indexOf('<li class="divider">') != -1) {
        sessionUl = removeAll + sessionUl.substring(sessionUl.indexOf('<li class="divider">'));
    } else {
        sessionUl = removeAll;
    }
    $("#notification").html(sessionUl);
    $("#remove-all").on("click", removeFunc);
}

stompClient.connect({}, function () {
    stompClient.subscribe('/queue/notifications/' + id, function (frame) {
        var msg = JSON.parse(frame.body);
        msg.date = new Date(msg.date);
        var hours = (msg.date.getHours() > 9 ? msg.date.getHours() : "0" + msg.date.getHours()) + ':' +
            (msg.date.getMinutes() > 9 ? msg.date.getMinutes() : "0" + msg.date.getMinutes());
        addToNotification(msg, hours);
        addToContainer(msg, hours);
    });
    connected = true;
});

$(document).ready(function () {
    $("#remove-all").on("click", removeFunc);
    $("#logout a").on("click", function () {
        sessionStorage.setItem(key, "");
    })
});
