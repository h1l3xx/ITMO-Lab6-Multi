package Human;


import java.lang.constant.Constable;

public class Human implements Moveable, Emotionable, Cognitiveable{
    String name;
    Size size;
    Sex sex;

    String old_face;

    String eyelid;

    String condition;


    public Human(String name, Sex sex, Size size) {
        this.name = name;
        this.sex = sex;
        this.size = size;
    }

    public class Face {
        public void string(){
            old_face = "лицо";
        }

        public static class Eye {

            public String toString(){
                return "закрылся и перестал видеть";
            }

            String eye;
            String eyeL;
            public void stringEye(){
                eye = "глаз";
                eyeL = "Глаз";
            }
        }

    }

    Face eyelid1 = new Face(){
        public void string(){
            eyelid = "веко";
            condition = "разпухло. ";
        }

    };

    public String stringEyelid() {
        eyelid1.string();
        return eyelid;
    }
    public String eyelidswollen(){
        eyelid1.string();
        return condition;
    }

    public String eye(){
        Face.Eye eye1 = new Face.Eye();
        eye1.stringEye();
        return eye1.eye;
    }

    public String eyeL(){
        Face.Eye eye1 = new Face.Eye();
        eye1.stringEye();
        return eye1.eyeL;
    }

    public String face(){
        Face face = new Face();
        face.string();
        return old_face;
    }

    public Human() {}

    public String noTime(){
        return "Не успел ";
    }

    public String dodge(){
        return "удалось увернуться, ";
    }

    public String look(){
        return " увидел ";
    }


    @Override
    public String payAttention(){
        return "не обращать внимания на ";
    }

    public String persevere(){
        return "Стараясь ";
    }

    public String say() {
        return " сказал";
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
    public String makeSure(){
        return " убедился ";
    }

    @Override
    public int hashCode(){
        return this.name.hashCode() + this.sex.hashCode();
    }

    @Override
    public String toString(){
        return this.name;
    }

    public String stay(){
        return "стоял ";
    }

    public String wantingToAmuse(){
        return ", желая потешить ";
    }

    public String payed(){
        return ", уплатил сразу за пять мячей";
    }

    public String beganToThrow(){
        return "принялся швырять ";
    }

    public String Shortyman(){
        return " коротышки.";
    }


    public String otherForm(){
        return "хозяина балаганчика.";
    }

    public String scaryman(){
        return ". Испуганный коротышка";
    }

    public String heCant(){
        return "не сможет работать ";
    }

    public String goToHome(){
        return " ушел домой. ";
    }

    public String notConfused(){
        return " не растерялся ";
    }

    public String climbingToPomost(){
        return " взобравшись на помост, ";
    }
    public String yelled(){
        return "закричал: ";
    }






}
