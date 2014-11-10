import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deal {

    private static final int CARDS_IN_HAND = 5;
    private static List<Card> deck;
    private static List<Card> hand;

    public static void main(String... args) {

        newDeal();

        dealHand(CARDS_IN_HAND);
        System.out.println(hand);

        holdHand();
        dealHand(CARDS_IN_HAND - hand.size());
        System.out.println(hand);

        try {
            Combination.check(hand);
        } catch (CombinationException e) {
            System.out.println(e.getMessage());
        }

        //for debug purpose only
        //remainingCards();
    }

    private static void newDeal() {

        deck = new ArrayList<>();
        hand = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(deck);
    }

    private static void dealHand(int cardToDeal) {

        hand.addAll(Deck.deal(deck, cardToDeal));
        hand.sort(null);
    }

    private static void holdHand() {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String input;
        List<Card> hold = new ArrayList<>();

        System.out.print("Hold [1-5]: ");
        try {
            input = reader.readLine().replaceAll("[^1-5]", "");
            for (char c : input.toCharArray()) {
                int i = Integer.parseInt(Character.toString(c));
                if (!hold.contains(hand.get(i - 1))) {
                    hold.add(hand.get(i - 1));
                }
                if (hold.size() == 5) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        hand = hold;
    }

    /**
     * for debug purpose only
     * */
    private static void remainingCards() {

        deck.sort(null);
        System.out.printf("Remaining cards (%d):%n%s%n", deck.size(), deck);
    }
}
