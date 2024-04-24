form = $("#add-comment-form");
form_outer = $("#add-comment-form-outer");

$("#add-cmnt-submit").click(addCommentBtnClickHandler);
form.hide();

is_form_showed = false


$("#add-comment-header").click(() => {

    if (is_form_showed) {
        form_outer.removeClass("dropdown-comment-form");
        form_outer.addClass("pickup-comment-form");
    } else {
        form_outer.removeClass("pickup-comment-form");
        form_outer.addClass("dropdown-comment-form");
    }

    form.toggle()
    is_form_showed = !is_form_showed;


});

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
                "Authorization": "Bearer " + JWT_TOKEN,
                'X-XSRF-TOKEN': CSRF
            },

            success: function (result, status, xhr) {
                if (window.isCommentsShowed) {
                    $(".comment-block").remove();
                    render_comments();
                }

                update_csrf_token(result, status, xhr)
            },

            error: function (ex) {
                console.log(ex)
            }

        });
    } else {
        alert("Please, enter your name and comment")
    }
}