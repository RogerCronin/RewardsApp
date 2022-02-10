import APIObjects.*;

public interface APIReturnable {
    GetSessionIDResponse getSessionID(String username, String password);
    GetCardsResponse getCards(String sessionID);
    GetRedeemedRewardsResponse getRedeemedRewards(String sessionID);
    GetTransactionsResponse getTransactions(String sessionID);
    GetRewardsResponse getRewards(String sessionID);
    GetPointsResponse getPoints(String sessionID);
}
