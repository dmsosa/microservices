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

$(".fliptext").bind("click", function() {
    $("#flipper").toggleClass("flippedcardinfo");
})
$(".flipinfo").on("click", function() {
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
$("#loginname").keydown(function (e) {
        if (e.which == 13) {
            preventDefault();
        }
});
$("#loginname").keyup(function (e) {
    if ($(this).val().length >= 3) {
        $(".firstenter").show();
        if (e.which == 13) {
            flipForm();
        }
        return;
    } else {
        $(".firstenter").hide();
    }
});
$("#loginpassword").keyup(function (e) { 
    if ($(this).val().length >= 6) {
        $("#loginsubmit").show();
        if (e.which == 13) {
            $("#loginsubmit").click();
        }
        return;
    } else {
        $("#loginsubmit").hide();
    }
});

// Bind Animations to Elements

$("#piggy").on("click mouseenter", function () {
    if ($(this).hasClass("hover-shake") === false && $(this).hasClass("auto-shake") === false) {
        OnHoverShaking();
    }    
});

InitialShake();