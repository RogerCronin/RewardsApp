package APIObjects;

public class GetCardsResponse {
    public record Card(
        String cardNumber,
        boolean isCreditCard,
        double balance,
        double credit, // only for credit cards
        /* might want to use something like a UNIX timestamp if this were a
        real project, but a hardcoded String value for billDate fine for now
         */
        String billDate, // only for credit cards
        double billAmount // only for credit cards
    ) {}
    boolean success;
    Card[] cards;
    public GetCardsResponse(boolean success, Card[] cards) {
        this.success = success;
        this.cards = cards;
    }
}
