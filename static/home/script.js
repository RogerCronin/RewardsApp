// page elements
const blanketDiv = document.getElementById("blanketDiv")

const fullCard = document.getElementById("fullCard")
const cardDescription = document.getElementById("cardDescription")
const creditDescriptionFields = cardDescription.children[1].children
const redeemedRewardDescription = document.getElementById("redeemedRewardDescription")
const rewardDescription = document.getElementById("rewardDescription")

const cardsDiv = document.getElementById("cardsDiv")
const redeemedRewardsDiv = document.getElementById("redeemedRewardsDiv")
const rewardsDiv = document.getElementById("rewardsDiv")

const cardInfoDiv = document.getElementById("cardInfoDiv")
const redeemedRewardInfoDiv = document.getElementById("redeemedRewardInfoDiv")
const rewardInfoDiv = document.getElementById("rewardInfoDiv")

// if you aren't logged in, go to landing page
if(!sessionStorage.getItem("sessionID")) window.location.replace("/")

getDataOnLoad() // fetch all of the dynamic content

// when back button on info box is clicked
function goBack() {
    blanketDiv.classList.remove("active")
    setTimeout(() => {
        blanketDiv.style.zIndex = -1
        cardInfoDiv.style.display = "none"
        redeemedRewardInfoDiv.style.display = "none"
        rewardInfoDiv.style.display = "none"
    }, 500) // move to back after css transition
}

async function getDataOnLoad() {
    const cardData = await fetchCards(sessionStorage.getItem("sessionID"))
    if(!cardData.success) {
        alert("Invalid session ID, returning to landing page")
        window.location.replace("/")
    } else {
        const rewardData = await fetchRewards(sessionStorage.getItem("sessionID"))
        rewardData.rewards.forEach(r => makeReward(r))
        //const redeemedRewardData = await fetchRedeemedRewards(sessionStorage.getItem("sessionID"))
        cardData.cards.forEach(c => makeCard(c))
        //redeemedRewardData.rewards.forEach(r => makeRedeemedReward(r))
    }
}