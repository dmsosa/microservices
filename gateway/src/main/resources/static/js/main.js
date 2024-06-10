// 📁 main.js
import { showModalToEdit, saveFromModalToItem, findWrapId, findInputChildren } from './dashboard.js';


//wrapping all selects in custom select divs

function wrapAllSelects() {
    const allSelects = $("select");
    for (let i = 0; i < allSelects.length ; i++ ) {
        var parent = allSelects[i].parentElement;
        var customSelectWrapper = document.createElement("div");
        customSelectWrapper.setAttribute("class", "custom-select");
        parent.replaceChild(customSelectWrapper, allSelects[i]);
        customSelectWrapper.appendChild(allSelects[i]);
    }
}

function closeAllSelects() {
    const allSelects = $(".select-wrap");
    for (let i = 0 ; i < allSelects.length ; i++ ) {
        allSelects[i].classList.remove("active");
    }
}

$(document).ready(function() {
    wrapAllSelects();

    $(".selected-option").on("click", function() {
        const currentSelect = $(this).parent()[0];
        if (currentSelect.classList.contains("active")) {
            currentSelect.classList.remove("active");
        } else {
            closeAllSelects();
            currentSelect.classList.add("active");
        }
    })
    $(".select-list li").on("click", function() {
        const optionInnerHtml = $(this).html();
        $(this).parent().prev().html(optionInnerHtml);
        $(this).parent().parent().removeClass("active");
    })

    $(".expensewrap, .incomewrap").on("click", function(e) {
        const id = findWrapId(e);
        const itemWrapWithInputs = $("#" + id)[0];
        const inputChildren = findInputChildren(itemWrapWithInputs);
        showModalToEdit(id, inputChildren);
        $("#modalsavebutton")[0].innerHTML = id;
    })
    $("#modalsavebutton").on("click", saveFromModalToItem)
});
