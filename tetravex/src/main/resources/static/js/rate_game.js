$(".star").mouseenter(onMouseEnter);

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
        alert("Sign in to rate game");
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
                "Authorization": "Bearer " + jwt_token
            },

            success: function (result) {
                getAvgRate();
                alert("Rating was added");
            },

            error: function (ex) {
            }
        });
    }
});

$(".star").mouseleave(() => $(".star").css("color", "black"));

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