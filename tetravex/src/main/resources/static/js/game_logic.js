var selected_tile = null;
var isFirstMove = true;

var startTimestamp;
var timer = $("#timer");
var setIntervalId;
var score = 0;
var isSolutionShowed = false;
isSolved = false;

function stopTimer() {
    clearInterval(setIntervalId);
}

function resetTimer() {
    startTimestamp = new Date().getTime();
    setIntervalId = setInterval(timerHandler, 1000);
}


function timerHandler() {
    let currentTime = Math.round((new Date().getTime() - startTimestamp) / 1000);
    score = currentTime;
    let seconds = currentTime % 60;
    let minutes = (currentTime - seconds) / 60;
    timer.text((minutes < 10 ? "0" + minutes : minutes)
        + ":" + (seconds < 10 ? "0" + seconds : seconds));
}


$(".tile").click(tileClickHandler);

$(".tile").on("dragover", function (event) {
    event.preventDefault();
    event.stopPropagation();

    let current = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();

    current.addClass("selected");
});

$(".tile").on("dragleave", function (event) {
    event.preventDefault();
    event.stopPropagation();
    let current = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();
    current.removeClass("selected");
});

$(".tile").on("dragstart", function (event) {
    if (selected_tile == null)
        selected_tile = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();
});


$(".tile").on("drop", function (event) {
    event.preventDefault();
    event.stopPropagation();
    let current = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();
    current.removeClass("selected");
    selected_tile.removeClass("selected");

    swapTiles(selected_tile, current);
    sendRequest(createObjectForRequest(selected_tile, current));
    selected_tile = null;

});


function tileClickHandler(event) {
    let current = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();
    current.addClass("selected");

    if (selected_tile != null) {
        // console.log(selected_tile, current);
        current.removeClass("selected");
        selected_tile.removeClass("selected");

        swapTiles(selected_tile, current)
        sendRequest(createObjectForRequest(selected_tile, current));

        selected_tile = null;
        return;
    }

    selected_tile = current;
}

function createObjectForRequest(selected, current) {
    let current_board = current.parent().parent();
    let selected_board = selected.parent().parent();

    return [
        {
            board: current_board.attr("id"),
            y: current.attr("data-y"),
            x: current.attr("data-x")
        },
        {
            board: selected_board.attr("id"),
            y: selected.attr("data-y"),
            x: selected.attr("data-x")
        }
    ];
}

function swapTiles(tile_1, tile_2) {
    if (isFirstMove && !isSolutionShowed) {
        resetTimer();
        isFirstMove = false;
    }

    tile_1.css("animation-direction", "reverse");
    tile_2.css("animation-direction", "reverse");

    setTimeout(function () {
        tile_1.css("animation-direction", "normal");
        tile_2.css("animation-direction", "normal");
    }, 50);

    let tmp = $('<span>').hide();

    tile_1.before(tmp);
    tile_2.before(tile_1);
    tmp.replaceWith(tile_2);

    let elm_1_x = tile_1.attr("data-x");
    let elm_1_y = tile_1.attr("data-y");

    tile_1.attr("data-x", tile_2.attr("data-x"));
    tile_1.attr("data-y", tile_2.attr("data-y"));
    tile_2.attr("data-x", elm_1_x);
    tile_2.attr("data-y", elm_1_y);
}

var previousState = "PLAYING";

function sendRequest(dataObj) {
    // console.log("Sending request : ", dataObj)

    $.ajax({
        type: "post",
        url: "/tetravex/handler",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(dataObj),
        success: function (result) {
            for (const resultKey in result) {
                if (result[resultKey] === "SOLVED" && result[resultKey] !== previousState) {
                    $("#message").replaceWith("<p class=\"lead mb-0 animated-message good-message\" id=\"message\">Congratulations, you won! Click to save your results</p>");
                    set_handler_for_message();
                    stopTimer();
                    if (!isSolutionShowed) isSolved = true;
                    setTimeout(set_message_anim, 1000);

                } else if (result[resultKey] !== previousState) {
                    $("#message").replaceWith("<p class=\"lead mb-0 just-message\" id=\"message\">Good luck ;)</p>");
                    setTimeout(set_message_anim, 1000);
                }
                previousState = result[resultKey];
            }
        },

        error: function (ex) {
            console.log("error", ex);
        }
    });
}