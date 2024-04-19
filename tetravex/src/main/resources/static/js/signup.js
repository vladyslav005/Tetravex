

jwt_token = null;

// TODO : show logged in user
// TODO : create fragment

TOKEN_LIFETIME = 300000;
TOKEN_LONG_LIFETIME = 604800000;

COOKIE_TOKEN = "token";
COOKIE_NAME = "username";

RENEW_TOKEN = null;

modal_name = $("#modal-name");
modal_password = $("#modal-password");
modal_submit = $("#modal-submit");
SIGNED_IN = false;

var modal = $("#modal");
modalIsOpened = false
modal.hide();
$("#back").hide();

USERNAME = null;

action = null;


modal_submit.click(modal_submit_click_handler)

logged_in_user_txt = $("#logged-in-user");


check_if_logged_in();

function check_if_logged_in() {
    if (Cookies.get(COOKIE_TOKEN) != null) {
        SIGNED_IN = true;
        jwt_token = Cookies.get(COOKIE_TOKEN);
        USERNAME = Cookies.get(COOKIE_NAME);

        update_logged_usr_txt();


    }

}

function update_logged_usr_txt() {

    if (SIGNED_IN) {
        logged_in_user_txt.css("background-color", "lightgreen")
        logged_in_user_txt.text("Logged in as: " + USERNAME);
    }   else {
        logged_in_user_txt.css("background-color", "orange")
        logged_in_user_txt.text("Logged in as: " + "guest");

    }
}


function modal_submit_click_handler() {

    if (jwt_token != null) return;

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
    console.log("logging in")
    let data = {username : name, password : password,  longToken: false};

    $.ajax({
        type: "post",
        url: "/auth/signin",
        contentType: 'application/json; charset=utf-8',

        data: JSON.stringify(data),

        success: function (result) {

            RENEW_TOKEN =  setTimeout(log_in_request, TOKEN_LIFETIME, name, password);
            console.log(result);
            jwt_token = result;
            USERNAME = name;
            SIGNED_IN = true;
            modal.hide();
            modal_name.val('');
            modal_password.val('');
            $("#back").hide();
            update_logged_usr_txt()
            if (confirm("Do you want to stay logged in?")) stay_logged_in(name, password);
        },

        error: function (ex) {
            console.log("error", ex);
            alert("Invalid name or password");
            clearTimeout(RENEW_TOKEN);
        }
    });

}

function stay_logged_in(name, password) {
    let data = {username : name, password : password,  longToken: true};

    $.ajax({
        type: "post",
        url: "/auth/signin",
        contentType: 'application/json; charset=utf-8',

        data: JSON.stringify(data),

        success: function (result) {
            console.log(result, "GOT long token");
            jwt_token = result;
            USERNAME = name;
            SIGNED_IN = true;
            modal.hide();
            modal_name.val('');
            modal_password.val('');
            $("#back").hide();
            clearTimeout(RENEW_TOKEN);


            Cookies.set(COOKIE_TOKEN, jwt_token, { expires: 7 });
            Cookies.set(COOKIE_NAME, USERNAME, { expires: 7 });

        },

        error: function (ex) {
            console.log("error", ex);
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
            $("#back").hide();

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
    $("#back").hide();

});

$("#back").click(function (event) {
    modal.hide();
    modal_name.val('');
    modal_password.val('');
    $("#back").hide();

});

$("#LogIn").click(() => {
    modal.show();
    action="signin";
    $("#back").show();
});

$("#SignUp").click(() => {
    modal.show(); action="signup";
    $("#back").show();

});

$("#LogOut").click(() => {
    if (SIGNED_IN) {
        jwt_token = null;
        USERNAME = null;
        PASSWORD = null;
        SIGNED_IN = false;
        alert("Logged out");
        clearTimeout(RENEW_TOKEN);
        Cookies.remove(COOKIE_TOKEN);
        Cookies.remove(COOKIE_NAME);
        update_logged_usr_txt();
    }
})
