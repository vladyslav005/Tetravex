jwt_token = null;


modal_name = $("#modal-name");
modal_password = $("#modal-password");
modal_submit = $("#modal-submit");
SIGNED_IN = false;

var modal = $("#modal");
modalIsOpened = false
modal.hide();

USERNAME = null;

action = null;


modal_submit.click(modal_submit_click_handler)

function modal_submit_click_handler() {
    let name = modal_name.val();
    let password = modal_password.val();
    if (name.length == 0 &&  password.length == 0) {
        alert("Please enter name and password");
        return;
    }

    if (action == "signin")
        log_in_request(name, password);
    else if (action == "signup") {
        signup_request(name, password)
    }
}


function log_in_request(name, password) {

    if (jwt_token != null) return;

    let data = {username : name, password : password};

    $.ajax({
        type: "post",
        url: "/auth/signin",
        contentType: 'application/json; charset=utf-8',

        data: JSON.stringify(data),

        success: function (result) {
           console.log(result);
           jwt_token = result;
           USERNAME = name;
           SIGNED_IN = true;
           modal.hide();

            modal.hide();
            modal_name.val('');
            modal_password.val('');
        },

        error: function (ex) {
            console.log("error", ex);
            alert("Invalid name or password");
        }
    });

}


function signup_request(name, password) {

    if (jwt_token != null) return;

    let data = {username : name, password : password};

    $.ajax({
        type: "post",
        url: "/auth/signup",
        contentType: 'application/json; charset=utf-8',

        data: JSON.stringify(data),

        success: function (result) {
            console.log(result);
            modal.hide();
        },

        error: function (ex) {
            console.log("error", ex);
            alert("This name is already used");
        }
    });
}



$("#modal-cancel").click(function (event) {
    modal.hide();
    modal_name.val('');
    modal_password.val('');
})


$("#LogIn").click(() => {modal.show(); action="signin";});

$("#SignUp").click(() => {modal.show(); action="signup";});

$("#LogOut").click(() => {
    if (SIGNED_IN) {
    USERNAME = null;
    SIGNED_IN = false;
    alert("Logged out");
    }
})
