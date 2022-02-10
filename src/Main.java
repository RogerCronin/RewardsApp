import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

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
    APIManager api = new APIManager();

    ServerHandler(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {

        // get requested file path
        String filePath = ex.getRequestURI().getPath();

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
        try {
            path = path.substring(5); // remove the heading \api\
            return path.equals("login") ||
                    path.equals("getCards") ||
                    path.equals("getRewards") ||
                    path.equals("getPoints") ||
                    path.equals("redeemReward"); // if doesn't equal any of these
        } catch(Exception e) {
            return false;
        }
    }

    // send request to proper API handler
    public void apiHandle(HttpExchange ex, String path) {
        path = path.substring(5);
        switch(path) {
            case "login" -> api.apiLogin(ex);
            case "getCards" -> api.apiGetCards(ex);
            case "getRedeemedRewards" -> api.apiGetRedeemedRewards(ex);
            case "redeemReward" -> api.apiRedeemReward(ex);
            case "getTransactions" -> api.apiGetTransactions(ex);
            case "getPoints" -> api.apiGetPoints(ex);
            case "getRewards" -> api.apiGetRewards(ex);
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