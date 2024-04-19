// TODO : show logged in user
// TODO : create fragment

//init vars
TOKEN_LIFETIME = 300000;
TOKEN_LONG_LIFETIME = 604800000;
COOKIE_TOKEN = "token";
COOKIE_NAME = "username";
SIGNED_IN = false;
RENEW_TOKEN = null;
USERNAME = null;
action = null;
JWT_TOKEN = null;



modal_submit.click(modal_submit_click_handler)
logged_in_user_txt = $("#logged-in-user");


check_if_logged_in();
update_logged_usr_label();

function check_if_logged_in() {
    if (Cookies.get(COOKIE_TOKEN) != null) {
        SIGNED_IN = true;
        JWT_TOKEN = Cookies.get(COOKIE_TOKEN);
        USERNAME = Cookies.get(COOKIE_NAME);

        update_logged_usr_label();
    }
}

function update_logged_usr_label() {
    if (SIGNED_IN) {
        logged_in_user_txt.css("background-color", "#4DB94DFF")
        logged_in_user_txt.text("Logged in as: " + USERNAME);
        $("#LogOut").show();
        $("#SignUp").hide();
        $("#LogIn").hide();
    } else {
        logged_in_user_txt.css("background-color", "#ff9900")
        logged_in_user_txt.text("Logged in as: " + "guest");
        $("#LogOut").hide();
        $("#SignUp").show();
        $("#LogIn").show();

    }
}


function modal_submit_click_handler() {
    if (JWT_TOKEN != null) return;

    let name = modal_name.val();
    let password = modal_password.val();
    if (name.length == 0 && password.length == 0) {
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
    let data = {username: name, password: password, longToken: false};

    $.ajax({
        type: "post",
        url: "/auth/signin",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),

        success: function (result) {
            RENEW_TOKEN = setTimeout(renew_token, TOKEN_LIFETIME, name, password);
            JWT_TOKEN = result;
            USERNAME = name;
            SIGNED_IN = true;
            modal.hide();
            modal_name.val('');
            modal_password.val('');
            $("#back").hide();
            update_logged_usr_label()

            if (confirm("Do you want to stay logged in?")) stay_logged_in(name, password);
            else {
                Cookies.set(COOKIE_NAME, USERNAME, {expires: 0.003472 })
                Cookies.set(COOKIE_TOKEN, JWT_TOKEN, {expires: 0.003472 });
            }


        },

        error: function (ex) {
            console.log("error", ex);
            alert("Invalid name or password");
            clearTimeout(RENEW_TOKEN);
        }
    });
}


function renew_token(name, password) {

    let data = {username: name, password: password, longToken: false};

    $.ajax({
        type: "post",
        url: "/auth/signin",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),

        success: function (result) {
            RENEW_TOKEN = setTimeout(renew_token, TOKEN_LIFETIME, name, password);
            JWT_TOKEN = result;
            USERNAME = name;
            SIGNED_IN = true;
            modal.hide();
            modal_name.val('');
            modal_password.val('');
            $("#back").hide();
            update_logged_usr_label()

            Cookies.set(COOKIE_NAME, USERNAME, {expires: 0.003472 })
            Cookies.set(COOKIE_TOKEN, JWT_TOKEN, {expires: 0.003472 });
        },

        error: function (ex) {
            console.log("error", ex);
            clearTimeout(RENEW_TOKEN);
        }
    });


}

function stay_logged_in(name, password) {
    let data = {username: name, password: password, longToken: true};

    $.ajax({
        type: "post",
        url: "/auth/signin",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),

        success: function (result) {
            console.log(result, "GOT long token");
            JWT_TOKEN = result;
            USERNAME = name;
            SIGNED_IN = true;
            modal.hide();
            modal_name.val('');
            modal_password.val('');
            $("#back").hide();
            clearTimeout(RENEW_TOKEN);

            Cookies.set(COOKIE_TOKEN, JWT_TOKEN, {expires: 7});
            Cookies.set(COOKIE_NAME, USERNAME, {expires: 7});
        },

        error: function (ex) {
            console.log("error", ex);
        }
    });
}

function signup_request(name, password) {

    if (JWT_TOKEN != null) return;

    let data = {username: name, password: password};

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
            alert("This name is already used or too short");
        }
    });
}

$("#LogOut").click(() => {
    if (SIGNED_IN) {
        JWT_TOKEN = null;
        USERNAME = null;
        PASSWORD = null;
        SIGNED_IN = false;
        clearTimeout(RENEW_TOKEN);
        Cookies.remove(COOKIE_TOKEN);
        Cookies.remove(COOKIE_NAME);
        update_logged_usr_label();

    }
})

