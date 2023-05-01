package Human;

public enum Form {
    HE("Он "), HIM("его") ,  ALL(" Все "), SHORTYS("коротышек, "), CR("толпу"), WHO(" кто"), SHORTY("коротышку "), YOURSELF_AND_OTHERS("себя и других "),
    THEM("их "), FROM("От "), UNLUCKY_MAN(" у бедняги ");

    private final String form;

    Form (String form) {
        this.form = form;
    }
    public String toString() {
        return this.form;
    }
}
