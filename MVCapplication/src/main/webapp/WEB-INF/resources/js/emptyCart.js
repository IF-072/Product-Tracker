function waitForElement(){
    if(typeof connected !== 'undefined' && connected === true){
        if (document.referrer.match(/.*\/cart([\/?].*)?/g)) {
            $.ajax({
                url: "/shopping/finished",
                method: "POST"
            });
        }
    }
    else{
        setTimeout(waitForElement, 250);
    }
}
$(document).ready(function () {
    waitForElement();
});

