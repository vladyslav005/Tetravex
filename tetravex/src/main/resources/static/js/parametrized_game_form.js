const urlParams = new URLSearchParams(window.location.href);

formMessage = $("#form-message");
buttonPlay = $("#button-play");
formMessage.hide();
var isFormValid = false;

var complexity = urlParams.has("complexity") ? urlParams.get("complexity") : "Hard";
width = 3;
height = 3;

$("#complexity-btn").text(complexity);

$(document).ready(function () {
    $(".size-of-field").keyup(function (event) {
        width = parseInt($("#width").val());
        height = parseInt($("#height").val());

        if (width > 0 && width < 7 && height > 0 && height < 7) {
            formMessage.hide();
            isFormValid = true;

        } else if ($("#width").val().length === 0 && $("#height").val() === 0) {
            formMessage.hide();
            isFormValid = false;
        } else {
            formMessage.show();
            isFormValid = false;

        }
    })
});

buttonPlay.click(function () {
    console.log(complexity);
    if (!isFormValid) return;
    let url = "/tetravex/play/parameters" + "?width=" + width + "&height=" + height + "&complexity=" + complexity;
    window.location.replace(url);
    $("#reload").attr("href", url);
})

$(".complexity").click(function (event) {
    complexity = $(event.target).text();
    $("#complexity-btn").text(complexity);
})