// Modal

export function showModalToEdit(formObject, editMode = true) {
    $("#modal-outer").show(0, function () {
        $(this).addClass("modal-show")
    });
    $("#modal-title").val(formObject.get("title"));
    $("#modal-amount").val(formObject.get("amount"));
    const modalIcon = createOptionItem("icon", formObject.get("icon"));
    const modalCurrency = createOptionItem("currency", formObject.get("currency"));
    const modalCategory = createOptionItem("category", formObject.get("category"));
    const modalFrequency = createOptionItem("frequency", formObject.get("frequency"));  
    $("#modal-icon .selected-option").children().replaceWith(modalIcon);
    $("#modal-currency .selected-option").children().replaceWith(modalCurrency);
    $("#modal-category .selected-option").children().replaceWith(modalCategory);
    $("#modal-frequency .selected-option").children().replaceWith(modalFrequency);

    if (editMode) {
        $("#modal-target").attr("value", formObject.get("id"));
    }
}


export function saveFromModalToItem() {
    let modalTitle, modalAmount, modalIcon, modalCurrency, modalCategory, modalFrequency;
    modalTitle = $("#modal-title").val();
    modalAmount = $("#modal-amount").val();
    modalIcon = $("#modal-icon .selected-option .option-item").data("icon");
    modalCurrency = $("#modal-currency .selected-option .option-item").data("currency");
    modalCategory = $("#modal-category .selected-option .option-item").data("category");
    modalFrequency = $("#modal-frequency .selected-option .option-item").data("frequency");

    const target = $("#modal-target").val();

    $("#" + target + "\\.title").val(modalTitle);
    $("#" + target + "\\.amount").val(modalAmount);
    $("#" + target + "\\.icon").val(modalIcon);
    $("#" + target + "\\.currency").val(modalCurrency);
    $("#" + target + "\\.category").val(modalCategory);
    $("#" + target + "\\.frequency").val(modalFrequency);

    $("#" + target + " .card-icon .card-img").attr("class", "card-img " + modalIcon.toLowerCase());

}
export function findWrapId(event) {
    let id = event.target.id.split(".")[0];
    let parent = event.target.parentElement;
    let count = 0;
    while ( id.length < 1 && count < 2 ) {
        id = parent.id;
        parent = parent.parentElement;
        count++;
    }
    return id;
}

export function createFormObject(id) {
    const formObject = new Map(); 
    var id, title, amount, icon, currency, category, frequency;
    title = $("#" + id + "\\.title")[0] ? $("#" + id + "\\.title")[0].value : "";
    amount = $("#" + id + "\\.amount")[0] ? $("#" + id + "\\.amount")[0].value : "0";
    icon = $("#" + id + "\\.icon")[0] ? $("#" + id + "\\.icon")[0].value : "house";
    currency = $("#" + id + "\\.currency")[0] ? $("#" + id + "\\.currency")[0].value : "USD";
    category = $("#" + id + "\\.category")[0] ? $("#" + id + "\\.category")[0].value : "FIXED";
    frequency = $("#" + id + "\\.frequency")[0] ? $("#" + id + "\\.frequency")[0].value : "DAY";

    formObject.set("id", id);
    formObject.set("title", title);
    formObject.set("amount", amount);
    formObject.set("icon", icon);
    formObject.set("currency", currency);
    formObject.set("category", category);
    formObject.set("frequency", frequency);

    return formObject;
}

export function createOptionItem(fieldName, fieldValue) {
    const optionTemplate = `
        <div class="option-item ${fieldValue}" data-${fieldName}="${fieldValue}">
            <div class="option-text">${fieldValue}</div>
        </div>
    `
    const iconTemplate = `
        <div class="option-item ${fieldValue}" data-${fieldName}="${fieldValue}">
            <div class="option-icon"></div>
        </div>
    `
    const result = fieldName === "icon" ? $.parseHTML(iconTemplate) : $.parseHTML(optionTemplate);
    return result;
}

// Create card item

export function createItemCard(event) {
    event.preventDefault();
    var itemType = event.target.classList.contains("addIncome") ? "incomes":"expenses";
    var index = $("#" + itemType + "-card .item-card-content .item-list")[0].children.length;
    const template = `
    <div id="${itemType + index}" class="item-wrap">
            <div class="hiddeninputs">
                <input name="${itemType}[${index}].id" id="${itemType + index}.id" type="hidden" value="">
                <input name="${itemType}[${index}].accountName" id="${itemType + index}.accountName"type="hidden" value="demo">
                <input name="${itemType}[${index}].type" id="${itemType + index}.type" type="hidden" value="INCOME">
                <input name="${itemType}[${index}].category" id="${itemType + index}.category" type="hidden" value="FIXED">
            </div>
            <div class="card-icon">
                <div class="card-img sport"></div>
                <input name="${itemType}[${index}].icon" id="${itemType + index}.icon" type="hidden" value="sport" readonly>
            </div>
            <div class="card-details">
                <input name="${itemType}[${index}].title" id="${itemType + index}.title" class="card-title" value="" readonly>
                <div class="card-divisor"></div>
                <input name="${itemType}[${index}].amount" id="${itemType + index}.amount" class="card-amount" value="" readonly> 
                <input name="${itemType}[${index}].currency" id="${itemType + index}.currency" class="card-currency" value="USD" readonly>
                <div class="card-slash"></div>
                <input name="${itemType}[${index}].frequency" id="${itemType + index}.frequency" class="card-frequency" value="DAY" readonly>
            </div>
            <button class="delete-button">
                <span class="material-symbols-outlined delete-icon">delete</span>
            </button>
        </div>`;
    
    var itemCard = $.parseHTML(template);
    $("#" + itemType + "-card .item-card-content .item-list").append(itemCard);

    return $(`#${itemType + index}`);
}

export function deleteItemCard(event) {
    event.stopPropagation();
    event.target.parentElement.parentElement.remove();
}


export function updateAccountDetails(event) {
    event.stopPropagation();
    let avatar;
    avatar = $(".custom-select-avatars .selected-option .option-item").data("avatar");
    $(".account-avatar").val(avatar);
    $("#note-form").submit();
}


export function checkErrors() {
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

export function appendError(errorMessage) {
    const errorTemplate = `
        <li class="error-message">${errorMessage}</li>
    `
    const errorLi = $.parseHTML(errorTemplate);
    $("#modal-errors").append(errorLi);

}

export function closeModal(event) {
    event.preventDefault();
    $("#modal-outer").removeClass("modal-show");
    setTimeout(function() {
        $("#modal-outer").hide();
    }, 200)
}

export function scrollDown(itemType, scrollCount, scrollLimit) {
    if (scrollCount >= scrollLimit) return scrollCount;
    let pos;

    if (itemType === "incomes") {
        pos = Number($("#incomes-card .item-list").css("top").replace("px", ""));
        $("#incomes-card .item-list").css("top", Number(pos - 80).toString() + "px");
    } else {
        pos = Number($("#expenses-card .item-list").css("top").replace("px", ""));
        $("#expenses-card .item-list").css("top", Number(pos - 80).toString() + "px");
    }

    return scrollCount+1;
}
export function scrollUp(itemType, scrollCount) {
    if (!scrollCount > 0) return scrollCount;
    let pos;
    if (itemType === "incomes") {
        pos = Number($("#incomes-card .item-list").css("top").replace("px", ""));
        $("#incomes-card  .item-list").css("top", Number(pos + 80).toString() + "px");
    } else {
        pos = Number($("#expenses-card .item-list").css("top").replace("px", ""));
        $("#expenses-card  .item-list").css("top", Number(pos + 80).toString() + "px");
    }
    return scrollCount-1;
}

export function calculateScrolls(itemType) {
    let itemNumber;
    if (itemType === "incomes") {
        itemNumber = $("#incomes-card .item-list").children().length;
    } else {
        itemNumber = $("#expenses-card .item-list").children().length;
    }
    if (itemNumber > 4) {
        return (Math.floor(itemNumber / 4) - 1) + (itemNumber % 4);
    }
    else return 0; 
}