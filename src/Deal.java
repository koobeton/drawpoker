import java.util.List;

public class Deal {

    private static final int CARDS_TO_DEAL = 5;

    public static void main(String... args) {

        Deck deck = new Deck();
        List<Card> hand;

        deck.shuffle();

        try {
            while (deck.size() > 0) {
                hand = deck.dealHand(CARDS_TO_DEAL);
                hand.sort(null);
                System.out.println(hand);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.printf("Remaining cards:%n%s%n", deck);
        }
    }
}
