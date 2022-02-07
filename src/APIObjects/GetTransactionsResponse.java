package APIObjects;

public class GetTransactionsResponse {
    public record Transaction (
        String card,
        String name,
        int categoryID,
        double amount,
        int points
    ) {}
    boolean success;
    Transaction[] transactions;
    public GetTransactionsResponse(boolean success, Transaction[] transactions) {
        this.success = success;
        this.transactions = transactions;
    }
}
