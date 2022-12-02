package Items;

public enum Time {
    NOW("В это время "), AT_THE_MOMENT("сразу же");

    private final String time;

    Time(String name) {
        this.time = name;
    }


    public String toString() {
        return this.time;
    }
}
