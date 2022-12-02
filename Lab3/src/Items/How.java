package Items;

public enum How {
    HARD(" сильно"), MAX(" как можно "), MET(" давольно метко ");

    private final String how;

    How(String name) {
        this.how = name;
    }


    public String toString() {
        return this.how;
    }
}
