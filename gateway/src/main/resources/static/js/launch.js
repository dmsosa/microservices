//send request to auth server, get browser's access Token
const textP = document.getElementById("text");
const superButton = document.getElementById("super");
superButton.addEventListener("click", getAccessToken)
async function getAccessToken() {
    fetch("http://localhost:9000/api/uaa/oauth2/token", {
        method: "POST",
        headers: {
            "Authorization": "Basic YnJvd3NlckNsaWVudDpicm93c2Vy"
        }
    })
}