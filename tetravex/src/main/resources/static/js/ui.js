$("#solved").hide();
$("#solved").children().children().off("click");
$("#show-solution").click(show_solution);

function show_solution() {
    $("#solved").toggle();
}