package Human;

public enum Form {
    HE("Он "), HEL("он "), HIM("его") ,  ALL(" Все "), SHRT("коротышек, "), CR("толпу");

    private final String form;

    Form (String form) {
        this.form = form;
    }
    public String toString() {
        return this.form;
    }
}
