import APIObjects.*;

import APIObjects.GetCardsResponse.Card;
import APIObjects.GetRedeemedRewardsResponse.RedeemedReward;
import APIObjects.GetTransactionsResponse.Transaction;
import APIObjects.GetRewardsResponse.Reward;

public class TestDatabaseManager implements APIReturnable {
    SessionManager sm = new SessionManager();
    static final String testAccountID = "account_id_" + (System.currentTimeMillis() * (Math.random() + 0.5));

    public GetSessionIDResponse getSessionID(String username, String password) {
        // verify username and password here
        return new GetSessionIDResponse(true, sm.createSession(testAccountID));
    }

    public GetCardsResponse getCards(String sessionID) {
        System.out.println("abc");
        if(sm.getAccountID(sessionID).equals(testAccountID)) {
            Card[] cards = {
                    new Card("5412 8224 6310 0005", true, 50.0, 10.0, "2/22/22", 1.0),
                    new Card("7253 3256 7895 1245", false, 250.0, 0.0, null, 0.0)
            };
            System.out.println(true);
            return new GetCardsResponse(true, cards);
        } else {
            System.out.println(false);
            return new GetCardsResponse(false, null);
        }
    }

    public GetRedeemedRewardsResponse getRedeemedRewards(String sessionID) {
        if(sm.getAccountID(sessionID).equals(testAccountID)) {
            RedeemedReward[] rewards = {
                    new RedeemedReward("2/4/2022", "Code: 123456789",0),
                    new RedeemedReward("2/4/2022", "Voucher ID: 987654321", 1)
            };
            return new GetRedeemedRewardsResponse(true, rewards);
        } else {
            return new GetRedeemedRewardsResponse(false, null);
        }
    }

    public GetRewardsResponse getRewards(String sessionID) {
        if(sm.getAccountID(sessionID).equals(testAccountID)) {
            Reward[] rewards = {
                    new Reward(
                            0,
                            "$50 Wawa Gift Card",
                            "$50 gift card for usage at any Wawa location.",
                            50
                    ),
                    new Reward(
                            1,
                            "1000 CD Airlines Miles",
                            "Voucher for 1000 airplane travel miles for CD Airlines.",
                            5000
                    ),
                    new Reward(
                            2,
                            "$50 gas voucher",
                            "Voucher for $50 at participating Wawa, Shell, and Valero locations.",
                            50
                    )
            };
            return new GetRewardsResponse(true, rewards);
        } else {
            return new GetRewardsResponse(false, null);
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
