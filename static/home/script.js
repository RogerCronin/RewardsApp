const cardsDiv = document.getElementById("cardsDiv")
const blanketDiv = document.getElementById("blanketDiv")
const fullCard = document.getElementById("fullCard")
const cardDescription = document.getElementById("cardDescription")
const creditDescriptionFields = cardDescription.children[1].children

if(!sessionStorage.getItem("sessionID")) window.location.replace("/")

function makeCard(data) {
    const { cardNumber, isCreditCard, balance, credit, billDate, billAmount } = data
    let cardDiv = document.createElement("div")
    if(isCreditCard) {
        cardDiv.classList.add("creditCard")
        cardDiv.innerHTML = `<h3>Credit Card</h3><p>${cardNumber}</p>`
    } else {
        cardDiv.classList.add("debitCard")
        cardDiv.innerHTML = `<h3>Debit Card</h3><p>${cardNumber}</p>`
    }
    cardDiv.data = data
    cardDiv.onclick = handleCard
    cardsDiv.append(cardDiv)
}

function handleCard() {
    const { cardNumber, isCreditCard, balance, credit, billDate, billAmount } = this.data
    fullCard.children[2].innerHTML = cardNumber
    cardDescription.children[0].innerHTML = `Balance: \$${balance}`
    if(isCreditCard) {
        fullCard.style.backgroundColor = "var(--blue)"
        fullCard.children[1].innerHTML = "Credit Card"
        cardDescription.children[1].style.display = "none"
        creditDescriptionFields[0].innerHTML = `Credit: \$${credit}`
        creditDescriptionFields[1].innerHTML = `Next bill: ${billDate}`
        creditDescriptionFields[2].innerHTML = `Total due: \$${billAmount}`
        creditDescriptionFields[3].innerHTML = `Minimum payment: \$${Math.ceil(billAmount / 10)}`
    } else {
        fullCard.style.backgroundColor = "var(--orange)"
        fullCard.children[1].innerHTML = "Debit Card"
        cardDescription.children[1].style.display = "block"
    }
    blanketDiv.classList.add("active")
    blanketDiv.style.zIndex = 1
}

function goBack() {
    blanketDiv.classList.remove("active")
    setTimeout(() => blanketDiv.style.zIndex = -1, 500) // move to back after css transition
}

const creditCard = {
    cardNumber: "4000 3000 2000 1000",
    isCreditCard: true,
    balance: 200,
    credit: 50,
    billDate: "2/2/2022",
    billAmount: 20
}

const debitCard = {
    cardNumber: "1000 2000 3000 4000",
    isCreditCard: false,
    balance: 400,
    credit: 0,
    billDate: null,
    billAmount: 0
}

makeCard(creditCard)
makeCard(debitCard)