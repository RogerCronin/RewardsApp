/* response format
{
    success: boolean,
    cards: [
        {
            cardNumber: string,
            isCreditCard: boolean,
            balance: double,
            credit: double, // everything below this is for credit cards only
            billDate: string,
            billAmount: string
        }
    ]
*/
async function fetchCards(sessionID) {
    const res = await fetch("/api/getCards", {
        method: "POST",
        body: JSON.stringify({
            sessionID: sessionID
        })
    })
    if(!res.ok) throw new Error(res.statusText)
    return await res.json()
}

// creates a card component and appends it to cardDiv
function makeCard(data) {
    const { cardNumber, isCreditCard } = data
    let card = document.createElement("div")
    if(isCreditCard) {
        card.classList.add("creditCard", "card")
        card.innerHTML = `<h3>Credit Card</h3><p>${cardNumber}</p>`
    } else {
        card.classList.add("debitCard", "card")
        card.innerHTML = `<h3>Debit Card</h3><p>${cardNumber}</p>`
    }
    card.data = data // attach all card data to the card element for usage in handleCard
    card.onclick = handleCard
    cardsDiv.append(card)
}

// when card is clicked, show info box
function handleCard() {
    // most of this code is just setting html elements to fit data from card.data
    const { cardNumber, isCreditCard, balance, credit, billDate, billAmount } = this.data
    fullCard.children[2].innerHTML = cardNumber
    cardDescription.children[0].innerHTML = `Balance: \$${balance}`
    if(isCreditCard) {
        fullCard.style.backgroundColor = "var(--blue)"
        fullCard.children[1].innerHTML = "Credit Card"
        cardDescription.children[1].style.display = "block"
        creditDescriptionFields[0].innerHTML = `Credit: \$${credit}`
        creditDescriptionFields[1].innerHTML = `Next bill: ${billDate}`
        creditDescriptionFields[2].innerHTML = `Total due: \$${billAmount}`
        creditDescriptionFields[3].innerHTML = `Minimum payment: \$${Math.ceil(billAmount / 10)}`
    } else {
        fullCard.style.backgroundColor = "var(--orange)"
        fullCard.children[1].innerHTML = "Debit Card"
        cardDescription.children[1].style.display = "none"
    }
    cardInfoDiv.style.display = "flex"
    blanketDiv.classList.add("active") // makes screen go dim
    blanketDiv.style.zIndex = 1 // needed to make screen dimming appear above everything else
}