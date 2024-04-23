const urlParams = new URLSearchParams(window.location.search);

formMessage = $("#form-message");
buttonPlay = $("#button-play");
formMessage.hide();
var isFormValid = false;

var complexity = urlParams.has("complexity") ? urlParams.get("complexity") : "Hard";

width = urlParams.has("width") ? urlParams.get("width") : null;
height = urlParams.has("height") ? urlParams.get("height") : null;


if (width != null && height != null) {
    $("#width").val(width);
    $("#height").val(height);
} else {
    width = 3;
    height = 3;
}


$("#complexity-btn").text(complexity);

var widthFromForm = NaN;
var heightFromForm = NaN;

$(document).ready(function () {
    $(".size-of-field").keyup(function (event) {
        let width = parseInt($("#width").val());
        let height = parseInt($("#height").val());
        heightFromForm = height;
        widthFromForm = width;

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
    let url = "/tetravex/play/parameters" + "?width=" + widthFromForm + "&height=" + heightFromForm + "&complexity=" + complexity;
    window.location.replace(url);
    $("#reload").attr("href", url);
})

$(".complexity").click(function (event) {
    complexity = $(event.target).text();
    $("#complexity-btn").text(complexity);
})