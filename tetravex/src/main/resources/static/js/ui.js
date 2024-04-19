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

//TODO : METRIC FOR SCORE