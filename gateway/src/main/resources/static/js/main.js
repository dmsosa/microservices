// 📁 main.js
import * as dashboard from './dashboard.js';

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

    const noteInitValue = $("#noteInitValue").data("note");
    const avatarInitValue = $("#avatarInitValue").data("avatar");

    $(".header-toggler").on("click", function() {
        $("#modal-note").parent().show();
    });

    $(".item-wrap").on("click", function(e) {
        const id = findWrapId(e);
        const formObject = dashboard.createFormObject(id);
        dashboard.showModalToEdit(formObject);
    });

    $(".close-button").on("click", function (e) { 
        e.preventDefault();
        $(".modal-outer").hide();
    });

    $(".modal-outer").on("click", function(e) {
        if (!e.target.closest("#modal") && !e.target.closest("#modal-note")) {
            $(this).hide();
        }
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
        $(".modal-buttons").children().removeAttr("disabled");
    });


    $(".addIncome, .addExpense").on("click", dashboard.createItemCard);
    $(".delete-button").on("click", dashboard.deleteItemCard)

    $("#modal-save-button").on("click", dashboard.saveFromModalToItem);
    $("#modal-note .primary-button").on("click", dashboard.updateAccountDetails);

    $("#textarea-note").on("keyup", function (e) { 
        
        if ($(this).val() !== noteInitValue) {
            console.log(noteInitValue, $(this).val())
            console.log($("#modal-note .modal-buttons").children());
            $("#modal-note .modal-buttons").children().removeAttr("disabled");
        } else if ($(this).val() === noteInitValue) {
            $("#modal-note .modal-buttons").children().attr("disabled", "true")
        }
    });

    $("#modal-note .danger-button").on("click", dashboard.discardDetailsChanges);


});
