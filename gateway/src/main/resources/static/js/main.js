// 📁 main.js
import * as dashboard from './dashboard.js';


var iconInitValue = "piggy";
var titleInitValue = "";
var amountInitValue = "";
var currencyInitValue = "USD";
var categoryInitValue = "FIXED";
var frequencyInitValue = "MONTH"; 

const noteInitValue = $("#noteInitValue").data("note");
const avatarInitValue = $("#avatarInitValue").data("avatar");

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

function setInitValues(formObject) {
    iconInitValue = formObject.get("icon");
    titleInitValue = formObject.get("title");
    amountInitValue = formObject.get("amount");
    currencyInitValue = formObject.get("currency");
    categoryInitValue = formObject.get("category");
    frequencyInitValue = formObject.get("frequency");
}

function modalReactToChanges() {
    $("#modal-title").on("keyup", function() {
        if ($(this).val() !== titleInitValue) {
            $("#modal .modal-buttons").children().removeAttr("disabled");
        } else if ($(this).val() === titleInitValue) {
            $("#modal .modal-buttons").children().attr("disabled", "true")
        }
    })
    $("#modal-amount").on("keyup", function() {
        if ($(this).val() !== amountInitValue) {
            $("#modal .modal-buttons").children().removeAttr("disabled");
        } else if ($(this).val() === titleInitValue) {
            $("#modal .modal-buttons").children().attr("disabled", "true")
        }
    });
    $("#modal-icon .select-list .option-item").on("click", function() {
        if ($(this).data("icon") !== iconInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("icon") === iconInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 
    $("#modal-currency .select-list .option-item").on("click", function() {
        if ($(this).data("currency") !== currencyInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("currency") === currencyInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 
    $("#modal-category .select-list .option-item").on("click", function() {
        if ($(this).data("category") !== categoryInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("category") === categoryInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 
    $("#modal-frequency .select-list .option-item").on("click", function() {
        if ($(this).data("frequency") !== frequencyInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("frequency") === frequencyInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 

}

function modalNoteReactToChanges() {


    $("#textarea-note").on("keyup", function () { 
        if ($(this).val() !== noteInitValue) {
            $("#modal-note .modal-buttons").children().removeAttr("disabled");
        } else if ($(this).val() === noteInitValue) {
            $("#modal-note .modal-buttons").children().attr("disabled", "true")
        }
    });

    $("#modal-avatar .select-list .option-item").on("click", function() {
        if ($(this).data("avatar") !== avatarInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("avatar") === avatarInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    })
}

function discardModalChanges(event) {
    event.preventDefault();

    const initIcon = dashboard.createOptionItem("icon", iconInitValue);
    const initCurrency = dashboard.createOptionItem("currency", currencyInitValue);
    const initCategory = dashboard.createOptionItem("category", categoryInitValue);
    const initFrequency = dashboard.createOptionItem("frequency", frequencyInitValue);

    $("#modal-icon .selected-option").children().replaceWith(initIcon);
    $("#modal-title").val(titleInitValue);
    $("#modal-amount").val(amountInitValue);
    $("#modal-currency .selected-option").children().replaceWith(initCurrency);
    $("#modal-category .selected-option").children().replaceWith(initCategory);
    $("#modal-frequency .selected-option").children().replaceWith(initFrequency);
    $("#modal-errors").empty();

    $("#modal .modal-buttons").children().attr("disabled", "true");
    $(".modal-outer").hide();
}

function checkErrors() {
    let errors = [];
    let modalTitle, modalAmount, modalIcon, modalCurrency, modalCategory, modalFrequency;
    modalTitle = $("#modal-title").val();
    modalAmount = Number($("#modal-amount").val());
    modalIcon = $("#modal-icon .selected-option .option-item").data("icon");
    modalCurrency = $("#modal-currency .selected-option .option-item").data("currency");
    modalCategory = $("#modal-category .selected-option .option-item").data("category");
    modalFrequency = $("#modal-frequency .selected-option .option-item").data("frequency");

    if (modalTitle.length < 3) {
        errors.push("Title must be at least 3 characters long");
    }
    if (modalTitle.length > 20) {
        errors.push("Title must be at most 20 characters long");
    }
    if (modalAmount < 0) {
        errors.push("Amount must be positive");
    }
    if (modalAmount > 1000000) {
        errors.push("Amount can be at most 1 million gross, for now");
    }
    if ( modalIcon === null ) {
        errors.push("Icon can not be null");
    }
    if ( modalCurrency === null ) {
        errors.push("Currency can not be null");
    }
    if ( modalCategory === null ) {
        errors.push("Category can not be null");
    }
    if ( modalFrequency === null ) {
        errors.push("Frequency can not be null");
    }
    
    return errors;
}

function appendError(errorMessage) {
    const errorTemplate = `
        <li class="error-message">${errorMessage}</li>
    `
    const errorLi = $.parseHTML(errorTemplate);
    $("#modal-errors").append(errorLi);

}

$(document).ready(function() {


    modalNoteReactToChanges();
    modalReactToChanges();

    $(".header-toggler").on("click", function() {
        $("#modal-note").parent().show();
    });

    $(".item-wrap").on("click", function(e) {
        const id = dashboard.findWrapId(e);
        const formObject = dashboard.createFormObject(id);
        setInitValues(formObject);
        console.log(amountInitValue);
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
    });


    $(".addIncome, .addExpense").on("click", dashboard.createItemCard);
    $(".delete-button").on("click", function (e) {

        $("#submit-button").removeAttr("disabled");
        dashboard.deleteItemCard(e)
    })

    $("#modal .primary-button").on("click", function(e) {
        $("#modal-errors").empty();
        const errors = checkErrors();
        if (errors.length > 0) {
            for (const errorMessage of errors ) {
                appendError(errorMessage);
            }
        } else {
            dashboard.saveFromModalToItem();
            $("#submit-button").removeAttr("disabled");
        }

    });
    $("#modal .danger-button").on("click", discardModalChanges);

    $("#modal-note .primary-button").on("click", dashboard.updateAccountDetails);
    $("#modal-note .danger-button").on("click", dashboard.discardDetailsChanges);



});
