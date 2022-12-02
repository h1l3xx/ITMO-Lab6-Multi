package Items;

public enum Where {
    CURTAIN(" за занавеской"), HOLE("в отверстие голову"), HERE("здесь надо"), AROUND("вокруг");

    private final String where;

    Where(String where) {
        this.where = where;
    }


    public String toString() {
        return this.where;
    }
}
