import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deal {

    private static final int CARDS_IN_HAND = 5;
    private static final String EXIT = "exit";

    private static boolean sort = false;
    private static boolean endGame = false;
    private static BufferedReader standardInputStream;
    private static List<Card> deck;
    private static List<Card> hand;
    private static Billing billing;

    public static void main(String... args) {

        AnsiConsole.systemInstall();

        if (args.length != 0) handleArgs(args);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            standardInputStream = reader;

            billing = new Billing();

            while (!endGame) {

                Combination combination = null;

                newDeal();

                billing.makeBet();

                dealHand(CARDS_IN_HAND);
                System.out.println(hand);

                AnsiConsole.systemUninstall();
                holdHand();
                AnsiConsole.systemInstall();

                dealHand(CARDS_IN_HAND - hand.size());
                System.out.println(hand);

                try {
                    Combination.check(hand);
                } catch (CombinationException e) {
                    combination = e.getCombination();
                    System.out.println(e.getMessage());
                } finally {
                    Statistics.update(combination);
                    billing.update(combination);
                }

                AnsiConsole.systemUninstall();
                checkEndGame();
                AnsiConsole.systemInstall();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //show statistics and exit
        Statistics.show();
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
        if (sort) hand.sort(null);
    }

    private static void holdHand() throws IOException {

        List<Card> hold = new ArrayList<>();

        System.out.print("Hold [1-5]: ");
        for (char c : standardInputStream.readLine().replaceAll("[^1-5]", "").toCharArray()) {
            int i = Integer.parseInt(Character.toString(c));
            if (!hold.contains(hand.get(i - 1))) {
                hold.add(hand.get(i - 1));
            }
            if (hold.size() == CARDS_IN_HAND) break;
        }

        hand = hold;
    }

    private static void checkEndGame() throws IOException {

        if (billing.getCredit() <= 0) {
            endGame = true;
            System.out.printf("%nGame over!%n%n");
        } else {
            System.out.printf("%nPress [Enter] to continue or type [%s] to exit: ", EXIT);
            if (standardInputStream.readLine().equals(EXIT)) endGame = true;
            System.out.printf("%n");
        }
    }

    private static void handleArgs(String... args) {

        for (String arg : args) {
            try {
                switch (Option.get(arg)) {
                    case SORT:
                        sort = true;
                        break;
                    case STAT:
                        Statistics.show();
                        break;
                    case PAYOUT:
                        Statistics.showPayouts();
                }
            } catch (NullPointerException e) {
                Option.showAvailable();
            }
        }
    }

    static void exit() {

        AnsiConsole.systemUninstall();
        System.exit(0);
    }

    /**
     * for debug purpose only
     * */
    private static void remainingCards() {

        deck.sort(null);
        System.out.printf("Remaining cards (%d):%n%s%n", deck.size(), deck);
    }

    /**
     * for debug purpose only
     * */
    private static void testDealHand() {

        hand.add(new Card(Rank.KING, Suit.CLUBS));
        hand.add(new Card(Rank.TEN, Suit.CLUBS));
        hand.add(new Card(Rank.ACE, Suit.CLUBS));
        hand.add(new Card(Rank.QUEEN, Suit.CLUBS));
        hand.add(new Card(Rank.JACK, Suit.CLUBS));
        hand.sort(null);
    }
}
