package cards;

import java.util.ArrayList;
import java.util.List;

public enum Combination {

    ROYAL_FLUSH("Royal Flush", 500),
    STRAIGHT_FLUSH("Straight Flush", 50),
    FOUR_OF_A_KIND("Four of a Kind", 25),
    FULL_HOUSE("Full House", 8),
    FLUSH("Flush", 5),
    STRAIGHT("Straight", 4),
    THREE_OF_A_KIND("Three of a Kind", 3),
    TWO_PAIRS("Two Pairs", 2),
    JACKS_OR_BETTER("Jacks or Better", 1);

    private final String value;
    private final int payout;

    Combination(String value, int payout) {
        this.value = value;
        this.payout = payout;
    }

    @Override
    public String toString() {
        return value;
    }

    public int getPayout() {
        return payout;
    }

    public static void check(List<Card> hand) throws CombinationThrowable {

        //hand must be sorted in ascending order
        hand.sort(null);
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

    private static void checkRoyalFlush(List<Card> cards) throws CombinationThrowable {

        if (cards.get(0).getRank().compareTo(Rank.TEN) == 0) {
            try {
                checkStraightFlush(cards);
            } catch (CombinationThrowable e) {
                throw new CombinationThrowable(ROYAL_FLUSH, cards);
            }
        }
    }

    private static void checkStraightFlush(List<Card> cards) throws CombinationThrowable {

        try {
            checkFlush(cards);
        } catch (CombinationThrowable e) {
            try {
                checkStraight(cards);
            } catch (CombinationThrowable e1) {
                throw new CombinationThrowable(STRAIGHT_FLUSH, cards);
            }
        }
    }

    private static void checkFourOfAKind(List<Card> cards) throws CombinationThrowable {

        for (int i = 0; i < cards.size() - 3; i++) {
            if (cards.get(i).compareTo(cards.get(i + 1)) == 0 &&
                    cards.get(i).compareTo(cards.get(i + 2)) == 0 &&
                    cards.get(i).compareTo(cards.get(i + 3)) == 0) {
                throw new CombinationThrowable(FOUR_OF_A_KIND, cards.subList(i, i + 4));
            }
        }
    }

    private static void checkFullHouse(List<Card> cards) throws CombinationThrowable {

        List<Card> temp = new ArrayList<>(cards);
        try {
            checkThreeOfAKind(temp);
        } catch (CombinationThrowable e) {
            temp.removeAll(e.getCards());
            try {
                checkPair(temp);
            } catch (CombinationThrowable e1) {
                throw new CombinationThrowable(FULL_HOUSE, cards);
            }
        }
    }

    private static void checkFlush(List<Card> cards) throws CombinationThrowable {

        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getSuit().compareTo(cards.get(i + 1).getSuit()) != 0) return;
        }
        throw new CombinationThrowable(FLUSH, cards);
    }

    private static void checkStraight(List<Card> cards) throws CombinationThrowable {

        List<Card> temp = new ArrayList<>(cards);
        int offset = temp.get(0).getRank().compareTo(Rank.DEUCE) == 0 &&
                temp.get(temp.size() - 1).getRank().compareTo(Rank.ACE) == 0 ?
                2 : 1;
        for (int i = 0; i < temp.size() - offset; i++) {
            if (temp.get(i + 1).getRank().getWeight() - temp.get(i).getRank().getWeight() != 1) {
                return;
            }
        }
        if (offset == 2) {
            temp.add(0, temp.remove(temp.size() - 1));
        }
        throw new CombinationThrowable(STRAIGHT, temp);
    }

    private static void checkThreeOfAKind(List<Card> cards) throws CombinationThrowable {

        for (int i = 0; i < cards.size() - 2; i++) {
            if (cards.get(i).compareTo(cards.get(i + 1)) == 0 &&
                    cards.get(i).compareTo(cards.get(i + 2)) == 0) {
                throw new CombinationThrowable(THREE_OF_A_KIND, cards.subList(i, i + 3));
            }
        }
    }

    private static void checkTwoPairs(List<Card> cards) throws CombinationThrowable {

        List<Card> temp = new ArrayList<>(cards);
        List<Card> result = new ArrayList<>();
        try {
            checkPair(temp);
        } catch (CombinationThrowable e) {
            result.addAll(e.getCards());
            temp.removeAll(e.getCards());
            try {
                checkPair(temp);
            } catch (CombinationThrowable e1) {
                result.addAll(e1.getCards());
                throw new CombinationThrowable(TWO_PAIRS, result);
            }
        }
    }

    private static void checkJacksOrBetter(List<Card> cards) throws CombinationThrowable {

        List<Card> temp = new ArrayList<>(cards);
        try {
            checkPair(temp);
        } catch (CombinationThrowable e) {
            if (e.getCards().get(0).getRank().compareTo(Rank.JACK) >= 0) {
                throw new CombinationThrowable(JACKS_OR_BETTER, e.getCards());
            } else {
                temp.removeAll(e.getCards());
                checkJacksOrBetter(temp);
            }
        }
    }

    private static void checkPair(List<Card> cards) throws CombinationThrowable {

        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).compareTo(cards.get(i + 1)) == 0) {
                throw new CombinationThrowable(null, cards.subList(i, i + 2));
            }
        }
    }
}
