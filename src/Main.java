import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String baseDirectory;
        if(args.length == 0) baseDirectory = System.getProperty("user.dir") + "\\static\\";
        else baseDirectory = args[0];
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 5500), 0);
            server.createContext("/", new ServerHandler(baseDirectory)); // ServerHandler will handle all requests
            server.start();
            System.out.println("Server started on port 5500");
        } catch(IOException e) {
            System.out.println("Failed to start server: " + e);
        }
    }
}

class ServerHandler implements HttpHandler {
    // where all the files from our static server will be located
    String baseDirectory;

    ServerHandler(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {

        // get requested file path and clean it up
        URI uri = ex.getRequestURI();
        String filePath = new File(uri.getPath()).getPath();
        if(filePath.endsWith("/")) filePath = filePath.substring(0, filePath.length() - 1);

        // if it should be handled by api instead of static site handler, do that
        if(!ex.getRequestMethod().equals("GET") || ServerHandler.isAPIRequest(filePath)) {
            apiHandle(ex, filePath);
            return;
        }

        // if navigating to a directory, point it towards index.html located there
        if(!filePath.contains(".")) filePath += "/index.html";
        File path = new File(baseDirectory, filePath);

        // set proper MIME content type headers using ContentTypeHandler
        Headers headers = ex.getResponseHeaders();
        headers.add("Content-Type", ContentTypeHandler.getContentTypeHeader(filePath));

        OutputStream out = ex.getResponseBody();

        if(path.exists()) { // if file exists
            ex.sendResponseHeaders(200, path.length());
            out.write(Files.readAllBytes(path.toPath()));
        } else { // otherwise, send a 404 error
            System.out.println("File not found: " + path.getAbsolutePath());
            ex.sendResponseHeaders(404, 0);
            out.write("404 File not found".getBytes());
        }

        out.close(); // close output stream, sending it to the recipient
    }

    // returns true if request should be handled as an API request
    public static boolean isAPIRequest(String path) {
        path = path.substring(1); // remove the heading \ character
        return path.equals("login") ||
                path.equals("getCards") ||
                path.equals("getRewards") ||
                path.equals("redeemReward"); // if doesn't equal any of these
    }

    // send request to proper API handler
    public void apiHandle(HttpExchange ex, String path) {
        path = path.substring(1);
        switch(path) {
            case "login" -> RewardsInterface.apiLogin(ex);
            case "getCards" -> RewardsInterface.apiGetCards(ex);
            case "getRewards" -> RewardsInterface.apiGetRewards(ex);
            case "redeemReward" -> RewardsInterface.apiRedeemReward(ex);
            default -> { // this should never happen, but just in case
                try {
                    OutputStream out = ex.getResponseBody();
                    ex.sendResponseHeaders(500, 0);
                    out.write("500 Unknown error".getBytes());
                } catch(IOException e) {
                    System.out.println("Error at apiHandle!");
                    e.printStackTrace();
                }
            }
        }
    }
}

// contains all api responses
class RewardsInterface {
    // JSON builder object
    // you may have to import Gson from Maven if this doesn't run on your computer
    static Gson gson = new Gson();

    // response format for the /login endpoint
    static class LoginResponse {
        boolean success;
        String sessionID;
        LoginResponse(boolean success, String sessionID) {
            this.success = success;
            this.sessionID = sessionID;
        }
    }

    public static void apiLogin(HttpExchange ex) {
        // TODO implement proper responses
        // connect to database and send false when incorrect username + password
        // for now I'm just sending over example data so I can keep working on the frontend
        // do session manager later bc it's complicated and idk how to do it yet
        // if you want to make it ask me and I can send over my idea for what it should be
        LoginResponse lr = new LoginResponse(true, "abc");
        OutputStream out = ex.getResponseBody();
        String json = gson.toJson(lr); // convert LoginResponse instance to JSON string
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

    static class GetCardsResponse {
        static class Card {
            int cardType; // 0 is credit, 1 is debit
            double balance;
            double credit; // only for credit cards
            /* might want to use something like a UNIX timestamp if this were
            a real project, but a hardcoded String value is fine for now
             */
            String billDate; // only for credit cards
            double billAmount; // only for credit cards
            Card(int cardType, double balance, double credit, String billDate, double billAmount) {
                this.cardType = cardType;
                this.balance = balance;
                this.credit = credit;
                this.billDate = billDate;
                this.billAmount = billAmount;
            }
        }
        Card[] cards;
        GetCardsResponse(Card[] cards) {
            this.cards = cards;
        }
    }

    public static void apiGetCards(HttpExchange ex) {
        GetCardsResponse.Card[] cards = {
            new GetCardsResponse.Card(0, 50.0, 10.0, "2/22/22", 1.0),
            new GetCardsResponse.Card(1, 250.0, 0.0, null, 0.0),
        };
        GetCardsResponse gcr = new GetCardsResponse(cards);
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

class ContentTypeHandler {
    final static HashMap<String, String> contentTypes = new HashMap<>() {{
        put("bmp", "image/bmp");
        put("css", "text/css");
        put("gif", "image/gif");
        put("html", "text/html");
        put("jpeg", "image/jpeg");
        put("jpg", "image/jpeg");
        put("js", "text/javascript");
        put("mjs", "text/javascript");
        put("json", "application/json");
        put("mp3", "audio/mpeg");
        put("mp4", "video/mp4");
        put("mpeg", "video/mpeg");
        put("otf", "font/otf");
        put("png", "image/png");
        put("svg", "image/svg+xml");
        put("txt", "text/plain");
        put("wav", "audio/wav");
        put("weba", "audio/webm");
        put("webm", "video/webm");
        put("webp", "image/webp");
        put("xml", "application/xml");
    }};
    final static Pattern regex = Pattern.compile("^.*\\.(.+)$");

    public static String getContentTypeHeader(String fileName) {
        Matcher m = regex.matcher(fileName);
        if(m.find() && contentTypes.containsKey(m.group(1))) {
            return contentTypes.get(m.group(1));
        }
        return "text/plain";
    }
}
