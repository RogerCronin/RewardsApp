import APIObjects.GetCardsResponse;
import APIObjects.GetRedeemedRewardsResponse;
import APIObjects.GetTransactionsResponse;
import APIObjects.GetSessionIDResponse;

import APIObjects.GetCardsResponse.Card;
import APIObjects.GetRedeemedRewardsResponse.RedeemedReward;
import APIObjects.GetTransactionsResponse.Transaction;

public class TestDatabaseManager implements APIReturnable {
    SessionManager sm = new SessionManager();
    static final String testAccountID = "account_id_" + (System.currentTimeMillis() * (Math.random() + 0.5));

    public GetSessionIDResponse getSessionID(String username, String password) {
        // verify username and password here
        return new GetSessionIDResponse(true, sm.createSession(testAccountID));
    }

    public GetCardsResponse getCards(String sessionID) {
        if(sm.getAccountID(sessionID).equals(testAccountID)) {
            Card[] cards = {
                    new Card("5412 8224 6310 0005", true, 50.0, 10.0, "2/22/22", 1.0),
                    new Card("7253 3256 7895 1245", false, 250.0, 0.0, null, 0.0)
            };
            return new GetCardsResponse(true, cards);
        } else {
            return new GetCardsResponse(false, null);
        }
    }

    public GetRedeemedRewardsResponse getRedeemedRewards(String sessionID) {
        if(sm.getAccountID(sessionID).equals(testAccountID)) {
            RedeemedReward[] rewards = {
                    new RedeemedReward("2/4/2022", 0),
                    new RedeemedReward("2/4/2022", 1)
            };
            return new GetRedeemedRewardsResponse(true, rewards);
        } else {
            return new GetRedeemedRewardsResponse(false, null);
        }
    }

    public GetTransactionsResponse getTransactions(String sessionID) {
        if(sm.getAccountID(sessionID).equals(testAccountID)) {
            Transaction[] transactions = {
                    new Transaction("5412 8224 6310 0005", "TACO BELL 843 MIDDLETOWN", 0, 5.99, 1),
                    new Transaction("5412 8224 6310 0005", "WAWA 200 MIDDLETOWN", 3, 48.10, 20)
            };
            return new GetTransactionsResponse(true, transactions);
        } else {
            return new GetTransactionsResponse(false, null);
        }
    }
}
