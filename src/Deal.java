public class Deal {

    public static void main(String... args) {

        Deck deck = new Deck();

        deck.shuffle();

        try {
            while (deck.size() > 0) {
                System.out.println(deck.dealHand(5));
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.printf("Remaining cards:%n%s%n", deck);
        }
    }
}
