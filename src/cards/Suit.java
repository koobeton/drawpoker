package cards;

public enum Suit {

    //UTF-8
    HEARTS("\u2665"),
    DIAMONDS("\u2666"),
    CLUBS("\u2663"),
    SPADES("\u2660");

    private final String value;

    Suit(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
