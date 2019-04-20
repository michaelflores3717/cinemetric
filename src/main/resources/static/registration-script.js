(function() {

    let usernameIsValid = false;
    let emailIsValid = false;
    let passwordIsValid = false;
    let passwordsMatch = false;
    let usernameField = document.querySelector("#username");
    let userWarning = document.querySelector("#user-warning");
    let emailField = document.querySelector("#email");
    let emailWarning = document.querySelector("#email-warning");
    let passwordField = document.querySelector("#password");
    let passwordWarning = document.querySelector("#password-warning");
    let passwordFieldChecker = document.querySelector("#password-checker");
    let passwordCheckerWarning = document.querySelector("#password-checker-warning");

    usernameField.addEventListener('keyup', checkIfUserExistsInDatabase);
    emailField.addEventListener('keyup', checkIfEmailExistsInDatabase);
    passwordField.addEventListener('keyup', declarePasswordValidIfValid);
    passwordField.addEventListener('keyup', declarePasswordsMatchIfMatch);
    passwordFieldChecker.addEventListener('keyup', declarePasswordsMatchIfMatch);

    usernameField.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);
    emailField.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);
    passwordField.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);
    passwordFieldChecker.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);



    function checkIfUserExistsInDatabase() {
        fetch("register/" + usernameField.value).then(response => {
            response.json().then(data => {
                declareUsernameAsValidIfDoesNotExistInDB(data);
            })
        })
    }

    function checkIfEmailExistsInDatabase() {
        fetch("register/email/" + emailField.value).then(response => {
            response.json().then(data => {
                declareEmailAsValidIfDoesNotExistInDB(data);
            })
        })
    }

    function declareUsernameAsValidIfDoesNotExistInDB(data) {
        if (usernameField.value.length < 6 || usernameField.value.length > 20) {
            userWarning.innerHTML = "<p>Username must be between 6 and 20 characters.</p>";
            usernameIsValid = false;
        } else if (data) {
            userWarning.innerHTML = "<p>Username already exists!</p>"
            usernameIsValid = false;
        } else if (!data) {
            userWarning.innerHTML = "<p>Username is looking good</p>";
            usernameIsValid = true;
        }
    }

    function declareEmailAsValidIfDoesNotExistInDB(data) {
        if (!validateEmail(emailField.value)) {
            emailWarning.innerHTML = "<p>Email needs to be in correct format.</p>";
            emailIsValid = false;
        } else if (data) {
            console.log(data);
            emailWarning.innerHTML = "<p>Email already exists</p>";
            emailIsValid = false;
        } else if (validateEmail(emailField.value) && !data) {
            emailWarning.innerHTML = "<p>Email looks a-okay</p>";
            emailIsValid = true;
        }

    }

    function declarePasswordValidIfValid() {
        if (passwordField.value.length < 6 || passwordField.value.length > 20) {
            passwordWarning.innerHTML = "<p>Password needs to be between 6 and 20 characters</p>"
            passwordIsValid = false;
        } else if (passwordField.value.length >= 6 && passwordField.value.length < 20) {
            passwordWarning.innerHTML = "<p>Password length looks about right</p>"
            passwordIsValid = true;
        }

    }

    function declarePasswordsMatchIfMatch() {
        if (passwordField.value === passwordFieldChecker.value) {
            passwordCheckerWarning.innerHTML = "<p>Passwords match!</p>";
            passwordsMatch = true;
        } else if (passwordField.value !== passwordFieldChecker.value) {
            passwordCheckerWarning.innerHTML = "<p>Passwords don't match</p>";
            passwordsMatch = false;
        }

    }

    function allowUserToSubmitIfAllChecksAreMet() {
        console.log("passwords match" + passwordsMatch);
        console.log("password is valid" + passwordIsValid);
        console.log("user is valid" + usernameIsValid);
        console.log("email is valid" + emailIsValid);
        if (usernameIsValid && emailIsValid && passwordIsValid && passwordsMatch) {
            document.querySelector("#submit").disabled = false;
        } else document.querySelector("#submit").disabled = true;
    }

    function validateEmail(email) {
        let re = /\S+@\S+\.\S+/;
        return re.test(email);
    }


})();