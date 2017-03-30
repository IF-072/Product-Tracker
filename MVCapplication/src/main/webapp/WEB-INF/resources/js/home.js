function waitForElement(){
    if(typeof connected !== 'undefined' && connected === true){
        if (document.referrer.match(/^([^/]*\/\/[^/]*\/)(login(\?\w*)?)?$/g)) {
            $.ajax({
                url: "storage/review",
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
