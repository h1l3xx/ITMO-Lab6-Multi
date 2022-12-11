package Items;

public enum Time {
    NOW("В это время "), AT_THE_MOMENT("сразу же"), HERE("Тут "), LIKE_AT_THIS_MOMENT("Как в этот момент"), INSTANTLY("моментально "), TODAY("сегодня ");
    private final String time;

    Time(String name) {
        this.time = name;
    }


    public String toString() {
        return this.time;
    }
}
