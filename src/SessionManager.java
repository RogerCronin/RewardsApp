import java.util.HashMap;

public class SessionManager {
    final static int MIN_ACTIVE = 30;
    record Session(
        String accountID,
        long timeWhenDelete
    ) {}
    HashMap<String, Session> sessionIDList = new HashMap<>();

    public String generateSessionID() {
        return "session_id_" + (System.currentTimeMillis() * (Math.random() + 0.5));
    }

    public String createSession(String accountID) {
        String sessionID = generateSessionID();
        sessionIDList.put(sessionID, new Session(accountID,
                System.currentTimeMillis() + MIN_ACTIVE * 60000));
        return sessionID;
    }

    public String getAccountID(String sessionID) {
        return sessionIDList.get(sessionID).accountID;
    }

    // run every once in a while
    public void garbageCollect() {
        long now = System.currentTimeMillis();
        sessionIDList.entrySet().removeIf(e -> e.getValue().timeWhenDelete > now);
    }
}
