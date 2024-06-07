// 📁 main.js
import { show } from './login.js';
import { showModalToEdit, saveFromModalToItem, findWrapId, findInputChildren } from './dashboard.js';

$(document).ready(function() {
    show();
    $(".expensewrap, .incomewrap").on("click", function(e) {
        const id = findWrapId(e);
        const itemWrapWithInputs = $("#" + id)[0];
        const inputChildren = findInputChildren(itemWrapWithInputs);
        showModalToEdit(id, inputChildren);
        $("#modalsavebutton")[0].innerHTML = id;
    })
    $("#modalsavebutton").on("click", saveFromModalToItem)
});

