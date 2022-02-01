import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

// contains all api responses
public class APIManager {
    // JSON builder object
    // you may have to import Gson from Maven if this doesn't run on your computer
    static Gson gson = new Gson();

    // response format for the /login endpoint
    static class LoginOutput {
        boolean success;
        String sessionID;
        LoginOutput(boolean success, String sessionID) {
            this.success = success;
            this.sessionID = sessionID;
        }
    }

    static class LoginInput {
        String username;
        String password;
    }

    public static void apiLogin(HttpExchange ex) throws IOException {
        // TODO implement proper responses
        // connect to database and send false when incorrect username + password
        // for now I'm just sending over example data so I can keep working on the frontend
        // do session manager later bc it's complicated and idk how to do it yet
        // if you want to make it ask me and I can send over my idea for what it should be

        OutputStream out = ex.getResponseBody();
        LoginInput in;

        try {
            String responseText = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            in = gson.fromJson(responseText, LoginInput.class);
        } catch(IOException e) {
            ex.sendResponseHeaders(400, 0);
            out.write("400 Malformed response body".getBytes());
            out.close();
            return;
        }

        // TODO check username and password
        if(in.username.equals("asdf") && in.password.equals("1234")) {
            // create json string
            LoginOutput lr = new LoginOutput(true, "abc");
            String json = gson.toJson(lr); // convert LoginResponse instance to JSON string

            try {
                // send over the JSON data
                ex.sendResponseHeaders(200, json.length());
                ex.getResponseHeaders().add("Content-Type", "application/json");
                out.write(json.getBytes());
                out.close();
            } catch(IOException e) {
                System.out.println("Error at apiLogin! - send success");
                e.printStackTrace();
            }

        } else {
            LoginOutput lr = new LoginOutput(false, null);
            String json = gson.toJson(lr);

            try {
                ex.sendResponseHeaders(200, json.length());
                ex.getResponseHeaders().add("Content-Type", "application/json");
                out.write(json.getBytes());
                out.close();
            } catch(IOException e) {
                System.out.println("Error at apiLogin! - send failure");
                e.printStackTrace();
            }

        }
    }

    static class GetCardsResponse {
        static class Card {
            String cardNumber;
            boolean isCreditCard;
            double balance;
            double credit; // only for credit cards
            /* might want to use something like a UNIX timestamp if this were
            a real project, but a hardcoded String value is fine for now
             */
            String billDate; // only for credit cards
            double billAmount; // only for credit cards
            Card(String cardNumber, boolean isCreditCard, double balance, double credit, String billDate, double billAmount) {
                this.cardNumber = cardNumber;
                this.isCreditCard = isCreditCard;
                this.balance = balance;
                this.credit = credit;
                this.billDate = billDate;
                this.billAmount = billAmount;
            }
        }
        boolean success;
        Card[] cards;
        GetCardsResponse(boolean success, Card[] cards) {
            this.success = success;
            this.cards = cards;
        }
    }

    public static void apiGetCards(HttpExchange ex) {
        GetCardsResponse.Card[] cards = {
            new GetCardsResponse.Card("5412 8224 6310 0005", true, 50.0, 10.0, "2/22/22", 1.0),
            new GetCardsResponse.Card("7253 3256 7895 1245", false, 250.0, 0.0, null, 0.0)
        };
        GetCardsResponse gcr = new GetCardsResponse(true, cards);
        OutputStream out = ex.getResponseBody();
        String json = gson.toJson(gcr); // convert LoginResponse instance to JSON string
        try {
            // send over the JSON data
            ex.sendResponseHeaders(200, json.length());
            ex.getResponseHeaders().add("Content-Type", "application/json");
            out.write(json.getBytes());
            out.close();
        } catch(IOException e) {
            System.out.println("Error at apiLogin!");
            e.printStackTrace();
        }
    }

    // TODO implement GetRewardsResponse
    // and also apiGetRewards()

    public static void apiGetRewards(HttpExchange ex) {

    }

    // TODO implement RedeemRewardResponse
    // apiRedeemReward() too

    public static void apiRedeemReward(HttpExchange ex) {

    }
}
