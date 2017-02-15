function checkBox(index) {
    var checkbox = document.getElementById("checkbox"+index);
    if(checkbox.checked == true){
        checkbox.checked = false;
    } else{
        checkbox.checked = true;
    }
}
