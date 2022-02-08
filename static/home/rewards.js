let rewardTable = [

]

/* response format
{
    success: boolean,
    rewards: [
        {
            dateRedeemed: string,
            information: string,
            rewardID: int
        }
    ]
*/
async function fetchRedeemedRewards(sessionID) {
    const res = await fetch("/api/getRedeemedRewards", {
        method: "POST",
        body: JSON.stringify({
            sessionID: sessionID
        })
    })
    if(!res.ok) throw new Error(res.statusText)
    return await res.json()
}

/* response format
{
    success: boolean,
    rewards: [
        rewardID: int,
        name: string,
        description: string,
        points: int
    ]
}
*/
async function fetchRewards(sessionID) {
    const res = await fetch("/api/getRewards", {
        method: "POST",
        body: JSON.stringify({
            sessionID: sessionID
        })
    })
    if(!res.ok) throw new Error(res.statusText)
    let json = await res.json()
    rewardTable = []
    json.rewards.forEach(r => rewardTable.push(r.name))
    return json
}

// creates a card component and appends it to cardDiv
function makeRedeemedReward(data) {
    const { rewardID } = data
    let card = document.createElement("div")
    card.classList.add("card")
    card.innerHTML = `<h3>${rewardTable[rewardID]}</h3>`
    card.data = data // attach all card data to the card element for usage in handleCard
    card.onclick = handleCard
    cardsDiv.append(card)
}

function makeReward(data) {
    const { rewardID, points } = data
    let card = document.createElement("div")
    card.classList.add("rewardCard", "card")
    card.innerHTML = `<h3>${rewardTable[rewardID]}</h3><p>${points} pts.</p>`
    card.data = data
    card.onclick = handleReward
    rewardsDiv.append(card)
}

function handleReward() {
    const { name, description, points } = this.data
    rewardDescription.children[0].innerHTML = name
    rewardDescription.children[1].innerHTML = `${points} pts.`
    rewardDescription.children[2].innerHTML = description
    rewardInfoDiv.style.display = "flex"
    blanketDiv.classList.add("active")
    blanketDiv.style.zIndex = 1
}