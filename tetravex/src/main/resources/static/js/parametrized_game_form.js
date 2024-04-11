formMessage = $("#form-message");
buttonPlay = $("#button-play");
formMessage.hide();
isFormValid = false;
width = 0;
height = 0;

$(document).ready(function () {
    $(".size-of-field").keyup(function (event) {
        width = parseInt($("#width").val());
        height = parseInt($("#height").val());

        if (width > 0 && width < 7 && height > 0 && height < 7) {
            formMessage.hide();
            isFormValid = true;

        } else if ($("#width").val().length == 0 && $("#height").val() == 0) {
            formMessage.hide();
            isFormValid = false;
        } else {
            formMessage.show();
            isFormValid = false;

        }
    })
});

buttonPlay.click(function () {
    if (!isFormValid) return;
    let url = "/tetravex/play/parameters" + "?width=" + width + "&height=" + height;
    window.location.replace(url);
    $("#reload").attr("href", url);

})