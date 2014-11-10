import java.util.List;

enum Combination {

    ROYAL_FLUSH("Royal Flush"),
    STRAIGHT_FLUSH("Straight Flush"),
    FOUR_OF_A_KIND("Four of a Kind"),
    FULL_HOUSE("Full House"),
    FLUSH("Flush"),
    STRAIGHT("Straight"),
    THREE_OF_A_KIND("Three of a Kind"),
    TWO_PAIRS("Two Pairs"),
    JACKS_OR_BETTER("Jacks or Better");

    private final String value;

    private Combination(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    static void check(List<Card> hand) throws CombinationException {

        checkRoyalFlush(hand);
        checkStraightFlush(hand);
        checkFourOfAKind(hand);
        checkFullHouse(hand);
        checkFlush(hand);
        checkStraight(hand);
        checkThreeOfAKind(hand);
        checkTwoPairs(hand);
        checkJacksOrBetter(hand);
    }

    private static void checkRoyalFlush(List<Card> cards) throws CombinationException {

    }

    private static void checkStraightFlush(List<Card> cards) throws CombinationException {

    }

    private static void checkFourOfAKind(List<Card> cards) throws CombinationException {

    }

    private static void checkFullHouse(List<Card> cards) throws CombinationException {

    }

    private static void checkFlush(List<Card> cards) throws CombinationException {

    }

    private static void checkStraight(List<Card> cards) throws CombinationException {

    }

    private static void checkThreeOfAKind(List<Card> cards) throws CombinationException {

    }

    private static void checkTwoPairs(List<Card> cards) throws CombinationException {

    }

    private static void checkJacksOrBetter(List<Card> cards) throws CombinationException {

    }

    private static void checkPair(List<Card> cards) throws CombinationException {

    }
}
