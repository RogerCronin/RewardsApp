import APIObjects.*;

import APIObjects.GetCardsResponse.Card;
import APIObjects.GetRedeemedRewardsResponse.RedeemedReward;
import APIObjects.GetTransactionsResponse.Transaction;
import APIObjects.GetRewardsResponse.Reward;

import java.sql.*;

public class TestDatabaseManager implements APIReturnable {

    // link to the sql database and the username and password for accessing it
    static final String url = "jdbc:mysql://localhost:3306/?user=root";
    static final String user = "root";
    static final String pass = "password";
    // session IDs are links to a specific account ID that has been verified by username and password
    // you don't have to check the username and password every single time you want
    // to make a request to the API
    SessionManager sm = new SessionManager();
    // generate an example account ID
    static int account_id = getAccountId("Username", "Password");
    static final int testAccountID = (int) (account_id + (System.currentTimeMillis() * (Math.random() + 0.5)));

    // this is called whenever the user logs in
    public GetSessionIDResponse getSessionID(String username, String password) {
        // boolean stores whether the data exists
        // verify username and password here
        try (Connection conn = DriverManager.getConnection(url, user, pass)
        ) {
            final String queryCheck = "SELECT count(*) from users WHERE username = ? AND password = ?";
            final PreparedStatement ps = conn.prepareStatement(queryCheck);
            ps.setString(1, username);
            ps.setString(2, password);
            final ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                // if success, return a new response object with newly created session ID
                return new GetSessionIDResponse(true, sm.createSession(testAccountID));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        // otherwise, return a failure response object
        return new GetSessionIDResponse(false, 0);
    }

    // called whenever the user gets credit and debit cards
    public GetCardsResponse getCards(String sessionID) {
        // fetch all the cards associated with an account ID
        // puts them into a Card[] array called cards
        // see examples below on how to construct a Card object
        if(sm.getAccountID(Integer.parseInt(sessionID)) == (testAccountID)) { // unnecessary example, always returns true
            Card[] cards = {
                    new Card("5412 8224 6310 0005", true, 50.0, 10.0, "2/22/22", 1.0),
                    new Card("7253 3256 7895 1245", false, 250.0, 0.0, null, 0.0)
            };
            // success response
            return new GetCardsResponse(true, cards);
        } else {
            // failure response if session ID doesn't exist or something
            return new GetCardsResponse(false, null);
        }
    }

    // called whenever the user gets the list of rewards they've redeemed
    public GetRedeemedRewardsResponse getRedeemedRewards(String sessionID) {
        // you can ignore all these if statements, at first I did it to test failure responses
        if(sm.getAccountID(Integer.parseInt(sessionID)) == (testAccountID)) {
            // make RedeemedReward[] array, see examples on how to construct RedeemedReward object with
            // information from the database
            RedeemedReward[] rewards = {
                    new RedeemedReward("2/4/2022", "Code: 123456789",0),
                    new RedeemedReward("2/4/2022", "Voucher ID: 987654321", 1)
            };
            return new GetRedeemedRewardsResponse(true, rewards);
        } else {
            return new GetRedeemedRewardsResponse(false, null);
        }
    }


    public String getRedeemDate(int account_id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)
        ) {
            final String queryCheck = "SELECT redeemDate FROM rewards WHERE account_id = ?";
            final PreparedStatement ps = conn.prepareStatement(queryCheck);
            ps.setInt(1, account_id);
            final ResultSet resultSet = ps.executeQuery();
            final String redeemDate = resultSet.getString("redeemDate");
            if(resultSet.next()) {
                return redeemDate;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    public int getRewardId(int account_id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)
        ) {
            final String queryCheck = "SELECT reward_id FROM rewards WHERE account_id = ?";
            final PreparedStatement ps = conn.prepareStatement(queryCheck);
            ps.setInt(1, account_id);
            final ResultSet resultSet = ps.executeQuery();
            final int reward_id = resultSet.getInt("reward_id");
            if(resultSet.next()) {
                return reward_id;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return 0;
    }

    // called whenever the user gets the list of rewards that can be redeemed by them
    // no database work needed here
    public GetRewardsResponse getRewards(String sessionID) {
        if(sm.getAccountID(Integer.parseInt(sessionID)) == (testAccountID)) {
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

    @Override
    public GetPointsResponse getPoints(String sessionID) {
        return null;
    }

    // called whenever the user gets a list of their card transactions
    public GetTransactionsResponse getTransactions(String sessionID) {
        if(sm.getAccountID(Integer.parseInt(sessionID)) == (testAccountID)) {
            // Transaction[] array of all the transactions associated with an account ID
            // put it into GetTransactionsResponse object, etc. etc.
            Transaction[] transactions = {
                    new Transaction("5412 8224 6310 0005", "TACO BELL 843 MIDDLETOWN", 0, 5.99, 1),
                    new Transaction("5412 8224 6310 0005", "WAWA 200 MIDDLETOWN", 3, 48.10, 20)
            };
            return new GetTransactionsResponse(true, transactions);
        } else {
            return new GetTransactionsResponse(false, null);
        }
    }

    public static int getAccountId(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)
        ) {
            final String queryCheck = "SELECT account_id FROM users WHERE username = ? AND password = ?";
            final PreparedStatement ps = conn.prepareStatement(queryCheck);
            ps.setString(1, username);
            ps.setString(2, password);
            final ResultSet resultSet = ps.executeQuery();
            final int account_id = resultSet.getInt("account_id");
            if(resultSet.next()) {
                return account_id;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return 0;
    }

    public int getCardNumber(int account_id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)
        ) {
            final String queryCheck = "SELECT cardNumber FROM cards WHERE account_id = ?";
            final PreparedStatement ps = conn.prepareStatement(queryCheck);
            ps.setInt(1, account_id);
            final ResultSet resultSet = ps.executeQuery();
            return resultSet.getInt("cardNumber");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return 0;
    }

}
