window.isCommentsShowed = false;
comments = $("#show-comments-block");
commentBlock = $("#template-comment");
commentBlock.hide();

$("#show-comment-header").click(showAllComments);


function hideAllComments() {
    $(".comment-block").remove();
    window.isCommentsShowed = false;

}

function showAllComments() {
    if (window.isCommentsShowed) {
        hideAllComments()
        return;
    }

    $.ajax({
        type: "get",
        url: "/api/comment/tetravex",
        contentType: 'application/json; charset=utf-8',

        success: function (result) {
            commentBlock.toggle()
            for (i in result) {
                showComment(result[i]);
            }

            commentBlock.toggle();
        },

        error: function (ex) {
            console.log("error", ex);
        }
    });

    window.isCommentsShowed = true;

}

function showComment(data) {
    newComment = commentBlock.clone();

    comentedOn = new Date(data.commentedOn);

    dateTime = comentedOn.toDateString() + " "
        + comentedOn.getHours() + ":"
        + (comentedOn.getMinutes() === 0 ? "00" : comentedOn.getMinutes())

    newComment.find(".comment-name").text(data.player);
    newComment.find(".comment-text").text(data.comment);
    newComment.find(".comment-timestamp").text(
        dateTime
    );

    newComment.appendTo(comments);
}

