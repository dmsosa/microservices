function showModalToEdit(id, itemFieldInputs) {
    let formObject = {
        icon: "",
        title: "",
        amount: 0,
        currency: "USD",
        category: "FIXED",
        frequency: "DAY"
    };
    const modalTitle = $("#modal-title");
    const modalAmount = $("#modal-amount-currency input");
    const modalCurrency = $("#modal-amount-currency .custom-select");
    const modalCategory = $("#modal-category-frequency .custom-select[name='category']");
    const modalFrequency = $("#modal-category-frequency .custom-select[name='frequency']");
    modalTitle.value = formObject.title;
    modalAmount.value = formObject.amount;
    modalCurrency.children(":first").value = formObject.currency;
    for (const itemField of itemFieldInputs) {
        var nameAttribute = itemField.name.split(".")[1];
        var modalField = $(`#modal input[name=${nameAttribute}]`)[0]  || $(`#modal select[name=${nameAttribute}]`)[0];
        if (typeof modalField !== "undefined" && modalField !== null) {
                modalField.value = itemField.value;
        }
    }
}

function saveFromModalToItem() {
    const itemWrapId = $("#modal").data("wrapId");
    const itemFieldInputs = findInputChildren($(itemWrapId)[0]);
    for (const itemField of itemFieldInputs) {
        var nameAttribute = itemField.name.split(".")[1];
        var modalField = $(`#modal input[name=${nameAttribute}]`)[0]  || $(`#modal select[name=${nameAttribute}]`)[0];
        if (typeof modalField !== "undefined" && modalField !== null) {
            itemField.value = modalField.value;
        }  
    }
}
function findWrapId(event) {
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
function findInputChildren(element) {
    let inputChildren = [];
    let nonInputChildren = [];
    for (const child of element.children) {
        console.log(child.tagName);
        if (child.tagName === "INPUT") {
            inputChildren.push(child);
        } else if (child.children.length > 0) {
            nonInputChildren.push(child)
        }
    }
    for (let i = 0; i < nonInputChildren.length ; i ++) {
        for (const secondChild of nonInputChildren[i].children) {
            if (secondChild.tagName === "INPUT") {
                inputChildren.push(secondChild);
            } else {
                continue;
            }
        }
    }
    console.log(inputChildren);
    return inputChildren;
}

export { showModalToEdit, saveFromModalToItem, findWrapId, findInputChildren };