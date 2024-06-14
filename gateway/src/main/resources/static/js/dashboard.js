// Modal
export function showModalToEdit(formObject, editMode = true) {
    $("#modal-outer").show();
    $("#modal-title").attr("value", formObject.get("title"));
    $("#modal-amount").attr("value", formObject.get("amount"));
    const modalIcon = $("#modal-icon .selected-option .option-item");
    modalIcon.attr("class", "option-item " + formObject.get("icon"));
    modalIcon.data("icon", formObject.get("icon"));
    createOptionItemForCustomSelect(formObject);
    if (editMode) {
        $("#modal-target").attr("value", formObject.get("id"));
    }
}


export function saveFromModalToItem() {
    let modalTitle, modalAmount, modalIcon, modalCurrency, modalCategory, modalFrequency;
    modalTitle = $("#modal-title")[0].value;
    modalAmount = $("#modal-amount")[0].value;
    modalIcon = $("#modal-icon .selected-option .option-item").data("icon");
    modalCurrency = $("#modal-currency .selected-option .option-item").data("currency");
    modalCategory = $("#modal-category .selected-option .option-item").data("category");
    modalFrequency = $("#modal-frequency .selected-option .option-item").data("frequency");

    const target = $("#modal-target")[0].value;

    $("#" + target + "\\.title")[0].value = modalTitle;
    $("#" + target + "\\.amount")[0].value = modalAmount;
    $("#" + target + "\\.icon")[0].value = modalIcon;
    $("#" + target + "\\.currency")[0].value = modalCurrency;
    $("#" + target + "\\.category")[0].value = modalCategory;
    $("#" + target + "\\.frequency")[0].value = modalFrequency;

    $("#" + target + " .card-icon .card-img").attr("class", "card-img " + modalIcon.toLowerCase());

    $("#modal-outer").hide();
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

export function createOptionItemForCustomSelect(formObject) {
    const properties = ["currency", "category", "frequency"];
    for (const property of properties ) {
        var optionItem = $.parseHTML(`
            <div class=\"option-item\" data-currency=\"${formObject.get(property)}\">
                <p class=\"option-text\">${formObject.get(property)}</p>
            </div>`
        );
        $("#" + property + "-select .selected-option").html(optionItem);
    }
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
                <div class="card-img house"></div>
                <input name="${itemType}[${index}].icon" id="${itemType + index}.icon" type="hidden" value="piggy" readonly>
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

    $(`#${itemType + index}`).on("click", function(e) {
        const id = findWrapId(e);
        const formObject = createFormObject(id);
        showModalToEdit(formObject);
    });
    $(".delete-button").on("click", deleteItemCard);

    $(`#${itemType + index}`).click();
    $(`#${itemType}-card .item-card-content .card-empty`).hide();

}

export function deleteItemCard(event) {
    event.stopPropagation();
    event.target.parentElement.parentElement.remove();
}

export function updateAccountDetails(event) {
    event.stopPropagation();
    let avatar;
    avatar = $(".custom-select-avatars .selected-option .option-item").data("avatar");
    $("#modal-avatar").attr("value")
    $("#note-form").submit();
}