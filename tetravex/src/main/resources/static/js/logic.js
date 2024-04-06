var selected_tile = null;

$(".tile").click(tileClickHandler);


function tileClickHandler(event) {
    let current = $(event.target).parent();

    if (selected_tile != null) {
        sendRequest(createObjectForRequest(selected_tile, current));
        swap_tiles(selected_tile, current)

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
            board : current_board.attr("id"),
            y : current.attr("data-y"),
            x : current.attr("data-x")
        },
        {
            board : selected_board.attr("id"),
            y : selected.attr("data-y"),
            x : selected.attr("data-x")
        }
    ];
}

function swap_tiles(tile_1, tile_2) {
    tile_1 = $(tile_1); tile_2 = $(tile_2);

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



function sendRequest (dataObj) {
    console.log("Sending request : ", dataObj)

    $.ajax({
        type: "post",
        url: "/tetravex/handler",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(dataObj),
        success: function (result) {
            for (const resultKey in result) {
                if (result[resultKey] === "SOLVED") {
                    $("#message").replaceWith("<p class=\"lead mb-0 animated-message good-message\" id=\"message\">Congratulations, you won!</p>");
                } else $("#message").replaceWith("<p class=\"lead mb-0 just-message\" id=\"message\">Good luck ;)</p>");
            }
        },
        error: function (ex) {
            console.log("error", ex);
        }
    });
}

