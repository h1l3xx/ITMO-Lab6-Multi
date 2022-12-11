package Items;

public enum How {
    HARD(" сильно"), MAX(" как можно "), MET(" давольно метко "), FULL(" доверху наполненной "), BUT("зато"), HOWPOWER("с такой силой, что "),
    HOWEVER(", однако, ");

    private final String how;

    How(String name) {
        this.how = name;
    }


    public String toString() {
        return this.how;
    }
}
