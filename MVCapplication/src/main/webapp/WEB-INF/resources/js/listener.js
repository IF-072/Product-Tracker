var socket = new SockJS('/rest/rest');
var stompClient = Stomp.over(socket);
if (screen.width > 1170){
    $("#container-notification").css("width", "350");
} else if(screen.width > 970){
    $("#container-notification").css("width", "240");
} else {
    $("#container-notification").css("width", "100");
}
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/queue/notifications/' + id, function (frame) {
        var msg = JSON.parse(frame.body);
        msg.date = new Date(msg.date)
        $("#notification-li").removeClass("hidden")
        var ul = $("#notification");
        var text = ul.html();
        text = text.trim();
        if (text != "") {
            text += '<li class="divider"></li>';
        }
        text = text + '<li><a href="#"><div><i class="fa fa-envelope fa-fw"></i>' + msg.message +
            '<span class="pull-right text-muted small">' + msg.date.getHours() + ':' + msg.date.getMinutes() +
            '</span></div></a></li>';
        ul.html(text);

        var container = $("#container-notification");
        var notif = container.html();
        container.html(notif + '<div class="alert alert-info alert-dismissable">' +
            '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' +
            msg.message + '<a href="#" class="alert-link">' +
            msg.date.getHours() + ':' + msg.date.getMinutes() + '</a>.' +
            '</div>');
    });
});
