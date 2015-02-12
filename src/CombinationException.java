import java.util.List;

class CombinationException extends Exception {

    private Combination combination;
    private List<Card> cards;

    CombinationException(Combination combination, List<Card> cards) {
        this.combination = combination;
        this.cards = cards;
    }

    @Override
    public String getMessage() {
        return String.format("%s%n%s",
                Paint.getAnsiString(Paint.MAGENTA, combination + ":"),
                cards);
    }

    Combination getCombination() {
        return combination;
    }

    List<Card> getCards() {
        return cards;
    }
}
