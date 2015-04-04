package cards;

public enum Rank {

    DEUCE("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    private final String value;
    private final int weight;

    Rank(String value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return value;
    }

    int getWeight() {
        return weight;
    }
}
