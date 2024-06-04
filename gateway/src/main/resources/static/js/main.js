// 📁 main.js
import { show } from './login.js';

$(document).ready(function() {
    show();
    function toggleInfo() {
        $("#infopage").toggle();
    }
    $("#flipper").toggleClass("flippedcardinfo");
});

