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

let points

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
    let sessionID = sessionStorage.getItem("sessionID")
    const rewardData = await fetchRewards(sessionID)

    if(rewardData.success) {
        rewardData.rewards.forEach(r => makeReward(r))
        fetchCards(sessionID).then(d => d.cards.forEach(c => makeCard(c)))
        fetchRedeemedRewards(sessionID).then(d => d.rewards.forEach(r => makeRedeemedReward(r)))
        fetch("/api/getPoints", {
            method: "POST",
            body: JSON.stringify({
                sessionID: sessionID
            })
        })
        .then(res => res.json())
        .then(json => {
            points = json.points
            document.getElementById("pointsSpan").innerHTML = `${points} pts.`
        })
    } else {
        alert("Invalid session ID, returning to landing page...")
        window.location.replace("/")
    }
}