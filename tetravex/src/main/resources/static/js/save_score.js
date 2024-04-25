is_saved = false;

set_handler_for_message()

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
        points: SCORE
    };

    $.ajax({
        type: "post",
        url: "/tetravex/savescore",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        headers: {
            "Authorization": "Bearer " + JWT_TOKEN,
            'X-XSRF-TOKEN': CSRF
        },

        success: function (result, status, xhr) {
            showAllScores();
            is_saved = true;
            update_csrf_token(result, status, xhr)
            // alert("Saved!");
        },

        error: function (ex) {
            console.log("error", ex);
        }
    });
}