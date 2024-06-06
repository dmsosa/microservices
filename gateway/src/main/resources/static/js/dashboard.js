function showModalToEdit(id, itemFieldInputs) {
    const modal = $("#modal");
    modal.data("itemFieldId", id);
    for (const itemField of itemFieldInputs) {
        if (itemField.tagName === "INPUT") {
            var nameAttribute = itemField.name.split(".")[1];
            var modalField = $(`#modal input[name=${nameAttribute}]`)[0]  || $(`#modal select[name=${nameAttribute}]`)[0];
            if (typeof modalField !== "undefined" && modalField !== null) {
                    modalField.value = itemField.value;
            }
        }
    }
}

function saveFromModalToItem() {
    const itemFieldId = $("#modal").data("itemFieldId");
    const itemFieldInputs = $("#" + itemFieldId)[0].children;
    for (const itemField of itemFieldInputs) {
        if (itemField.tagName === "INPUT") {
            var nameAttribute = itemField.name.split(".")[1];
            var modalField = $(`#modal input[name=${nameAttribute}]`)[0]  || $(`#modal select[name=${nameAttribute}]`)[0];
            if (typeof modalField !== "undefined" && modalField !== null) {
                itemField.value = modalField.value;
            }  
        }     
    }
}

export { showModalToEdit, saveFromModalToItem };