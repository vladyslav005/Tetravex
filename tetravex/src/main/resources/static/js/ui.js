$("#solved").hide();
$("#solved").children().children().off("click");
$("#show-solution").click(show_solution);

function show_solution() {
    $("#solved").toggle();
    isSolutionShowed = true;
    stopTimer();
    score = 0;
}

function set_message_anim() {
    $("#message").click(function () {
        $('#message').css('animation', 'wiggle 1.5s linear 0ms 1');
        setTimeout(() => $('#message').css('animation', 'none'), 1500);
    });
}

setTimeout(set_message_anim, 1000);

modal_name = $("#modal-name");
modal_password = $("#modal-password");
modal_submit = $("#modal-submit");


var modal = $("#modal");
modalIsOpened = false;
modal.hide();
$("#back").hide();



$("#modal-cancel").click(function (event) {

    modal.removeClass("modal-slide-in");
    modal.addClass("modal-slide-out");
    setTimeout(() => modal.hide(), 500);

    modal_name.val('');
    modal_password.val('');
    $("#back").hide();
});


$("#back").click(function (event) {
    modal.removeClass("modal-slide-in");
    modal.addClass("modal-slide-out");

    setTimeout(() => modal.hide(), 500);
    modal_name.val('');
    modal_password.val('');
    $("#back").hide();
});


$("#LogIn").click(() => {
    modal.removeClass("modal-slide-out");
    modal.addClass("modal-slide-in");

    modal.show();

    action = "signin";
    $("#back").show();
});

$("#SignUp").click(() => {
    modal.removeClass("modal-slide-out");
    modal.addClass("modal-slide-in");

    modal.show();
    action = "signup";
    $("#back").show();
});



//TODO : METRIC FOR SCORE