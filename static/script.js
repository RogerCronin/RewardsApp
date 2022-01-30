// debug
const sleep = ms => new Promise(r => setTimeout(r, ms))
const id = iden => document.getElementById(iden)

// page elements
const alertP = id("alert")
const usernameInput = id("username")
const passwordInput = id("password")
const submitButton = id("submit")
const spinnerDiv = id("spinner")

const spinnerArray = ["▙", "▛", "▜", "▟"]
let spinnerFrame = 0
let spinnerTimeout

function toggleButton(on) {
    submitButton.toggleAttribute("disabled")
    usernameInput.toggleAttribute("disabled")
    passwordInput.toggleAttribute("disabled")
    if(on) {
        spinnerDiv.style.visibility = "hidden"
        clearInterval(spinnerTimeout)
        submitButton.style.backgroundColor = "var(--orange)"
    } else {
        spinnerDiv.style.visibility = "visible"
        spinnerTimeout = setInterval(cycleSpinner, 100)
        submitButton.style.backgroundColor = "var(--gray)"
    }
}

function cycleSpinner() {
    spinnerFrame++
    if(spinnerFrame === 4) spinnerFrame = 0
    spinnerDiv.innerHTML = spinnerArray[spinnerFrame]
}

function displayAlert(text = "Incorrect username or password!") {
    alertP.innerHTML = text
    alertP.style.visibility = "visible"
}

/* response format
{
    success: boolean,
    sessionID: string or null
}
*/
async function fetchCredentials(username, password) {
    const res = await fetch("/api/login", {
        method: "POST",
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
    if(!res.ok) throw new Error(res.statusText)
    return await res.json()
}

async function login() {
    toggleButton(false)
    await sleep(1000)
    try {
        const data = await fetchCredentials(usernameInput.value, passwordInput.value)
        if(data.success) {
            sessionStorage.setItem("sessionID", data.sessionID)
            window.location.replace("./home/")
        } else {
            displayAlert()
        }
    } catch(e) {
        displayAlert(e)
    } finally {
        toggleButton(true)
    }
}