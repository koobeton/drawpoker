class Card implements Comparable {

    private final Rank rank;
    private final Suit suit;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        String color = "";
        switch (suit) {
            case HEARTS:
            case DIAMONDS:
                color = Paint.RED;
                break;
            case CLUBS:
            case SPADES:
                color = Paint.BLACK;
        }
        return Paint.getAnsiString(color, rank.toString() + suit.toString());
    }

    @Override
    public int compareTo(Object o) {
        Card other = (Card)o;
        return rank.compareTo(other.rank);
    }

    Rank getRank() {
        return rank;
    }

    Suit getSuit() {
        return suit;
    }
}
