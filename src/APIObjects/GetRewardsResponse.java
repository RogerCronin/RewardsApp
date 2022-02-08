package APIObjects;

public class GetRewardsResponse {
    public record Reward(
        int rewardID,
        String name,
        String description,
        int points
    ) {}
    boolean success;
    Reward[] rewards;
    public GetRewardsResponse(boolean success, Reward[] rewards) {
        this.success = success;
        this.rewards = rewards;
    }
}
