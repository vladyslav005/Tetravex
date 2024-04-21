window.isCommentsShowed = false;
comments = $("#show-comments-block");
commentBlock = $("#template-comment");
commentBlock.hide();
commentBlockOuter =  $("#show-comments-block-outer").removeClass("dropdown-comments"),

$("#show-comment-header").click(showAllComments);


function hideAllComments() {
    $(".comment-block").remove();
    window.isCommentsShowed = false;

}

function showAllComments() {
    if (window.isCommentsShowed) {
        commentBlockOuter.removeClass("dropdown-comments");
        commentBlockOuter.addClass("pickup-comments");
        hideAllComments();
        return;
    }


    render_comments();

    commentBlockOuter.removeClass("pickup-comments");
    commentBlockOuter.addClass("dropdown-comments")
    // setTimeout(() => commentBlockOuter.removeClass("dropdown-comments"), 500);

}

function render_comments() {
    $.ajax({
        type: "get",
        url: "/api/comment/tetravex",
        contentType: 'application/json; charset=utf-8',

        success: function (result) {
            for (i in result) {
                showComment(result[i]);
            }
            window.isCommentsShowed = true;

        },

        error: function (ex) {
            console.log("error", ex);
            window.isCommentsShowed = false;
            commentBlockOuter.removeClass("dropdown-comments");
            commentBlockOuter.addClass("pickup-comments");
        }
    });
}

function showComment(data) {
    newComment = commentBlock.clone();

    comentedOn = new Date(data.commentedOn);

    dateTime = comentedOn.toDateString() + " "
        + comentedOn.getHours() + ":"
        + (comentedOn.getMinutes() === 0 ? "00" :
            (comentedOn.getMinutes() < 10 ? "0" + comentedOn.getMinutes() : comentedOn.getMinutes()));

    newComment.find(".comment-name").text(data.player);
    newComment.find(".comment-text").text(data.comment);
    newComment.find(".comment-timestamp").text(
        dateTime
    );

    newComment.appendTo(comments);
    newComment.show();
}

