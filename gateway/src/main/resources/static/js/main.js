// 📁 main.js
import * as dashboard from './dashboard.js';
import * as stats from './stats.js';

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
        $(".avatar-button").removeClass("reversed").fadeIn(600);
    }, 1200)

}
function unpinButton() {
    $(".avatar-button").removeClass("reversed forward").fadeOut(0, function() {
        $("#avatar-wrapper").removeClass("pinned expanded");
        $(".avatar-button").addClass("forward").fadeIn();
    });
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
        $("#welcome-page").show();
        setTimeout(function() {
            showWelcomeUnits();
        }, 600)
    })

}
function showBottomPage() {
    if ($(".stats-page").children()[0].classList.contains("stats-empty")) {
        $("#form-wrapper").submit();
    }
    stats.calculateSavings();
    $(".bottom-page").show(0, function() {
        $(".top-page").addClass("slide");
        $(".bottom-page").addClass("slide");
    })
    
    setTimeout(function() {
        $(".top-page").hide();
    }, 500)

}
function hideBottomPage() {
    $(".top-page").show();
    $(".top-page").removeClass("slide");
    $(".bottom-page").removeClass("slide");

    setTimeout(function() {
        $(".bottom-page").hide();
    }, 500)
}
function toggleMainPageButtons() {
    $("#continue-button").toggleClass("show");
    $("#submit-button").toggleClass("show");
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





$(document).ready(function() {

    var incomesScrollLimit = 0;
    var incomesScrollCount = 0;
    var expensesScrollLimit = 0;
    var expensesScrollCount = 0;

    var loggedIn = $("#loggedIn").data("logged");
    if (loggedIn) {
        $("#welcome-page").hide();
        $("#avatar-wrapper").show().addClass("pinned");
        showMainPage();
    } else {
        initWelcomePage();
    };

    $(".plus").on("click", function(e) {
        if ($("#avatar-wrapper").hasClass("pinned")) {
            showAvatarOptions();
        } else {
            showMainPage();
        }
    });
    
    $("#avatar-wrapper .back-button").on("click", backToHome);

    modalNoteReactToChanges();
    modalReactToChanges();
    incomesScrollLimit = dashboard.calculateScrolls("incomes");
    expensesScrollLimit = dashboard.calculateScrolls("expenses");

    $(".close-button").on("click", dashboard.closeModal);

    $("#modal-outer").on("click", function(e) {
        if (!e.target.closest("#modal")) {
            dashboard.closeModal(e);
        }
    });
    $("#incomes-card .item-card-footer .scroll-down").on("click", function(e) {
        e.preventDefault();
        incomesScrollCount = dashboard.scrollDown("incomes", incomesScrollCount, incomesScrollLimit);
    });
    $("#incomes-card .item-card-footer .scroll-up").on("click", function(e) {
        e.preventDefault();
        incomesScrollCount = dashboard.scrollUp("incomes", incomesScrollCount);
    });
    $("#expenses-card .item-card-footer .scroll-down").on("click", function(e) {
        e.preventDefault();
        expensesScrollCount = dashboard.scrollDown("expenses", expensesScrollCount, expensesScrollLimit);
    });
    $("#expenses-card .item-card-footer .scroll-up").on("click", function(e) {
        e.preventDefault();
        expensesScrollCount = dashboard.scrollUp("expenses", expensesScrollCount);
    });

    $(".item-wrap").on("click", function(e) {
        const id = dashboard.findWrapId(e);
        const formObject = dashboard.createFormObject(id);
        setInitValues(formObject);
        dashboard.showModalToEdit(formObject);
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


    $(".addIncome, .addExpense").on("click", function(e) {
        const itemCard = dashboard.createItemCard(e);
        const itemType = itemCard.parent().parent().parent().attr("id").split("-")[0];
        if (itemType === "incomes") {
            incomesScrollLimit = dashboard.calculateScrolls("incomes");
        } else {
            expensesScrollLimit = dashboard.calculateScrolls("expenses");
        }
        itemCard.on("click", function(e) {
            const id = dashboard.findWrapId(e);
            const formObject = dashboard.createFormObject(id);
            setInitValues(formObject);
            dashboard.showModalToEdit(formObject);
        });

        itemCard.children(".delete-button").on("click", function(e) {
            e.preventDefault();
            dashboard.deleteItemCard(e);
            if (itemType === "incomes") {
                incomesScrollLimit = dashboard.calculateScrolls("incomes");
            } else {
                expensesScrollLimit = dashboard.calculateScrolls("expenses");
            }
        });

        itemCard.parent().siblings(".card-empty").hide();
        itemCard.click();
    });

    $(".delete-button").on("click", function (e) {
        $("#submit-button").removeAttr("disabled");
        dashboard.deleteItemCard(e)
        const itemType = e.currentTarget.closest(".item-card-content").parentElement.getAttribute("id").split("-")[0];
        if (itemType === "incomes") {
            incomesScrollLimit = dashboard.calculateScrolls("incomes");
        } else {
            expensesScrollLimit = dashboard.calculateScrolls("expenses");
        }
    })

    $("#modal .primary-button").on("click", function(e) {
        $("#modal-errors").empty();
        const errors = dashboard.checkErrors();
        if (errors.length > 0) {
            for (const errorMessage of errors ) {
                dashboard.appendError(errorMessage);
            }
        } else {
            dashboard.saveFromModalToItem();
            dashboard.closeModal(e);
            toggleMainPageButtons();
        }

    });
    $("#modal .danger-button").on("click", function(e) {
        discardModalChanges(e);
        dashboard.closeModal(e);
    });

    $(".avatar-options .primary-button").on("click", dashboard.updateAccountDetails);
    $(".avatar-options .danger-button").on("click", discardModalNoteChanges);

    $("#submit-button").on("click", function() {
        $("#form-wrapper").submit();
        showBottomPage();
    });

    $("#continue-button").on("click", showBottomPage);

    $(".back-to-main").on("click", hideBottomPage);

});
