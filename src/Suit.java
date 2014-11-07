enum Suit {
    //ASCII codes
    HEARTS(3),
    DIAMONDS(4),
    CLUBS(5),
    SPADES(6);

    private final String value;

    private Suit(int value) {
        this.value = Character.toString((char)value);
    }

    String getValue() {
        return value;
    }
}
