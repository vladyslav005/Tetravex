//init vars
TOKEN_LIFETIME = 300000;
// TOKEN_LIFETIME = 3000;

TOKEN_LONG_LIFETIME = 604800000;
COOKIE_TOKEN = "token";
COOKIE_NAME = "username";
COOKIE_PASSWORD = "PASSWORD";
COOKIE_STAY_LOGGED_IN = "stay_logged_id";
PASSWORD = null;
SIGNED_IN = false;
RENEW_TOKEN = null;
USERNAME = null;
action = null;
JWT_TOKEN = null;
STAY_LOGGED_IN = false;


modal_submit.click(modal_submit_click_handler)
logged_in_user_txt = $("#logged-in-user");

CSRF = $("meta[name='_csrf']").attr("content");

function update_csrf_token(result, status, xhr) {
    CSRF = xhr.getResponseHeader('X-XSRF-TOKEN');
}

check_if_logged_in();
update_logged_usr_label();

function check_if_logged_in() {
    if (Cookies.get(COOKIE_TOKEN) != null) {
        SIGNED_IN = true;
        JWT_TOKEN = Cookies.get(COOKIE_TOKEN);
        USERNAME = Cookies.get(COOKIE_NAME);
        PASSWORD = Cookies.get(COOKIE_PASSWORD);

        update_logged_usr_label();
        RENEW_TOKEN = renew_token(USERNAME, PASSWORD);

        if (Cookies.get(COOKIE_STAY_LOGGED_IN) != null) {
            STAY_LOGGED_IN = true;
        } else {
            STAY_LOGGED_IN = false;
        }
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

s = null

function log_in_request(name, password) {
    let data = {username: name, password: password, longToken: false};

    $.ajax({
        type: "post",
        url: "/auth/signin",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        headers: {'X-XSRF-TOKEN': CSRF},


        success: function (result, status, xhr) {
            RENEW_TOKEN = setTimeout(renew_token, TOKEN_LIFETIME - 500, name, password);
            JWT_TOKEN = result;
            USERNAME = name;
            PASSWORD = password
            SIGNED_IN = true;
            modal.hide();
            modal_name.val('');
            modal_password.val('');
            $("#back").hide();

            update_logged_usr_label()
            getPlayersRating();

            modal.removeClass("modal-slide-in");
            modal.addClass("modal-slide-out");
            setTimeout(() => modal.hide(), 500);

            if (confirm("Do you want to stay logged in?")) {
                STAY_LOGGED_IN = true;
                Cookies.set(COOKIE_STAY_LOGGED_IN, STAY_LOGGED_IN, {expires: 7})

                Cookies.set(COOKIE_NAME, USERNAME, {expires: 0.003472})
                Cookies.set(COOKIE_TOKEN, JWT_TOKEN, {expires: 0.003472});
                Cookies.set(COOKIE_PASSWORD, PASSWORD, {expires: 0.003472});
            } else {
                Cookies.set(COOKIE_NAME, USERNAME, {expires: 0.003472})
                Cookies.set(COOKIE_TOKEN, JWT_TOKEN, {expires: 0.003472});
                Cookies.set(COOKIE_PASSWORD, PASSWORD, {expires: 0.003472});

            }

            update_csrf_token(result, status, xhr)
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
        headers: {'X-XSRF-TOKEN': CSRF},

        success: function (result, status, xhr) {
            RENEW_TOKEN = setTimeout(renew_token, TOKEN_LIFETIME - 500, name, password);
            JWT_TOKEN = result;
            USERNAME = name;
            SIGNED_IN = true;
            modal.hide();
            modal_name.val('');
            modal_password.val('');
            $("#back").hide();
            update_logged_usr_label()

            Cookies.set(COOKIE_NAME, USERNAME, {expires: 0.003472})
            Cookies.set(COOKIE_TOKEN, JWT_TOKEN, {expires: 0.003472});
            Cookies.set(COOKIE_PASSWORD, PASSWORD, {expires: 0.003472});

            update_csrf_token(result, status, xhr)
        },

        error: function (ex) {
            console.log("error", ex);
            clearTimeout(RENEW_TOKEN);
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
        headers: {'X-XSRF-TOKEN': CSRF},

        success: function (result, status, xhr) {
            modal.hide();

            modal_name.val('');
            modal_password.val('');

            $("#back").hide();
            alert("Account created successfully")

            update_csrf_token(result, status, xhr)
        },

        error: function (ex) {
            console.log("error", ex);
            alert("Some error occurred, maybe this name is already taken or too short");
        }
    });
}


$("#LogOut").click(() => {
    if (SIGNED_IN) {


        Cookies.remove(COOKIE_TOKEN);
        Cookies.remove(COOKIE_NAME);
        Cookies.remove(COOKIE_PASSWORD);
        Cookies.remove(COOKIE_STAY_LOGGED_IN);

        JWT_TOKEN = null;
        USERNAME = null;
        PASSWORD = null;
        SIGNED_IN = false;
        STAY_LOGGED_IN = false;
        clearTimeout(RENEW_TOKEN);

        update_logged_usr_label();
        getPlayersRating();
    }
})


window.onbeforeunload = function () {

    if (STAY_LOGGED_IN) {
        // stay_logged_in(USERNAME, PASSWORD);
        Cookies.set(COOKIE_STAY_LOGGED_IN, "true", {expires: 7});
        Cookies.set(COOKIE_TOKEN, JWT_TOKEN, {expires: 7});
        Cookies.set(COOKIE_NAME, USERNAME, {expires: 7});
        Cookies.set(COOKIE_PASSWORD, PASSWORD, {expires: 7});
    }

    return null;
};

