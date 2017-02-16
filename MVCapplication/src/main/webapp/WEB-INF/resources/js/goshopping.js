function checkBox(index) {
    var checkbox = document.getElementById("checkbox"+index);
    if(checkbox.checked == true){
        checkbox.checked = false;
    } else{
        checkbox.checked = true;
    }
}

function selectAll() {
    $('input[type=checkbox]').prop('checked', true);
}

function prevStep() {
    window.history.back();
}