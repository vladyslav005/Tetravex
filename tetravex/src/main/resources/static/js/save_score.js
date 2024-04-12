function saveScore() {

    if (confirm("Do you want to save your results?\nYour score is " + score)) {
        name = prompt("Enter your name");

        data = {
            player: name,
            game: "tetravex",
            playedOn: new Date(),
            points: score
        };
        if (name.length != 0) {
            $.ajax({
                type: "post",
                url: "/api/score",
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),

                success: function (result) {
                    showAllScores()
                },

                error: function (ex) {
                    console.log("error", ex);
                }
            });
        } else {
            alert("Please enter your name");
            saveScore();
        }
    }
}