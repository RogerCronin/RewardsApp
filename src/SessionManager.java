import java.util.HashMap;

public class SessionManager {
    final static int MIN_ACTIVE = 30;
    record Session(
        int accountID,
        long timeWhenDelete
    ) {}
    HashMap<Integer, Session> sessionIDList = new HashMap<>();

    public int generateSessionID() {
        return (int) (System.currentTimeMillis() * (Math.random() + 0.5));
    }

    public int createSession(int accountID) {
        int sessionID = generateSessionID();
        sessionIDList.put(sessionID, new Session(accountID,
                System.currentTimeMillis() + MIN_ACTIVE * 60000));
        return sessionID;
    }

    public int getAccountID(int sessionID) {
        return sessionIDList.get(sessionID).accountID;
    }

    // run every once in a while
    public void garbageCollect() {
        long now = System.currentTimeMillis();
        sessionIDList.entrySet().removeIf(e -> e.getValue().timeWhenDelete > now);
    }
}
