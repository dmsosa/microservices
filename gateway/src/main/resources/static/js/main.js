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

function showWelcomeUnits() {
    $(".up-title").fadeIn(600);
    $(".left-title").fadeIn(600);
    $(".right-title").fadeIn(600);
    $(".bottom-buttons").fadeIn(600);
}
function hideWelcomeUnits() {
    $(".up-title").addClass("reversed").fadeOut(600);
    $(".left-title").addClass("reversed").fadeOut(600);
    $(".right-title").addClass("reversed").fadeOut(600);
    $(".bottom-buttons").addClass("reversed").fadeOut(600);

    $(".up-title").removeClass("reversed");
    $(".left-title").removeClass("reversed");
    $(".right-title").removeClass("reversed");
    $(".bottom-buttons").removeClass("reversed");

}
function pinButton() {
    $(".avatar-button").addClass("reversed").fadeOut(600);
    setTimeout(function() {
        $("#avatar-wrapper").addClass("pinned");
        $(".avatar-button").attr("class", "avatar-button forward plus").fadeIn(600);
    }, 1200)

}
function unpinButton() {
    $(".avatar-button").removeClass("reversed").fadeOut(600);
    showWelcomeUnits();
    setTimeout(function() {
        $("#avatar-wrapper").removeClass("pinned").removeClass("expanded");
        $(".avatar-button").attr("class", "avatar-button forward").fadeIn(600);
    }, 600)

}
function initWelcomePage() {
    $("#avatar-wrapper").show(0, function() {
        setTimeout( function() { showWelcomeUnits() },300)
    });
}

function showMainPage() {
    pinButton();
    hideWelcomeUnits();
    setTimeout(function() {$(".main-page").fadeIn(200)}, 500);
}

function showAvatarOptions() {
    $(".avatar-wrapper").toggleClass("expanded");
}
function backToHome() {
    $(".main-page").hide(0, function() {
        unpinButton();
        showWelcomeUnits();
    })

}
function showBottomPage() {
    $(".top-page").addClass("slide");
    $(".bottom-page").addClass("slide");
    setTimeout(function() {
        $(".top-page").hide();
    }, 500)

}

function handleOptionClick(jqOption) {
    const thisOptionClone = jqOption.clone();
    jqOption.parent().prev().html(thisOptionClone);
    jqOption.parent().parent().removeClass("active");
}
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
        handleOptionClick($(this));
        if ($(this).data("icon") !== iconInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("icon") === iconInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 
    $("#modal-currency .select-list .option-item").on("click", function() {
        handleOptionClick($(this));
        if ($(this).data("currency") !== currencyInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("currency") === currencyInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 
    $("#modal-category .select-list .option-item").on("click", function() {
        handleOptionClick($(this));
        if ($(this).data("category") !== categoryInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("category") === categoryInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 
    $("#modal-frequency .select-list .option-item").on("click", function() {
        handleOptionClick($(this));
        if ($(this).data("frequency") !== frequencyInitValue) {
            $(".modal-buttons").children().removeAttr("disabled");
        } else if ($(this).data("frequency") === frequencyInitValue) {
            $(".modal-buttons").children().attr("disabled", "true");
        }
    }) 

}

function modalNoteReactToChanges() {


    $(".textarea-note").on("keyup", function () { 
        if ($(this).val() !== noteInitValue) {
            $(".avatar-options .modal-buttons").children().removeAttr("disabled");
        } else if ($(this).val() === noteInitValue) {
            $(".avatar-options .modal-buttons").children().attr("disabled", "true")
        }
    });

    $(".avatar-options .select-list .option-item").on("click", function() {
        handleOptionClick($(this));
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
    $(".modal-errors").empty();

    $("#modal .modal-buttons").children().attr("disabled", "true");
    $(".modal-outer").hide();
}

function discardModalNoteChanges(event) {
    event.preventDefault();

    const initAvatar = dashboard.createOptionItem("avatar", avatarInitValue);

    $("#modal-avatar .selected-option").children().replaceWith(initAvatar);
    $(".avatar-options textarea").val(noteInitValue);
    $(".modal-errors").empty();

    $(".avatar-options .modal-buttons").children().attr("disabled", "true");
    $(".avatar-button").click();

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

    if (modalTitle.length === 0) {
        errors.push("Please indicate a title");
    }
    if (modalTitle.length < 3) {
        errors.push("Title must be at least 3 characters long");
    }
    if (modalTitle.length > 20) {
        errors.push("Title must be at most 20 characters long");
    }
    if (modalAmount === null) {
        errors.push("Amount can not be null");
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


    initWelcomePage();
    $(".avatar-button").on("click", function(e) {
        if ($("#avatar-wrapper").hasClass("pinned")) {
            showAvatarOptions();
        } else {
            showMainPage();
        }
    });
    
    $("#avatar-wrapper .back-button").on("click", backToHome);

    modalNoteReactToChanges();
    modalReactToChanges();

    $(".header-toggler").on("click", function() {
        $("#modal-note").parent().addClass("modal-show");
    });

    $(".item-wrap").on("click", function(e) {
        const id = dashboard.findWrapId(e);
        const formObject = dashboard.createFormObject(id);
        setInitValues(formObject);
        dashboard.showModalToEdit(formObject);
    });

    $(".close-button").on("click", function (e) { 
        e.preventDefault();
        $("#modal-outer").hide(0, function () { $(this).removeClass("modal-show") });
    });

    $("#modal-outer").on("click", function(e) {
        if (!e.target.closest("#modal")) {
            $(this).hide(0, function () { $(this).removeClass("modal-show") });
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

    $(".avatar-options .primary-button").on("click", dashboard.updateAccountDetails);
    $(".avatar-options .danger-button").on("click", discardModalNoteChanges);

    $("#submit-button").on("click", showBottomPage);

});
