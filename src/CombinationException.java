import java.util.List;

public class CombinationException extends Exception {

    private Combination combination;

    private List<Card> cards;

    CombinationException(Combination combination, List<Card> cards) {
        this.combination = combination;
        this.cards = cards;
    }

    @Override
    public String getMessage() {
        return combination + ":\n" + cards;
    }

    List<Card> getCards() {
        return cards;
    }
}
