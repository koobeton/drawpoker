import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Deck extends ArrayList<Card> {

    Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.add(new Card(rank, suit));
            }
        }
    }

    void shuffle() {
        Collections.shuffle(this);
    }

    List<Card> dealHand(int cardsToDeal) {

        int deckSize = this.size();
        if (deckSize < cardsToDeal) {
            throw new IllegalArgumentException("Not enough cards");
        }
        List<Card> handView = this.subList(deckSize - cardsToDeal, deckSize);
        List<Card> hand = new ArrayList<>(handView);
        handView.clear();
        return hand;
    }
}
