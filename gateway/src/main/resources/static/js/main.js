// 📁 main.js
import { show } from './login.js';
import { showModalToEdit, saveFromModalToItem, findWrapId, findInputChildren } from './dashboard.js';



function createCustomSelects() {
    var allSelects = $("select");
    for (let i = 0; i < allSelects.length ; i++) {
    var parent = allSelects[i].parentNode;
    var wrapper = document.createElement("div");
    wrapper.setAttribute("class", "custom-select");
    parent.replaceChild(wrapper, allSelects[i]);
    wrapper.appendChild(allSelects[i]);
    
}
}
//wrapping all selects in custom select divs

function initializeCustomSelects() {
    var customSelects, selectElement, optionsLenght, currentSelectedOption, optionsList;

    customSelects = $(".custom-select");
    for (let i = 0 ; i < customSelects.length ; i++) {
        // create current selected item
        selectElement = customSelects[i].getElementsByTagName("select")[0];
        optionsLenght = selectElement.length;
        currentSelectedOption = document.createElement("div");
        currentSelectedOption.setAttribute("class", "select-selected");
        currentSelectedOption.innerHTML = selectElement.options[selectElement.selectedIndex].innerHTML;
        customSelects[i].appendChild(currentSelectedOption);
        
        optionsList = document.createElement("div");
        optionsList.setAttribute("class", "select-items select-hide");
        for (let j = 0 ; j < optionsLenght ; j++) {
            createOptionElement(optionsList, selectElement, j);
        }
        customSelects[i].appendChild(optionsList);
        currentSelectedOption.addEventListener("click", function(e) {
            e.stopPropagation();
            closeAllSelect(this);
            this.nextSibling.classList.toggle("select-hide");
            this.classList.toggle("select-arrow-active");
        });
    }
}

function createOptionElement(optionsList, selectElement, j) {
    var optionElement = document.createElement("div");
    optionElement.innerHTML = selectElement.options[j].innerHTML;
    optionElement.addEventListener("click", function(e) {
        var parentSelect, currentSelected, sameAsSelected;
        parentSelect = this.parentNode.parentNode.getElementsByTagName("select")[0];
        currentSelected = this.parentNode.previousSibling;
        for (let i = 0; i < parentSelect.length ; i++) {
            if (parentSelect.options[i].innerHTML === this.innerHTML) {
                parentSelect.selectedIndex = i;
                currentSelected.innerHTML = this.innerHTML;
                sameAsSelected = this.parentNode.getElementsByClassName("same-as-selected");
                for ( let j = 0; j < sameAsSelected.length ; j++ ) {
                    sameAsSelected[j].classList.remove("same-as-selected");
                }
                this.classList.add("same-as-selected");
                break;
            }
        }
        currentSelected.click();
    });
    optionsList.appendChild(optionElement);
}
// Custom select inputs


function closeAllSelect(currentSelected) {
    var allOptionsLists, allSelects, closeCurrentList = [];
    allOptionsLists = $(".select-items");
    allSelects = $(".select-selected");
    for (let i = 0; i < allSelects.length ; i ++) {
        if (currentSelected == allSelects[i]) {
            closeCurrentList.push(i);
        } else {
            allSelects[i].classList.remove("select-arrow-active");
        }
    }
    for ( let i = 0; i < allOptionsLists.length ; i ++) {
        if (closeCurrentList.indexOf(i)) {
            allOptionsLists[i].classList.add("select-hide");
        }
    }
}

$(document).ready(function() {
    createCustomSelects();
    initializeCustomSelects();
    document.addEventListener("click", closeAllSelect);
    $(".expensewrap, .incomewrap").on("click", function(e) {
        const id = findWrapId(e);
        const itemWrapWithInputs = $("#" + id)[0];
        const inputChildren = findInputChildren(itemWrapWithInputs);
        showModalToEdit(id, inputChildren);
        $("#modalsavebutton")[0].innerHTML = id;
    })
    $("#modalsavebutton").on("click", saveFromModalToItem)
});
