// Animations
function InitialShake() {
    autoShake();
    setTimeout(autoShake, 1700)
}

function autoShake() {
    $("#piggy").toggleClass("auto-shake");
}

function OnHoverShaking() {
	hoverShake();
	setTimeout(hoverShake, 1700);
}

function hoverShake() {
	$("#piggy").toggleClass("hover-shake");
}

function flipForm() {
    $("#loginform").toggleClass("flippedform");
    $("#loginpassword").focus();
}

function toggleInfo() {
    $("#infopage").toggle();
}

function stopPropagation(e) {
    if (e.stopPropagation !== undefined) {
        e.stopPropagation();
    }
}
// Bind Functions to Elements

$("#loginform").on("keydown", function(e) {
    if (e.key == 'Enter') {
        e.preventDefault();
    }
});

$(".fliptext").bind("click", function() {
    $("#flipper").toggleClass("flippedcardinfo");
})
$(".flipinfo").on("click", function() {
	$("#flipper").toggleClass("flippedcardinfo");
	toggleInfo();
});
$(".flipinfotext").on("click", function() {
	$("#flipper").toggleClass("flippedcardinfo");
	toggleInfo();
});

$(".frominfo, #infotitle, #infosubtitle").on("click", function() {
	$("#flipper").toggleClass("flippedcardinfo");
	setTimeout(toggleInfo, 400);
});


$(".firstenter").on("click", function () {
    flipForm();
});

$("#loginname").keyup(function (e) {
    if ($(this).val().length >= 3) {
        $(".firstenter").show();
        if (e.code == 'Enter') {
            flipForm();
        }
        return;
    } else {
        $(".firstenter").hide();
    }
});
$(".backpassword").on("click", function() {
    flipForm();
});
$("#loginpassword").keyup(function (e) { 
    if ($(this).val().length >= 6) {
        $(".loginsubmit").show();
        if (e.key == 'Enter') {
            $(".loginsubmit").click();
        }
        return;
    } else {
        $(".loginsubmit").hide();
    }
});

// Bind Animations to Elements

$("#piggy").on("click mouseenter", function () {
    if ($(this).hasClass("hover-shake") === false && $(this).hasClass("auto-shake") === false) {
        OnHoverShaking();
    }    
});

$(document).ready(function () {
    InitialShake();
});
