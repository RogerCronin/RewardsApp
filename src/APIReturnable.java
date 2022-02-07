import APIObjects.GetCardsResponse;
import APIObjects.GetRedeemedRewardsResponse;
import APIObjects.GetTransactionsResponse;
import APIObjects.GetSessionIDResponse;

public interface APIReturnable {
    GetSessionIDResponse getSessionID(String username, String password);
    GetCardsResponse getCards(String sessionID);
    GetRedeemedRewardsResponse getRedeemedRewards(String sessionID);
    GetTransactionsResponse getTransactions(String sessionID);
}
