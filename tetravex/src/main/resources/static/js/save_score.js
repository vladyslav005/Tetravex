
is_saved = false;

function set_handler_for_message() {
    $("#message").click(function () {
        if (isSolved && !is_saved) {
            saveScore();
        } else {

        }
    })
}

function saveScore() {
    if (!SIGNED_IN) {
        alert("Sign in first");
        return;
    }


    data = {
        player: USERNAME,
        game: "tetravex",
        playedOn: new Date(),
        points: score
    };

    $.ajax({
        type: "post",
        url: "/api/score",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        headers: {
            "Authorization": "Bearer " + jwt_token
        },

        success: function (result) {
            showAllScores();
            is_saved = true;
            alert("Saved!");
        },

        error: function (ex) {
            console.log("error", ex);
        }
    });
}