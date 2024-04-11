var selected_tile = null;

$(".tile").click(tileClickHandler);


$(".tile").on("dragover", function(event) {
    event.preventDefault();
    event.stopPropagation();

    let current = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();

    current.addClass("selected");
});

$(".tile").on("dragleave", function(event) {
    event.preventDefault();
    event.stopPropagation();
    let current = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();
    current.removeClass("selected");
});

$(".tile").on("dragstart", function(event) {
    console.log("dragstart");
    if (selected_tile == null)
        selected_tile = $(event.target).hasClass("tile") ? $(event.target) : $(event.target).parent();
});


$(".tile").on("drop", function(event) {
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

        sendRequest(createObjectForRequest(selected_tile, current));
        swapTiles(selected_tile, current)

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
                    $("#message").replaceWith("<p class=\"lead mb-0 animated-message good-message\" id=\"message\">Congratulations, you won!</p>");
                } else if (result[resultKey] !== previousState)
                    $("#message").replaceWith("<p class=\"lead mb-0 just-message\" id=\"message\">Good luck ;)</p>");
                previousState = result[resultKey];
            }
        },
        error: function (ex) {
            console.log("error", ex);
            // setTimeout(() =>sendRequest(dataObj), 100);
        }
    });
}