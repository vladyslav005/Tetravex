$(".star").mouseenter(onMouseEnter);

PLAYERS_RATING = 0;
getPlayersRating();

function setStarsOnRating(result) {
    for (let i = 1; i < 6; i++) {
        $("#" + i).css("color", "black");

    }
    for (let j = 1; j <= result; j++) {
        $("#" + j).css("color", "#ffae00");
    }
}

function getPlayersRating() {
    if (SIGNED_IN) {
        $.ajax({
            async: false,
            type: "get",
            url: "/api/rating/tetravex/" + USERNAME,
            headers: {
                "Authorization": "Bearer " + JWT_TOKEN
            },

            success: function (result) {
                PLAYERS_RATING = result;
            },

            error: function (ex) {
            }
        });
    } else PLAYERS_RATING = 0;


    setStarsOnRating(PLAYERS_RATING);

}


function onMouseEnter(event) {
    let current = $(event.target).hasClass("star") ? $(event.target) : $(event.target).parent();
    i = parseInt($(current).attr("id"));
    for (let j = i; j > 0; j--) {
        $("#" + j).css("color", "#ffae00");
    }
}

$(".star").click(function (event) {
    let current = $(event.target).hasClass("star") ? $(event.target) : $(event.target).parent();
    i = parseInt($(current).attr("id"));

    if (!SIGNED_IN) {
        alert("Log in to rate game");
    } else {
        data = {
            player: USERNAME,
            game: "tetravex",
            ratedOn: new Date(),
            rating: i
        }

        $.ajax({
            type: "post",
            url: "/api/rating",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            headers: {
                "Authorization": "Bearer " + JWT_TOKEN
            },

            success: function (result) {
                getAvgRate();
                PLAYERS_RATING = i;
                setStarsOnRating(i);

            },

            error: function (ex) {
            }
        });
    }
});

$(".star").mouseleave(() => {
    $(".star").css("color", "black");
}
);

$(".rate-field").mouseleave(() => setStarsOnRating(PLAYERS_RATING));

function getAvgRate() {
    $.ajax({
        type: "get",
        url: "/api/rating/tetravex",
        contentType: 'application/json; charset=utf-8',
        success: function (result) {

            $("#avg-rate").text(" " + result);
        },

        error: function (ex) {
        }
    });
}

getAvgRate();