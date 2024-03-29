//send request to auth server, get browser's access Token
const textP = document.getElementById("text");
const superButton = document.getElementById("super");
const currentButton = document.getElementById("current");
superButton.addEventListener("click", createAccount)
currentButton.addEventListener("click", getCurrentAccount)


async function createAccount(username, email, password) {
    var data = {
        "username": username
        "email": email,
        "password": password
    }

    fetch("/account/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: data

    }).then((response) =>  response.json())
    .then((json) => { console.log(json()) })
    .catch((error) => {
        console.log(error);
        console.log(requestBody)
    })
}

function getCurrentAccount() {
    const currentAccount = localStorage.getItem("account");
    fetch()
}