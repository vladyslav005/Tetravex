$("#add-cmnt-submit").click(addCommentBtnClickHandler);
$("#add-comment-form").hide();
$("#add-comment-header").click(() => $("#add-comment-form").toggle());

var playerName;
var comment;

function addCommentBtnClickHandler() {

    if (!SIGNED_IN) {
        alert("Log in to add comments");
        return;
    }
    comment = $("#input-comment").val();

    if (comment.length !== 0) {
        data = {
            player: USERNAME,
            game: "tetravex",
            commentedOn: new Date(),
            comment: comment
        }

        $("#input-player").val(' ')
        $("#input-comment").val(' ');

        $.ajax({
            type: "post",
            url: "/api/comment",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            headers: {
                "Authorization": "Bearer " + JWT_TOKEN
            },

            success: function (result) {
                alert("Comment was added")

                if (window.isCommentsShowed) {
                    hideAllComments();
                    showAllComments();
                }
            },

            error: function (ex) {
                console.log(ex)
            }

        });
    } else {
        alert("Please, enter your name and comment")
    }
}