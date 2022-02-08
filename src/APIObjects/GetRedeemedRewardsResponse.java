package APIObjects;

public class GetRedeemedRewardsResponse {
    public record RedeemedReward(
        String dateRedeemed,
        String information, // something like a gift card code, voucher ID, etc.
        int rewardID
    ) {}
    boolean success;
    RedeemedReward[] rewards;
    public GetRedeemedRewardsResponse(boolean success, RedeemedReward[] rewards) {
        this.success = success;
        this.rewards = rewards;
    }
}
