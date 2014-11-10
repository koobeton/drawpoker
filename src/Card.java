class Card implements Comparable {

    private final Rank rank;
    private final Suit suit;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

    @Override
    public int compareTo(Object o) {
        Card other = (Card)o;
        return rank.compareTo(other.rank);
    }

    Rank getRank() {
        return rank;
    }
}
