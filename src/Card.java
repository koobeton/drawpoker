class Card {

    private final Rank rank;
    private final Suit suit;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank.getValue() + suit.getValue();
    }
}
