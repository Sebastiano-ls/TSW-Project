function validateEmail(value) {
    return /^\S+@\S+\.\S+$/.test(value);
}

function validatePhone(value) {
    if (value === "") 
        return true;

    return /^\d{10}$/.test(value.replace(/[\s\+\(\)\-]/g, ""));
}

function validateNotEmpty(value) {
    return value != null && value.trim() !== "";
}

function showError(input, message) {
    let parent = input.parentElement;
    let err = parent.querySelector(".error-message");
    if (!err) {
        err = document.createElement("span");
        err.className = "error-message";
        parent.appendChild(err);
    }

    err.innerHTML = message;
    input.className = input.className + " input-error";
    input.className = input.className.replace("input-valid", "");
}

function clearError(input) {
    input.className = input.className.replace("input-error", "");
    input.className = input.className + " input-valid";
    let err = input.parentElement.querySelector(".error-message");
    if (err) 
        err.innerHTML = "";
}

function validateOnChange(input, validator, message) {
    let value = input.value;

    if (!validateNotEmpty(value)) {
        clearError(input);
        return true;
    }

    if (validator(value)) {
        clearError(input);
        return true;
    } else {
        showError(input, message);
        return false;
    }
}

function validateLoginForm() {
    let email = document.getElementById("email");
    let password = document.getElementById("password");
    let valid = true;

    if (!validateNotEmpty(email.value)) {
        showError(email, "L'email è obbligatoria.");
        valid = false;
    } else if (!validateEmail(email.value)) {
        showError(email, "Inserisci un'email valida.");
        valid = false;
    } else {
        clearError(email);
    }

    if (!validateNotEmpty(password.value)) {
        showError(password, "La password è obbligatoria.");
        valid = false;
    } else {
        clearError(password);
    }

    return valid;
}

function validateRegisterForm() {
    let nome = document.getElementById("nome");
    let cognome = document.getElementById("cognome");
    let email = document.getElementById("email");
    let password = document.getElementById("password");
    let telefono = document.getElementById("numTelefono");
    let valid = true;

    if (!validateNotEmpty(nome.value)) {
        showError(nome, "Il nome è obbligatorio.");
        valid = false;
    } else {
        clearError(nome);
    }

    if (!validateNotEmpty(cognome.value)) {
        showError(cognome, "Il cognome è obbligatorio.");
        valid = false;
    } else {
        clearError(cognome);
    }

    if (!validateNotEmpty(email.value)) {
        showError(email, "L'email è obbligatoria.");
        valid = false;
    } else if (!validateEmail(email.value)) {
        showError(email, "Inserisci un'email valida.");
        valid = false;
    } else {
        clearError(email);
    }

    if (!validateNotEmpty(password.value)) {
        showError(password, "La password è obbligatoria.");
        valid = false;
    } else {
        clearError(password);
    }

    if (telefono && !validatePhone(telefono.value)) {
        showError(telefono, "Inserisci un numero di telefono valido (10 cifre).");
        valid = false;
    } else if (telefono) {
        clearError(telefono);
    }

    return valid;
}

function validateProfileInfo() {
    let nome = document.querySelector("input[name='nome']");
    let cognome = document.querySelector("input[name='cognome']");
    let email = document.querySelector("input[name='email']");
    let valid = true;

    if (!validateNotEmpty(nome.value)) {
        showError(nome, "Il nome è obbligatorio.");
        valid = false;
    } else {
        clearError(nome);
    }

    if (!validateNotEmpty(cognome.value)) {
        showError(cognome, "Il cognome è obbligatorio.");
        valid = false;
    } else {
        clearError(cognome);
    }

    if (!validateNotEmpty(email.value)) {
        showError(email, "L'email è obbligatoria.");
        valid = false;
    } else if (!validateEmail(email.value)) {
        showError(email, "Inserisci un'email valida.");
        valid = false;
    } else {
        clearError(email);
    }

    return valid;
}

function validatePasswordForm() {
    let oldPwd = document.getElementById("oldPassword");
    let newPwd = document.getElementById("newPassword");
    let confirmPwd = document.getElementById("confirmPassword");
    let valid = true;

    if (!validateNotEmpty(oldPwd.value)) {
        showError(oldPwd, "Inserisci la password attuale.");
        valid = false;
    } else {
        clearError(oldPwd);
    }

    if (!validateNotEmpty(newPwd.value)) {
        showError(newPwd, "Inserisci la nuova password.");
        valid = false;
    } else if (newPwd.value.length < 6) {
        showError(newPwd, "La password deve avere almeno 6 caratteri.");
        valid = false;
    } else {
        clearError(newPwd);
    }

    if (newPwd.value !== confirmPwd.value) {
        showError(confirmPwd, "Le password non coincidono.");
        valid = false;
    } else {
        clearError(confirmPwd);
    }

    return valid;
}

function validatePositiveInt(value) {
    return /^\d+$/.test(value) && parseInt(value, 10) > 0;
}

window.onload = function() {
    setupModal();

    let loginForm = document.querySelector(".form form[action*='login']");
    if (loginForm) {
        loginForm.onsubmit = function() { return validateLoginForm(); };

        let inputs = loginForm.querySelectorAll("input");
        for (let i = 0; i < inputs.length; i++) {
            inputs[i].onchange = function() {
                if (this.id === "email") validateOnChange(this, validateEmail, "Inserisci un'email valida.");
            };
        }
    }

    let registerForm = document.querySelector(".form form[action*='register']");
    if (registerForm) {
        registerForm.onsubmit = function() { return validateRegisterForm(); };

        let rInputs = registerForm.querySelectorAll("input");
        for (let j = 0; j < rInputs.length; j++) {
            rInputs[j].onchange = function() {
                if (this.id === "email") validateOnChange(this, validateEmail, "Inserisci un'email valida.");
                if (this.id === "numTelefono") validateOnChange(this, validatePhone, "Inserisci un numero valido (10 cifre).");
            };
        }
    }

    let profileForm = document.querySelector("form input[name='action'][value='updateInfo']");
    if (profileForm) {

        let pForm = profileForm.parentElement;
        while (pForm && pForm.tagName !== "FORM") { pForm = pForm.parentElement; }
        if (pForm) pForm.onsubmit = function() { return validateProfileInfo(); };
    }

    let passwordForm = document.querySelector("form input[name='action'][value='updatePassword']");
    if (passwordForm) {

        let pwForm = passwordForm.parentElement;
        while (pwForm && pwForm.tagName !== "FORM") { pwForm = pwForm.parentElement; }

        if (pwForm) {
            pwForm.onsubmit = function() { return validatePasswordForm(); };

            let confirmInput = document.getElementById("confirmPassword");
            if (confirmInput) {
                confirmInput.onchange = function() {
                    let newPwd = document.getElementById("newPassword");
                    if (this.value !== newPwd.value) {
                        showError(this, "Le password non coincidono.");
                    } else {
                        clearError(this);
                    }
                };
            }
        }

        let dettaglioForm = document.querySelector(".dettaglio-form");
        if (dettaglioForm) {
            let adultiInput = document.getElementById("adulti");
            if (adultiInput) {
                adultiInput.onchange = function() {
                    validateOnChange(this, validatePositiveInt, "Inserisci un numero valido di adulti.");
                };
            }
        }
    }
}

function validateAddToCartForm() {
    let adulti = document.getElementById('adulti');
    if (adulti) {
        let val = adulti.value.trim();
        if (val === '' || isNaN(val) || parseInt(val, 10) < 1) {
            let errorEl = document.getElementById('adulti-error') || document.createElement('div');
            errorEl.id = 'adulti-error';
            errorEl.className = 'error-message';
            errorEl.innerHTML = 'numero biglietti adulti non valido';
            adulti.parentNode.appendChild(errorEl);
            adulti.focus();
            return false;
        }
    }
    return true;
}