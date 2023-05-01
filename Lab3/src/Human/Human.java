package Human;

public class Human implements Moveable, Emotionable, Cognitiveable{
    String name;
    Size size;
    Sex sex;

    String eyelid_name;

    String health = "poor";

    String charactersFace;


    String condition;


    public Human(String name, Sex sex, Size size) throws NoNameException {
        if (name.length() == 0)
            throw new NoNameException();
        this.name = name;
        this.size = size;
        this.sex = sex;


    }

    public class Face {
        public void setFace(){
            charactersFace = "лицо";
        }

        public static class Eye {

            public String toString(){
                return "закрылся и перестал видеть";
            }

            String eye;
            public void stringEye(){
                eye = "глаз";
            }
        }

    }

    Face eyelids = new Face(){
        public void swallUp(){
            eyelid_name = "веко";
            condition = "распухло. ";
        }

    };

    public String stringEyelid() {
        eyelids.setFace();
        return eyelid_name;
    }
    public String eyelidSwollen(){
        eyelids.setFace();
        return condition;
    }

    public String eyeDontSee(){
        return "закрылся и перестал видеть";
    }

    public String eye(){
        Face.Eye eye1 = new Face.Eye();
        eye1.stringEye();
        return eye1.eye;

    }


    public String charactersFace(){
        Face face = new Face();
        face.setFace();
        return charactersFace;
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
        class Platform {
            final String platform = "помост";
            @Override
            public String toString(){
                return platform;
            }
        }

        return " вскочив на " + new Platform();
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

    public String shortyMan(){
        return " коротышки.";
    }


    public String otherForm(){
        return "хозяина балаганчика.";
    }

    public String getScary(){
        return "Испуганный коротышка";
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

    public void checkHealth(){
        if (health.equals("normal")){
            throw new PoorHealthException();
        }
    }

    public String standUp(){
        return " снова поднялся";
    }






}
