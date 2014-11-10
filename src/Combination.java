import java.util.ArrayList;
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

        for (int i = 0; i < cards.size() - 3; i++) {
            if (cards.get(i).compareTo(cards.get(i + 1)) == 0 &&
                    cards.get(i).compareTo(cards.get(i + 2)) == 0 &&
                    cards.get(i).compareTo(cards.get(i + 3)) == 0) {
                throw new CombinationException(FOUR_OF_A_KIND, cards.subList(i, i + 4));
            }
        }
    }

    private static void checkFullHouse(List<Card> cards) throws CombinationException {

    }

    private static void checkFlush(List<Card> cards) throws CombinationException {

    }

    private static void checkStraight(List<Card> cards) throws CombinationException {
        //TODO remember that ACE may start straight
    }

    private static void checkThreeOfAKind(List<Card> cards) throws CombinationException {

        for (int i = 0; i < cards.size() - 2; i++) {
            if (cards.get(i).compareTo(cards.get(i + 1)) == 0 &&
                    cards.get(i).compareTo(cards.get(i + 2)) == 0) {
                throw new CombinationException(THREE_OF_A_KIND, cards.subList(i, i + 3));
            }
        }
    }

    private static void checkTwoPairs(List<Card> cards) throws CombinationException {

        List<Card> temp = new ArrayList<>(cards);
        List<Card> result = new ArrayList<>();
        try {
            checkPair(temp);
        } catch (CombinationException e) {
            result.addAll(e.getCards());
            temp.removeAll(e.getCards());
            try {
                checkPair(temp);
            } catch (CombinationException e1) {
                result.addAll(e1.getCards());
                throw new CombinationException(TWO_PAIRS, result);
            }
        }
    }

    private static void checkJacksOrBetter(List<Card> cards) throws CombinationException {

        List<Card> temp = new ArrayList<>(cards);
        try {
            checkPair(temp);
        } catch (CombinationException e) {
            if (e.getCards().get(0).getRank().compareTo(Rank.JACK) >= 0) {
                throw new CombinationException(JACKS_OR_BETTER, e.getCards());
            } else {
                temp.removeAll(e.getCards());
                checkJacksOrBetter(temp);
            }
        }
    }

    private static void checkPair(List<Card> cards) throws CombinationException {

        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).compareTo(cards.get(i + 1)) == 0) {
                throw new CombinationException(null, cards.subList(i, i + 2));
            }
        }
    }
}
