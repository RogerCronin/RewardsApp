package APIObjects;

public class GetSessionIDResponse {
    boolean success;
    int sessionID;
    public GetSessionIDResponse(boolean success, int sessionID) {
        this.success = success;
        this.sessionID = sessionID;
    }
}
