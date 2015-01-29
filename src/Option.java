enum Option {

    SORT("-sort", "Sort hand by rank in ascending order (unsorted by default)"),
    STAT("-stat", "Show statistics and exit");

    private final String value;
    private final String description;

    private Option(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("\t%s\t%s", value, description);
    }

    static Option get(String arg) {
        for (Option option : Option.values()) {
            if (arg.equals(option.value)) return option;
        }
        return null;
    }
}