function validateAddForm() {
    const username = document.getElementById("username").value;
    const isUsernameValidated = new RegExp("^[a-zA-Z0-9]+$");
    if (isUsernameValidated.test(username)) {
    }
}