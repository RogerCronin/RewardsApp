import APIObjects.GetCardsResponse;
import APIObjects.GetRedeemedRewardsResponse;
import APIObjects.GetSessionIDResponse;

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
    static APIReturnable db = new TestDatabaseManager();
    //static DatabaseManager db = new DatabaseManager();

    public APIManager() {
        //db.init();
    }

    static class LoginInput {
        String username;
        String password;
    }

    static class RequestInput {
        String sessionID;
    }

    public <T> T getRequestInput(HttpExchange ex, OutputStream out, Class<T> inputClass) throws Exception {
        try {
            String responseText = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            return gson.fromJson(responseText, inputClass);
        } catch(IOException e) {
            ex.sendResponseHeaders(400, 0);
            out.write("400 Malformed response body".getBytes());
            out.close();
            throw new Exception("Malformed response body");
        }
    }

    public RequestInput getRequestInput(HttpExchange ex, OutputStream out) throws Exception {
        return getRequestInput(ex, out, RequestInput.class);
    }

    public static void sendResponse(HttpExchange ex, OutputStream out, Object object) {
        String json = gson.toJson(object);
        try(out) {
            // send over the JSON data
            ex.sendResponseHeaders(200, json.length());
            ex.getResponseHeaders().add("Content-Type", "application/json");
            out.write(json.getBytes());
        } catch (IOException e) {
            System.out.println("Error at sendResponse!");
            e.printStackTrace();
        }
    }

    public void apiLogin(HttpExchange ex) {
        OutputStream out = ex.getResponseBody();
        LoginInput in;
        try {
            in = getRequestInput(ex, out, LoginInput.class);
        } catch(Exception e) { return; }
        GetSessionIDResponse res = db.getSessionID(in.username, in.password);
        sendResponse(ex, out, res);
    }

    public void apiGetPoints(HttpExchange ex) {

    }

    public void apiGetCards(HttpExchange ex) {
        OutputStream out = ex.getResponseBody();
        RequestInput in;
        try {
            in = getRequestInput(ex, out);
        } catch(Exception e) { return; }

        GetCardsResponse res = db.getCards(in.sessionID);
        sendResponse(ex, out, res);
    }

    public void apiGetRedeemedRewards(HttpExchange ex) {
        OutputStream out = ex.getResponseBody();
        RequestInput in;
        try {
            in = getRequestInput(ex, out);
        } catch(Exception e) { return; }

        GetRedeemedRewardsResponse res = db.getRedeemedRewards(in.sessionID);
        sendResponse(ex, out, res);
    }

    public void apiRedeemReward(HttpExchange ex) {

    }
}
