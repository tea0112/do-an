function validateAddForm() {
    const email = document.getElementById("emailInput").value;
    const isEmailValidated = new RegExp("^[a-zA-Z0-9]+@[a-zA-Z0-9\.]+$");
    if (isEmailValidated.test(email)) {

    }
}