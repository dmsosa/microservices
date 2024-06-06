// 📁 main.js
import { show } from './login.js';
import { showModalToEdit, saveFromModalToItem } from './dashboard.js';

$(document).ready(function() {
    show();
    $(".expensewrap").on("click", function(e) {
        const id = e.target.id.split(".")[0];
        const children = e.target.children;
        showModalToEdit(id, children);
        $("#modalsavebutton")[0].innerHTML = id;
    })
    $("#modalsavebutton").on("click", saveFromModalToItem)
});

