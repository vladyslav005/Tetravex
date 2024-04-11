$("#add-cmnt-submit").click(addCommentBtnClickHandler);
$("#add-comment-form").hide();
$("#add-comment-header").click(() => $("#add-comment-form").toggle());

var playerName;
var comment;



function addCommentBtnClickHandler () {

    console.log(new Date());

    playerName = $("#input-player").val();
    comment = $("#input-comment").val();

    if (playerName.length !== 0 && comment.length !== 0) {
        data = {
            player: playerName,
            game: "tetravex",
            commentedOn : new Date(),
            comment: comment
        }


        $.ajax({
            type: "post",
            url: "/api/comment",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),

            success: function (result) {
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