package APIObjects;

public class GetSessionIDResponse {
    boolean success;
    String sessionID;
    public GetSessionIDResponse(boolean success, String sessionID) {
        this.success = success;
        this.sessionID = sessionID;
    }
}
