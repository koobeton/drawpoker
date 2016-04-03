package cards;

import misc.Paint;

import java.util.List;

public class CombinationThrowable extends Throwable {

    private Combination combination;
    private List<Card> cards;

    CombinationThrowable(Combination combination, List<Card> cards) {
        this.combination = combination;
        this.cards = cards;
    }

    @Override
    public String getMessage() {
        return String.format("%s%n%s",
                Paint.getAnsiString(Paint.MAGENTA, combination + ":"),
                cards);
    }

    public Combination getCombination() {
        return combination;
    }

    List<Card> getCards() {
        return cards;
    }
}
