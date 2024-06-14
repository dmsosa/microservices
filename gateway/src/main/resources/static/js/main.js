// 📁 main.js
import { showModalToEdit, saveFromModalToItem, findWrapId, createFormObject, createItemCard, deleteItemCard, updateAccountDetails } from './dashboard.js';


//wrapping all selects in custom select divs

function inputNumberAdd(event) {
    const input = event.target.parentNode.previousElementSibling;
    input.value = Number(input.value) + 1;
}


function inputNumberSubstract(event) {
    const input = event.target.parentNode.previousElementSibling;
    input.value = Number(input.value) - 1;
}

function closeAllSelects() {
    const allSelects = $(".custom-select");
    for (let i = 0 ; i < allSelects.length ; i++ ) {
        allSelects[i].classList.remove("active");
    }
}

$(document).ready(function() {

    $(".header-toggler").on("click", function() {
        $("#modal-note").parent().show();
    })
    $(".close-button").on("click", function (e) { 
        e.preventDefault();
        const parentId = e.target.parentElement.parentElement.id;
        $("#" + parentId).hide();
    });
    $(".addButton").on("click", inputNumberAdd);
    $(".substractButton").on("click", inputNumberSubstract);

    $(".selected-option").on("click", function() {
        const currentSelect = $(this).parent()[0];
        if (currentSelect.classList.contains("active")) {
            currentSelect.classList.remove("active");
        } else {
            closeAllSelects();
            currentSelect.classList.add("active");
        }
    });
    $(".select-list .option-item").on("click", function() {
        const thisOptionClone = $(this).clone();
        $(this).parent().prev().html(thisOptionClone);
        $(this).parent().parent().removeClass("active");
    });

    $(".item-wrap").on("click", function(e) {
        const id = findWrapId(e);
        const formObject = createFormObject(id);
        showModalToEdit(formObject);
    });
    $(".addIncome, .addExpense").on("click", createItemCard);
    $("#modal-save-button").on("click", saveFromModalToItem);
    $("#modal-note .primary-button").on("click", function () {

    });
    $("#modal-outer").on("click", updateAccountDetails);
    $(".delete-button").on("click", deleteItemCard)
});
