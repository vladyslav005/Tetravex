scoreTemplate = $("#score-template");
table = scoreTemplate.parent();

scoreTemplate.hide();

showAllScores();

function showAllScores() {
    $(".score-row:not(#score-template)").remove();

    $.ajax({
        type: "get",
        url: "/api/score/tetravex",
        contentType: 'application/json; charset=utf-8',

        success: function (result, status, xhr) {
            scoreTemplate.show();
            for (i in result) {
                showPlayerScore(result[i], parseInt(i) + 1);
            }
            scoreTemplate.hide();
            update_csrf_token(result, status, xhr)

        },

        error: function (ex) {
            console.log("error", ex);
        }
    });
}

function showPlayerScore(data, i) {
    newScore = scoreTemplate.clone();
    newScore.appendTo(table);
    scoreTemplate.before(newScore);

    newScore.removeAttr("id");
    newScore.find(".score-n>p").text(i);
    newScore.find(".score-player>p").text(data.player);
    newScore.find(".score-score>p").text(data.points);

    playedOn = new Date(data.playedOn);
    dateTime = playedOn.toDateString() + " "
        + playedOn.getHours() + ":"
        + (playedOn.getMinutes() === 0 ? "00" :
            (playedOn.getMinutes() < 10 ? "0" + playedOn.getMinutes() : playedOn.getMinutes()));

    newScore.find(".score-date>p").text(dateTime);
}