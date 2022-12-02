package Human;



public class Human implements Moveable, Emotionable, Cognitiveable{
    String name;
    Size size;
    Sex sex;


    public Human(String name, Sex sex, Size size) {
        this.name = name;
        this.sex = sex;
        this.size = size;
    }

    public Human() {}

    public String noTime(){
        return "Не успел ";
    }


    @Override
    public String payAttention(){
        return "не обращать внимания на ";
    }

    public String persevere(){
        return "Стараясь ";
    }

    public String say() {
        return " сказал:";
    }


    @Override
    public String laugh() {
        return " смеётся";
    }

    @Override
    public String getThrought() {
        return this.name + " пролез сковь ";
    }


    @Override
    public String pushThrought() {
        return "просунул ";
    }

    @Override
    public String jumpUp() {
        return " вскочив на помост";
    }

    @Override
    public String hide() {
        return this.name + " спрятался";
    }

    public String rejectHead() {
        return "отклонять голову ";
    }

    public String lookAround() {
        return "оглядеться вокруг ";
    }

    @Override
    public String act(){
        return " действовать ";
    }

    public String nimbly(){
        return "проворнее.";
    }

    @Override
    public String mksr(){
        return " убедился ";
    }

    @Override
    public int hashCode(){
        return this.name.hashCode() + this.sex.hashCode();
    }






}
