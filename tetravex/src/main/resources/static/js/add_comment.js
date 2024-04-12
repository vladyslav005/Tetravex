$("#add-cmnt-submit").click(addCommentBtnClickHandler);
$("#add-comment-form").hide();
$("#add-comment-header").click(() => $("#add-comment-form").toggle());

var playerName;
var comment;

function addCommentBtnClickHandler () {

    playerName = $("#input-player").val();
    comment = $("#input-comment").val();

    if (playerName.length !== 0 && comment.length !== 0) {
        data = {
            player: playerName,
            game: "tetravex",
            commentedOn : new Date(),
            comment: comment
        }

        $("#input-player").val(' ')
        $("#input-comment").val(' ');

        $.ajax({
            type: "post",
            url: "/api/comment",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),

            success: function (result) {
                alert("Comment was added")

                if (window.isCommentsShowed) {
                    hideAllComments();
                    showAllComments();
                }
            },

            error : function (ex) {

            }

        });
    } else {
        alert("Please, enter your name and comment")
    }
}