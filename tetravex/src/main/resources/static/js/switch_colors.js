switchColorsButton = $("#switch-colors");
link = $("#tile-color-link");

filePath = "/css/themes/tile_colors_";


if (localStorage.getItem("color-scheme") == null) {
    colorScheme = "palette";
} else
    colorScheme = localStorage.getItem("color-scheme");

switchTheme();


function switchTheme() {
    link.attr("href", filePath + colorScheme  + ".css");
}

$(".tile-color-scheme").click(function (event) {
    colorScheme = $(event.target).text().toLowerCase();

    console.log(colorScheme);


    localStorage.setItem("color-scheme", colorScheme);
    switchTheme()


})
