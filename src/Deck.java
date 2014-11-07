import java.util.ArrayList;
import java.util.List;

class Deck {

    static <E> List<E> deal(List<E> deck, int cardsToDeal) {

        int deckSize = deck.size();
        if (deckSize < cardsToDeal) {
            throw new IllegalArgumentException("Not enough cards");
        }
        List<E> handView = deck.subList(deckSize - cardsToDeal, deckSize);
        List<E> hand = new ArrayList<>(handView);
        handView.clear();
        return hand;
    }
}
