package Items;

public enum Numeral {
    FOUR("четырёх "), FIVE(" пятый ");
    private final String num;

    Numeral(String num) {
        this.num = num;
    }


    public String toString() {
        return this.num;
    }
}
